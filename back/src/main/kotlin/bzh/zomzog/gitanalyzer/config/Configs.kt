package bzh.zomzog.gitanalyzer.config

import bzh.zomzog.gitanalyzer.handler.RepositoryHandler
import bzh.zomzog.gitanalyzer.handler.UserHandler
import bzh.zomzog.gitanalyzer.repository.GitRepoRepository
import bzh.zomzog.gitanalyzer.repository.UserRepository
import bzh.zomzog.gitanalyzer.service.GitRepoManager
import bzh.zomzog.gitanalyzer.service.MetadataExtractor
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.mongo.reactiveMongodb
import de.flapdoodle.embed.mongo.distribution.Version

val mongoConfig = configuration {
    reactiveMongodb {
        embedded {
            version = Version.Main.PRODUCTION
        }
    }
}

val gitRepoConfig = configuration {
    beans {
        bean<GitRepoRepository>()
        bean<GitRepoManager>()
        bean<MetadataExtractor>()
        bean<RepositoryHandler>()
        bean(::gitRepoRoutes)
    }
}

val userConfig = configuration {
    beans {
        bean<UserRepository>()
        bean<UserHandler>()
        bean(::userRoutes)
    }
    listener<ApplicationReadyEvent> {
        runBlocking {
            ref<UserRepository>().init()
        }
    }
}
