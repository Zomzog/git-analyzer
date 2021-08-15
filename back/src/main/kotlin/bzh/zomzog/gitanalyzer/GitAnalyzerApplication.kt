package bzh.zomzog.gitanalyzer

import bzh.zomzog.gitanalyzer.config.*
import bzh.zomzog.gitanalyzer.properties.AnalyzerProperties
import org.springframework.boot.logging.LogLevel
import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
	logging {
		level = LogLevel.INFO
	}
	configurationProperties<AnalyzerProperties>(prefix = "analyzer")
	enable(mongoConfig)
	enable(gitRepoConfig)
	enable(userConfig)
	enable(webConfig)
}

fun main() {
	app.run()
}