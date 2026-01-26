import { NumberOfLettersValidationError } from "./number-of-letters-validation-error";

/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export class WrappedNumberOfLettersValidationError {
	private numberOfLetters: NumberOfLettersValidationError;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(numberOfLetters: NumberOfLettersValidationError) {
		this.numberOfLetters = numberOfLetters;
	}

	public getNumberOfLetters(): NumberOfLettersValidationError {
		return this.numberOfLetters;
	}

	public setNumberOfLetters(numberOfLetters: NumberOfLettersValidationError): void {
		this.numberOfLetters = numberOfLetters;
	}
}
