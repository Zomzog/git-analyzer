import { IAddGitRepoFormState } from "./AddGitRepoForm";

interface IErrorsForm {
  name?:string;
  url?:string;
}

export const addGitRepoValidation = (values:IAddGitRepoFormState) => {
  const errors:IErrorsForm = {};

  if(!values.name) {
    errors.name = "Repository name";
  }

  if(!values.url) {
    errors.name = "Url";
  }

  console.log("Errors " + JSON.stringify(errors))
  return errors;
};