/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export class NumberOfLettersValidationError {
	private requiredMinNumberOfLetters: number;
	private actualNumberOfLetters: number;
	private requiredMaxNumberOfLetters: number;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(requiredMinNumberOfLetters: number, actualNumberOfLetters: number, requiredMaxNumberOfLetters: number) {
		this.requiredMinNumberOfLetters = requiredMinNumberOfLetters;
		this.actualNumberOfLetters = actualNumberOfLetters;
		this.requiredMaxNumberOfLetters = requiredMaxNumberOfLetters;
	}

	public getRequiredMinNumberOfLetters(): number {
		return this.requiredMinNumberOfLetters;
	}

	public setRequiredMinNumberOfLetters(requiredMinNumberOfLetters: number): void {
		this.requiredMinNumberOfLetters = requiredMinNumberOfLetters;
	}

	public getActualNumberOfLetters(): number {
		return this.actualNumberOfLetters;
	}

	public setActualNumberOfLetters(actualNumberOfLetters: number): void {
		this.actualNumberOfLetters = actualNumberOfLetters;
	}

	public getRequiredMaxNumberOfLetters(): number {
		return this.requiredMaxNumberOfLetters;
	}

	public setRequiredMaxNumberOfLetters(requiredMaxNumberOfLetters: number): void {
		this.requiredMaxNumberOfLetters = requiredMaxNumberOfLetters;
	}
}
