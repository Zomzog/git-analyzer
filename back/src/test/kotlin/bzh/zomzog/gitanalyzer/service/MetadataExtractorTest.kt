package bzh.zomzog.gitanalyzer.service

import assertk.assertThat
import assertk.assertions.containsOnly
import bzh.zomzog.gitanalyzer.domain.FileMetadata
import bzh.zomzog.gitanalyzer.domain.GitRepo
import bzh.zomzog.gitanalyzer.initScenario1
import bzh.zomzog.gitanalyzer.properties.AnalyzerProperties
import bzh.zomzog.gitanalyzer.author1
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.eclipse.jgit.util.SystemReader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.Paths

internal class MetadataExtractorTest {
    @TempDir
    lateinit var tmpDir: Path
    val properties = mockk<AnalyzerProperties>()
    val service = MetadataExtractor(properties)

    @BeforeEach
    fun setUp() {
        every { properties.temporaryFolder } returns tmpDir
    }

    @Test
    fun `clone`() = runBlocking {
        // GIVEN
        // WHEN
        service.clone(GitRepo("zomzog.fr", "https://github.com/Zomzog/zomzog.fr.git"))
        // THEN
    }

    @Test
    fun `extract`() = runBlocking {
        // GIVEN
        // WHEN
        service.extractMetadata(Paths.get("/home/zomzog/git/zomzog/zomzog.fr"))
        // THEN
    }

    @Test
    fun `extract from linear history`() = runBlocking {
        // GIVEN
        tmpDir.initScenario1()
        // WHEN
        val result = service.extractMetadata(tmpDir)
        // THEN
        result.forEach{
            println("${it.key} -> ${it.value}")
        }
        assertThat(result).containsOnly(
                "fileA1" to FileMetadata("fileA1", 2, 1, listOf(author1, author1)),
                "fileB1" to FileMetadata("fileB1", 2, 1, listOf(author1, author1)),
                "fileB2" to FileMetadata("fileB2", 1, 0, listOf(author1))
        )
    }
}