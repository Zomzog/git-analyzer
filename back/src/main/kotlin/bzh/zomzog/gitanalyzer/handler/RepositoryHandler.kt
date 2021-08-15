package bzh.zomzog.gitanalyzer.handler

import bzh.zomzog.gitanalyzer.domain.GitRepoCreate
import bzh.zomzog.gitanalyzer.properties.AnalyzerProperties
import bzh.zomzog.gitanalyzer.service.GitRepoManager
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.lang.Exception

class RepositoryHandler(properties: AnalyzerProperties, private val gitRepoManager: GitRepoManager) {

    init {
        val file = properties.temporaryFolder.toFile()
        if (!file.exists()) {
            val mkdirs = file.mkdirs()
            if (!mkdirs) {
                throw Exception("Mkdir fails")
            }
        }
    }

    suspend fun add(request: ServerRequest): ServerResponse {
        val git = request.awaitBody<GitRepoCreate>()

        return ok().bodyValueAndAwait(gitRepoManager.add(git))
    }

    suspend fun findOne(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")

        return ok().bodyValueAndAwait(gitRepoManager.findOne(name))
    }
}