package bzh.zomzog.gitanalyzer.service

import bzh.zomzog.gitanalyzer.domain.AnalyzeState.ANALYZE
import bzh.zomzog.gitanalyzer.domain.AnalyzeState.DONE
import bzh.zomzog.gitanalyzer.domain.GitRepo
import bzh.zomzog.gitanalyzer.domain.GitRepoCreate
import bzh.zomzog.gitanalyzer.repository.GitRepoRepository

class GitRepoManager(private val metadataExtractor: MetadataExtractor,
                     private val repository: GitRepoRepository) {
    suspend fun add(gitRepoCreate: GitRepoCreate): GitRepo {
        assertNotExist(gitRepoCreate)
        var repo = repository.insert(gitRepoCreate.toGitRepo())
        val clone = metadataExtractor.clone(repo)
        repo = repository.update(repo.copy(analyzeState = ANALYZE))
        val metadata = metadataExtractor.extractMetadata(clone)
        return repository.update(repo.copy(analyzeState = DONE, metadata = metadata.values.toSet()))
    }

    private suspend fun assertNotExist(gitRepo: GitRepoCreate) {
        val findOne = repository.findOne(gitRepo.name)
        findOne?.let {
            throw Exception("Git repository ${gitRepo.name} already exist")
        }
    }

    suspend fun findOne(name: String): GitRepo =
            repository.findOne(name) ?: throw Exception("$name not found")

}