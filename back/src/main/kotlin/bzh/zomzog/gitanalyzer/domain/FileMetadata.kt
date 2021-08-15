package bzh.zomzog.gitanalyzer.domain

data class FileMetadata(
        val path: String,
        val updates: Int = 0,
        val linesCount: Long = 0,
        val updaters: List<Author> = emptyList()
)

data class Author(val name: String,
                  val email: String)
