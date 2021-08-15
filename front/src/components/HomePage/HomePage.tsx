import React from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { ThunkDispatch } from 'redux-thunk'
import { startEditGitRepo, startRemoveGitRepo, startAddGitRepo } from '../../actions/GitRepoActions'
import { AppState } from '../../store/configureStore'
import { AppActions } from '../../types/actions'
import { GitRepo } from '../../types/GitRepo'

interface HomePageState {}

interface HomePageProps {
  id?: string
  color?: string
}

interface LinkStateProp {
  gitRepos: GitRepo[]
}

interface LinkDispatchProp {
  startAddGitRepo: (gitRepo: GitRepo) => void
  startEditGitRepo: (gitRepo: GitRepo) => void
  startRemoveGitRepo: (name: string) => void
}

type Props = HomePageProps & LinkDispatchProp & LinkStateProp

class HomePageComponent extends React.Component<Props, HomePageState> {
  onAdd = () => {
    const gitRepo: GitRepo = { name: 'name' + Date.now(), url: 'url', analyzeState: 'analyzeState' }
    this.props.startAddGitRepo(gitRepo)
  }
  onEdit = (gitRepo: GitRepo) => {
    this.props.startEditGitRepo(gitRepo)
  }
  onRemove = (name: string) => {
    this.props.startRemoveGitRepo(name)
  }
  render() {
    const { gitRepos } = this.props
    return (
      <div>
        <h1>GitRepo Page</h1>
        <button onClick={() => this.onAdd()}>Add a repo</button>
        <div>
          {gitRepos.map((gitRepo) => (
            <div>
              <p>{gitRepo.name}</p>
              <p>{gitRepo.url}</p>
              <p>{gitRepo.analyzeState}</p>
              <button onClick={() => this.onRemove(gitRepo.name)}>Remove gitRepo</button>
              <button onClick={() => this.onEdit(gitRepo)}>Edit gitRepo</button>
            </div>
          ))}
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state: AppState, ownProps: HomePageProps): LinkStateProp => ({
  gitRepos: state.gitRepos
})

const mapDispatchToProps = (dispatch: ThunkDispatch<any, any, AppActions>, ownProps: HomePageProps): LinkDispatchProp => ({
  startAddGitRepo: bindActionCreators(startAddGitRepo, dispatch),
  startEditGitRepo: bindActionCreators(startEditGitRepo, dispatch),
  startRemoveGitRepo: bindActionCreators(startRemoveGitRepo, dispatch)
})

export const HomePage = connect(mapStateToProps, mapDispatchToProps)(HomePageComponent)
