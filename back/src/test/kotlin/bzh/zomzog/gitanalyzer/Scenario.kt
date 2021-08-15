package bzh.zomzog.gitanalyzer

import bzh.zomzog.gitanalyzer.domain.Author
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.util.FS
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path


data class Scenario(val repositoryLocation: File,
                    val commits: Map<String, Commit>)

data class Commit(val id: String,
                  val gitId: ObjectId,
                  val files: Set<String>,
                  val author: Author)

fun scenario(repositoryLocation: Path, block: ScenarioBuilder.() -> Unit) = ScenarioBuilder(repositoryLocation)
        .apply(block)
        .build()

class ScenarioBuilder(val repositoryLocation: Path) {
    val commits = mutableMapOf<String, Commit>()
    val git: Git

    init {
        git = Git.init()
                .setDirectory(repositoryLocation.toFile())
                .call()
    }

    fun commit(block: CommitBuilder.() -> Unit) {
        val commit = CommitBuilder(this).apply(block).build()
        commits[commit.id] = commit
    }

    fun build() = Scenario(repositoryLocation.toFile(), commits)

    class CommitBuilder(val scenarioBuilder: ScenarioBuilder) {
        var id: String = ""
        val files = mutableSetOf<String>()
        var message: String = ""
        var author: Author? = null

        fun file(block: CommitFile.() -> Unit) {
            val fileName = CommitFile().apply(block).fileName
            files.add(fileName)
            scenarioBuilder.repositoryLocation.resolve(fileName).createOrUpdate()
            scenarioBuilder.git.add()
                    .addFilepattern(fileName)
                    .call()
        }

        fun build(): Commit {
            if (author == null) {
                throw Exception("Author is required")
            }
            val commit = scenarioBuilder.git.commit()
                    .setMessage(message)
                    .setAuthor(PersonIdent(author!!.name, author!!.email))
                    .setCommitter(PersonIdent(author!!.name, author!!.email))
                    .call()
            return Commit(id, commit.id, files, author!!)
        }
    }
}

class CommitFile {
    var fileName: String = ""
}

fun Path.createOrUpdate() {
    val file = toFile()
    if (file.exists()) {
        addALine(file)
    } else {
        touch(file)
    }
}

private fun addALine(file: File) {
    BufferedWriter(FileWriter(file, true))
            .use {
                it.appendln("new line")
            }
}

fun touch(file: File) {
    if (!file.createNewFile() && !file.setLastModified(System.currentTimeMillis())) {
        throw IOException("Unable to update modification time of $file")
    }
}