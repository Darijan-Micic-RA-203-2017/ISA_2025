import { AbstractControl, FormArray, FormGroup, ValidationErrors, ValidatorFn } from "@angular/forms";

import { RepeatedPasswordValidationError } from "./repeated-password-validation-error";
import { WrappedRepeatedPasswordValidationError } from "./wrapped-repeated-password-validation-error";

/** REFERENCES:<br />
 * https://angular.dev/guide/forms/form-validation<br />
 * https://blog.angular-university.io/angular-custom-validators/
*/
export function repeatedPasswordValidator(): ValidatorFn {
	return (control: AbstractControl<string, string, string>): ValidationErrors | null => {
		let registrationFormGroup: FormGroup<any> | FormArray<any> | null = control.parent;
		if (registrationFormGroup == null) {
			return null;
		}

		let enteredPassword: string = registrationFormGroup.get('passwordControl')?.value;
		let enteredRepeatedPassword: string = registrationFormGroup.get('repeatedPasswordControl')?.value;
		if (enteredPassword !== '' || enteredRepeatedPassword !== '') {
			if (enteredPassword !== enteredRepeatedPassword) {
				return new WrappedRepeatedPasswordValidationError(
						new RepeatedPasswordValidationError(enteredPassword, enteredRepeatedPassword));
			}
		}

		return null;
	};
}
