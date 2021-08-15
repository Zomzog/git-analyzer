package bzh.zomzog.gitanalyzer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id val login: String,
        val firstname: String,
        val lastname: String
)