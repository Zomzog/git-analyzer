package bzh.zomzog.gitanalyzer.repository

import bzh.zomzog.gitanalyzer.domain.GitRepo
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo

class GitRepoRepository(
        private val mongo: ReactiveFluentMongoOperations
) {
    suspend fun findOne(name: String) = mongo.query<GitRepo>()
            .matching(query(where("name").isEqualTo(name)))
            .awaitOneOrNull()

    suspend fun insert(gitRepo: GitRepo) = mongo.insert<GitRepo>()
            .oneAndAwait(gitRepo)

    suspend fun update(gitRepo: GitRepo) = mongo.update<GitRepo>()
            .replaceWith(gitRepo)
            .asType<GitRepo>()
            .findReplaceAndAwait()!!

}