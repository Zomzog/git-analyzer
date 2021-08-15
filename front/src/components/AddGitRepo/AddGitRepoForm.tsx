import React from 'react'
import { Field, InjectedFormProps, reduxForm } from 'redux-form';
import { addGitRepoValidation } from './AddGitRepoValidation';

export interface IAddGitRepoFormState {
  name: string,
  url: string
}

export interface IAddGitRepoFormDispatch {
}

const FormComponent = (props: IAddGitRepoFormDispatch & InjectedFormProps<IAddGitRepoFormState, IAddGitRepoFormDispatch>) => (
  <div>
    <div>Add Git Repo page</div>
    <form onSubmit={props.handleSubmit}>
      <div>
        <label htmlFor="name">Name</label>
        <Field name="name" component="input" type="text" />
      </div>
      <div>
        <label htmlFor="url">Url</label>
        <Field name="url" component="input" type="text" />
      </div>
      <button type="submit"
      //disabled={!props.valid || props.pristine || props.submitting}
      >Add</button>
    </form>
  </div>
);


export const AddGitRepoForm = reduxForm<IAddGitRepoFormState, IAddGitRepoFormDispatch>({
  form: 'addGitRepoForm',
  validate: addGitRepoValidation,

})(FormComponent);