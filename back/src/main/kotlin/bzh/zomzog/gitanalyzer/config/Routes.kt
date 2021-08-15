package bzh.zomzog.gitanalyzer.config

import bzh.zomzog.gitanalyzer.handler.RepositoryHandler
import bzh.zomzog.gitanalyzer.handler.UserHandler
import org.springframework.web.reactive.function.server.coRouter

fun userRoutes(userHandler: UserHandler) = coRouter {
    GET("/", userHandler::listView)
    GET("/api/user", userHandler::listApi)
    GET("/conf", userHandler::conf)
}

fun gitRepoRoutes(repositoryHandler: RepositoryHandler) = coRouter {
    "/git".nest {
        POST("", repositoryHandler::add)
        GET("/{name}", repositoryHandler::findOne)
    }
}
