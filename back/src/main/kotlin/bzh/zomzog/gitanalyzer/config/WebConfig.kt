package bzh.zomzog.gitanalyzer.config

import bzh.zomzog.gitanalyzer.handler.RepositoryHandler
import bzh.zomzog.gitanalyzer.handler.UserHandler
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.webflux.mustache
import org.springframework.fu.kofu.webflux.webFlux

val webConfig = configuration {
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        mustache()
        codecs {
            string()
            jackson()
        }
    }
}