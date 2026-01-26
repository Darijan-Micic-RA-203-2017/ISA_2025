import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

import { NumberOfLettersValidationError } from '../utilities/number-of-letters-validation-error';
import { WrappedNumberOfLettersValidationError } from '../utilities/wrapped-number-of-letters-validation-error';

/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export function numberOfLettersValidator(minLetters: number, maxLetters?: number): ValidatorFn {
	return (control: AbstractControl<string | null, string | null, string | null>): ValidationErrors | null => {
		if (maxLetters == undefined) {
			maxLetters = 0;
		}
		let wrappedNumberOfLettersValidationError: WrappedNumberOfLettersValidationError = 
				new WrappedNumberOfLettersValidationError(new NumberOfLettersValidationError(minLetters, 0, maxLetters))

		let valueEnteredIntoFormControl: string | null = control.value;
		if (valueEnteredIntoFormControl == null) {
			return wrappedNumberOfLettersValidationError;
		}

		const regularExpression: RegExp = /\p{L}/u;
		let numberOfLetters: number = 0;
		for (let i = 0; i < valueEnteredIntoFormControl.length; i++) {
			let character: string = valueEnteredIntoFormControl.substring(i, i + 1);
			if (regularExpression.test(character)) {
				numberOfLetters++;
			}
		}

		if (numberOfLetters == 0) {
			return wrappedNumberOfLettersValidationError;
		}

		let containingMoreThenOrExactlyMinLetters: boolean = true;
		if (minLetters > 0) {
			containingMoreThenOrExactlyMinLetters = numberOfLetters >= minLetters;
		}
		let containingLessThenOrExactlyMaxLetters: boolean = true;
		if (maxLetters > 0) {
			containingLessThenOrExactlyMaxLetters = numberOfLetters <= maxLetters;
		}

		if (containingMoreThenOrExactlyMinLetters && containingLessThenOrExactlyMaxLetters) {
			return null;
		}

		wrappedNumberOfLettersValidationError.getNumberOfLetters().setActualNumberOfLetters(numberOfLetters);
		return wrappedNumberOfLettersValidationError;
	}
}
