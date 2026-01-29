import { RepeatedPasswordValidationError } from "./repeated-password-validation-error";

/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export class WrappedRepeatedPasswordValidationError {
	private passwordIsNotRepeated: RepeatedPasswordValidationError;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(passwordIsNotRepeated: RepeatedPasswordValidationError) {
		this.passwordIsNotRepeated = passwordIsNotRepeated;
	}

	public getPasswordIsNotRepeated(): RepeatedPasswordValidationError {
		return this.passwordIsNotRepeated;
	}

	public setPasswordIsNotRepeated(passwordIsNotRepeated: RepeatedPasswordValidationError): void {
		this.passwordIsNotRepeated = passwordIsNotRepeated;
	}
}
