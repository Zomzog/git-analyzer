import React from 'react'
import { Router, Route, Link } from 'react-router-dom'
import createHistory from 'history/createBrowserHistory'
import { HomePage } from '../components/HomePage'
import { AnalyzePage } from '../components/AnalyzePage/AnalyzePage'
import { AddGitRepoPage } from '../components/AddGitRepo/AddGitRepoPage'

export const history = createHistory()

// Instead of BrowserRouter, we use the regular router,
// but we pass in a customer history to it.
const AppRouter = () => (
  <Router history={history}>
    <div>
      <header>Git repo analyzer</header>
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/addRepo">Add a repo</Link>
          </li>
          <li>
            <Link to="/analyze">Analyze page</Link>
          </li>
        </ul>
      </nav>

      <Route path="/" component={HomePage} exact />
      <Route path="/addRepo" component={AddGitRepoPage} exact />
      <Route path="/analyze" component={AnalyzePage} exact />
    </div>
  </Router>
)

export default AppRouter
