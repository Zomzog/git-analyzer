package bzh.zomzog.gitanalyzer.service

import bzh.zomzog.gitanalyzer.domain.Author
import bzh.zomzog.gitanalyzer.domain.FileMetadata
import bzh.zomzog.gitanalyzer.domain.GitRepo
import bzh.zomzog.gitanalyzer.properties.AnalyzerProperties
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.Git.cloneRepository
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffEntry.ChangeType.*
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.RenameDetector
import org.eclipse.jgit.lib.Constants.MASTER
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.util.io.DisabledOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class MetadataExtractor(private val properties: AnalyzerProperties) {

    fun clone(gitRepo: GitRepo): Path {
        val path = properties.temporaryFolder.resolve(gitRepo.name + "-" + System.currentTimeMillis())
        cloneRepository()
                .setURI(gitRepo.url)
                .setDirectory(path.toFile())
                .call()
        return path
    }

    fun extractMetadata(path: Path): Map<String, FileMetadata> {
        val git = Git.open(path.toFile())
        var previousCommit: RevCommit? = null
        val metadata = mutableMapOf<String, FileMetadata>()

        val firstCommit = git.repository.findRef(MASTER).objectId
        val commits = listCommits(git, firstCommit)

        commits.forEach {
            if (isHead(previousCommit)) {
                extractAllFiles(git, it, metadata, path)
            } else {
                val diffFormatter = DiffFormatter(DisabledOutputStream.INSTANCE)
                diffFormatter.setRepository(git.repository)
                val entries: List<DiffEntry> = diffFormatter.scan(it.id, previousCommit!!.id)
                val renameDetector = RenameDetector(git.repository)
                renameDetector.addAll(entries)
                for (entry in renameDetector.compute()) {
                    val author = Author(it.committerIdent.name, it.committerIdent.emailAddress)
                    when (entry.changeType) {
                        ADD -> addInteraction(metadata, entry, author)
                        MODIFY -> addInteraction(metadata, entry, author)
                        DELETE -> Unit
                        RENAME -> moveAndAddInteraction(metadata, entry, author)
                        COPY -> TODO()
                        else -> TODO()
                    }
                }
            }
            previousCommit = it
        }
        addFirstCommit(git, previousCommit!!, metadata)
        return metadata
    }

    private fun listCommits(git: Git, firstCommit: ObjectId?): MutableList<RevCommit> {
        val commits = mutableListOf<RevCommit>()
        RevWalk(git.repository).use { walk ->
            var currentCommit: RevCommit? = walk.parseCommit(firstCommit)
            commits.add(currentCommit!!)
            while (currentCommit != null) {
                val parents = currentCommit.parents
                if (parents != null && parents.isNotEmpty()) {
                    currentCommit = walk.parseCommit(parents[0])
                    commits.add(currentCommit)
                } else {
                    currentCommit = null
                }
            }
        }
        return commits
    }

    private fun addFirstCommit(git: Git, commit: RevCommit, filesMetadata: MutableMap<String, FileMetadata>) {
        TreeWalk(git.repository).use {
            it.reset(commit.tree.id)
            it.isRecursive = true
            while (it.next()) {
                val path: String = it.pathString
                val metadata = filesMetadata[path]
                metadata?.let {
                    val author = Author(commit.authorIdent.name, commit.authorIdent.emailAddress)
                    val updaters = it.updaters.toMutableList().apply { add(author) }
                    filesMetadata[path] = it.copy(updates = it.updates + 1, updaters = updaters)
                }
            }
        }
    }

    private fun moveAndAddInteraction(filesMetadata: MutableMap<String, FileMetadata>, entry: DiffEntry, author: Author) {
        val metadata = filesMetadata[entry.newPath]
        metadata?.let {
            filesMetadata[entry.oldPath] = metadata
            filesMetadata.remove(entry.newPath)
        }
        addInteraction(filesMetadata, entry, author)
    }

    private fun addInteraction(filesMetadata: MutableMap<String, FileMetadata>, entry: DiffEntry, author: Author) {
        val metadata = filesMetadata[entry.newPath]
        metadata?.let {
            val updaters = it.updaters.toMutableList().apply { add(author) }
            filesMetadata[entry.newPath] = it.copy(updates = it.updates + 1, updaters = updaters)
        }
    }

    private fun extractAllFiles(git: Git, commit: RevCommit, metadata: MutableMap<String, FileMetadata>, rootPath: Path) {
        TreeWalk(git.repository).use {
            it.reset(commit.tree.id)
            it.isRecursive = true
            while (it.next()) {
                val path: String = it.pathString
                println("path = ${path}")
                val linesCount = countLines(rootPath, path)
                metadata[path] = FileMetadata(path, linesCount = linesCount)
            }
        }
    }

    private fun countLines(rootPath: Path, path: String): Long {
        val file = rootPath.resolve(path)
        return try {
            Files.lines(file).count()
        } catch (e: Exception) {
            UNREADABLE_FILE_DEFAULT_LINES_COUNT
        }
    }
    private fun isHead(previousCommit: RevCommit?) = previousCommit == null
    companion object {
        const val UNREADABLE_FILE_DEFAULT_LINES_COUNT = 0L
    }
}