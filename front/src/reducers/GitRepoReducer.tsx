
import { GitRepo } from '../types/GitRepo'
import { GitRepoActionTypes } from '../types/GitRepoActionTypes'

const gitRepoReducerDefaultState: GitRepo[] = []

const gitRepoReducer = (state = gitRepoReducerDefaultState, action: GitRepoActionTypes): GitRepo[] => {
  switch (action.type) {
    case 'ADD_GITREPO':
      return [...state, action.gitRepo]
    case 'REMOVE_GITREPO':
      return state.filter(({ name }) => name !== action.name)
    case 'EDIT_GITREPO':
      return state.map((gitRepo) => {
        if (gitRepo.name === action.gitRepo.name) {
          return {
            ...gitRepo,
            ...action.gitRepo
          }
        } else {
          return gitRepo
        }
      })
    case 'SET_GITREPOS':
      return action.gitRepos
    default:
      return state
  }
}

export { gitRepoReducer }
