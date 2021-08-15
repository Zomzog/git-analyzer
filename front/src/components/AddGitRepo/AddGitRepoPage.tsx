import React, { Component } from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { ThunkDispatch } from 'redux-thunk'
import { startAddGitRepo } from '../../actions/GitRepoActions'
import { AppState } from '../../store/configureStore'
import { AppActions } from '../../types/actions'
import { GitRepo } from '../../types/GitRepo'
import { AddGitRepoForm, IAddGitRepoFormState } from './AddGitRepoForm'

interface IAddGitRepoState { }

interface IAddGitRepoProps { }

interface LinkDispatchProp {
    startAddGitRepo: (gitRepo: GitRepo) => void
}

interface LinkStateProp {
    gitRepos: GitRepo[]
}

type Props = IAddGitRepoState & LinkDispatchProp & LinkStateProp

export class AddGitRepoPageComponent extends Component<Props, IAddGitRepoState> {
    submit = (values: IAddGitRepoFormState) => {
        const gitRepo: GitRepo = { name: values.name, url: values.url, analyzeState: 'analyzeState' }
        this.props.startAddGitRepo(gitRepo)
    }
    render() {
        return <AddGitRepoForm onSubmit={this.submit} />
    }
}

const mapStateToProps = (state: AppState, ownProps: IAddGitRepoProps): LinkStateProp => ({
    gitRepos: state.gitRepos
})

const mapDispatchToProps = (dispatch: ThunkDispatch<any, any, AppActions>, ownProps: IAddGitRepoProps): LinkDispatchProp => ({
    startAddGitRepo: bindActionCreators(startAddGitRepo, dispatch),
})

export const AddGitRepoPage = connect(mapStateToProps, mapDispatchToProps)(AddGitRepoPageComponent)
