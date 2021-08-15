package bzh.zomzog.gitanalyzer.service

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.messageContains
import bzh.zomzog.gitanalyzer.domain.AnalyzeState
import bzh.zomzog.gitanalyzer.domain.AnalyzeState.CLONE
import bzh.zomzog.gitanalyzer.domain.AnalyzeState.DONE
import bzh.zomzog.gitanalyzer.domain.FileMetadata
import bzh.zomzog.gitanalyzer.domain.GitRepo
import bzh.zomzog.gitanalyzer.domain.GitRepoCreate
import bzh.zomzog.gitanalyzer.repository.GitRepoRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

internal class GitRepoManagerTest {

    val metadataExtractor = mockk<MetadataExtractor>()
    val repository = mockk<GitRepoRepository>()
    val service = GitRepoManager(metadataExtractor, repository)

    @TempDir
    lateinit var tmpDir: Path

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `add a new repository`() = runBlocking {
        // GIVEN
        coEvery { repository.findOne("name") } returns null
        coEvery { repository.insert(any()) } returns defaultGitRepo()
        coEvery { repository.update(any()) } answers { firstArg() }
        coEvery { metadataExtractor.clone(defaultGitRepo()) } returns tmpDir
        coEvery { metadataExtractor.extractMetadata(tmpDir) } returns emptyMap()
        // WHEN
        val result = service.add(defaultGitRepoCreate())
        // THEN
        coVerify(exactly = 1) { repository.insert(defaultGitRepo()) }
        coVerify(exactly = 1) { metadataExtractor.extractMetadata(tmpDir) }
        assertThat(result).isEqualTo(defaultGitRepo(analyzeState = DONE))
    }

    @Test
    fun `cannot add a repository with same name`() = runBlocking {
        // GIVEN
        coEvery { repository.findOne("name") } returns defaultGitRepo(name = "otherName")
        // WHEN
        assertThat {
            service.add(defaultGitRepoCreate())
        }
                // THEN
                .isFailure()
                .messageContains("name")
    }

    private fun defaultGitRepoCreate(name: String = "name", url: String = "https://url.git") = GitRepoCreate(name, url)
    private fun defaultGitRepo(name: String = "name", url: String = "https://url.git", analyzeState: AnalyzeState = CLONE, metadata: Set<FileMetadata> = emptySet()) = GitRepo(name, url, analyzeState, metadata)

}