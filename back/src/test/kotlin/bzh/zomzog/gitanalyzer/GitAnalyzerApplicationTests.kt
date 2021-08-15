package bzh.zomzog.gitanalyzer

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class GitAnalyzerApplicationTests {

	private val context = app.run(profiles = "test")

	@Test
	fun contextLoads() {
	}

	@AfterAll
	fun afterAll() {
		context.close()
	}
}
