/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export class RepeatedPasswordValidationError {
	private enteredPassword: string;
	private enteredRepeatedPassword: string;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(enteredPassword: string, enteredRepeatedPassword: string) {
		this.enteredPassword = enteredPassword;
		this.enteredRepeatedPassword = enteredRepeatedPassword;
	}

	public getEnteredPassword(): string {
		return this.enteredPassword;
	}

	public setEnteredPassword(enteredPassword: string): void {
		this.enteredPassword = enteredPassword;
	}

	public getEnteredRepeatedPassword(): string {
		return this.enteredRepeatedPassword;
	}

	public setEnteredRepeatedPassword(enteredRepeatedPassword: string): void {
		this.enteredRepeatedPassword = enteredRepeatedPassword;
	}
}
