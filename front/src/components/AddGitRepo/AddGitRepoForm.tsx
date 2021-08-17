import { Button, TextField } from '@material-ui/core';
import React from 'react'
import { Field, InjectedFormProps, reduxForm, WrappedFieldProps } from 'redux-form';
import { addGitRepoValidation } from './AddGitRepoValidation';

export interface IAddGitRepoFormState {
  name: string,
  url: string
}

export interface IAddGitRepoFormDispatch {
}

interface IFieldProps extends WrappedFieldProps {
  label: string
}

const renderTextField = (props: IFieldProps) => (
  <TextField
    label={props.label}
    placeholder={props.label}
    error={props.meta.touched && props.meta.invalid}
    helperText={props.meta.touched && props.meta.error}

    {...props.input}
  />
)

const FormComponent = (props: IAddGitRepoFormDispatch & InjectedFormProps<IAddGitRepoFormState, IAddGitRepoFormDispatch>) => (
  <div>
    <div>Add Git Repo page</div>
    <form onSubmit={props.handleSubmit}>
      <Field name="name" component={renderTextField} required label="name" />
      <Field name="url" component={renderTextField} required label="url" />
      <Button type="submit"
        disabled={!props.valid || props.pristine || props.submitting}
      >Add</Button>
    </form>
  </div>
);


export const AddGitRepoForm = reduxForm<IAddGitRepoFormState, IAddGitRepoFormDispatch>({
  form: 'addGitRepoForm',
  validate: addGitRepoValidation,

})(FormComponent);