package bzh.zomzog.gitanalyzer.domain

import bzh.zomzog.gitanalyzer.domain.AnalyzeState.CLONE
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class GitRepo(
        @Id val name: String,
        val url: String,
        val analyzeState: AnalyzeState = CLONE,
        val metadata: Set<FileMetadata> = emptySet()
)

enum class AnalyzeState {
    CLONE,
    ANALYZE,
    DONE,
    ERROR
}

data class GitRepoCreate(
        val name: String,
        val url: String
) {
    fun toGitRepo() = GitRepo(name, url)
}
