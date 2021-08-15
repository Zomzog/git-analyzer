import { createStore, combineReducers, applyMiddleware } from 'redux'
import thunk, { ThunkMiddleware } from 'redux-thunk'
import { gitRepoReducer } from '../reducers/GitRepoReducer'
import { AppActions } from '../types/actions'
import { reducer as formReducer } from "redux-form";

export const rootReducer = combineReducers({
  gitRepos: gitRepoReducer,
  form: formReducer
})

export type AppState = ReturnType<typeof rootReducer>

export const store = createStore(rootReducer, applyMiddleware(thunk as ThunkMiddleware<AppState, AppActions>))
