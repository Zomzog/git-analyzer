import { Dispatch } from 'redux'
import { AppState } from '../store/configureStore'
import { AppActions } from '../types/actions'
import { GitRepo } from '../types/GitRepo'

export const addGitRepo = (gitRepo: GitRepo): AppActions => ({
  type: 'ADD_GITREPO',
  gitRepo
})

export const removeGitRepo = (name: String): AppActions => ({
  type: 'REMOVE_GITREPO',
  name
})

export const editGitRepo = (gitRepo: GitRepo): AppActions => ({
  type: 'EDIT_GITREPO',
  gitRepo
})

export const setGitRepos = (gitRepos: GitRepo[]): AppActions => ({
  type: 'SET_GITREPOS',
  gitRepos
})

export const startAddGitRepo = (gitRepoData: { name: string; url: string; analyzeState: string }) => {
  return (dispatch: Dispatch<AppActions>, getState: () => AppState) => {
    const { name = '', url = '', analyzeState = '' } = gitRepoData
    const gitRepo = { name, url, analyzeState }

    dispatch(
      addGitRepo({
        ...gitRepo
      })
    )
  }
}

export const startRemoveGitRepo = (name: string) => {
  return (dispatch: Dispatch<AppActions>, getState: () => AppState) => {
    dispatch(removeGitRepo(name))
  }
}

export const startEditGitRepo = (gitRepo: GitRepo) => {
  return (dispatch: Dispatch<AppActions>, getState: () => AppState) => {
    dispatch(editGitRepo(gitRepo))
  }
}

export const startSetGitRepos = (gitRepos: GitRepo[]) => {
  return (dispatch: Dispatch<AppActions>, getState: () => AppState) => {
    dispatch(setGitRepos(gitRepos))
  }
}
