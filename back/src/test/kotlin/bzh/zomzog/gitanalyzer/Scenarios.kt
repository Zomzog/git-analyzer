package bzh.zomzog.gitanalyzer

import bzh.zomzog.gitanalyzer.domain.Author
import java.nio.file.Path

val author1 = Author("author1", "author1@zomzog.fr")
val author2 = Author("author2", "author2@zomzog.fr")

fun Path.initScenario1() = scenario(this) {
    commit {
        id = "A"
        message = "Commit A"
        author = author1
        file {
            fileName = "fileA1"
        }
    }
    commit {
        id = "B"
        message = "Commit B"
        author = author1
        file {
            fileName = "fileB1"
        }
        file {
            fileName = "fileB2"
        }
    }
    commit {
        id = "B"
        message = "Commit C"
        author = author1
        file {
            fileName = "fileA1"
        }
        file {
            fileName = "fileB1"
        }
    }
}
