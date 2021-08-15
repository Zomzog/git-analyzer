import { GitRepo } from './GitRepo'

export const ADD_GITREPO = 'ADD_GITREPO'
export const EDIT_GITREPO = 'EDIT_GITREPO'
export const REMOVE_GITREPO = 'REMOVE_GITREPO'
export const SET_GITREPOS = 'SET_GITREPOS'

export interface SetGitRepoAction {
  type: typeof SET_GITREPOS
  gitRepos: GitRepo[]
}

export interface EditGitRepoAction {
  type: typeof EDIT_GITREPO
  gitRepo: GitRepo
}

export interface RemoveGitRepoAction {
  type: typeof REMOVE_GITREPO
  name: String
}

export interface AddGitRepoAction {
  type: typeof ADD_GITREPO
  gitRepo: GitRepo
}

export type GitRepoActionTypes = SetGitRepoAction | EditGitRepoAction | RemoveGitRepoAction | AddGitRepoAction
