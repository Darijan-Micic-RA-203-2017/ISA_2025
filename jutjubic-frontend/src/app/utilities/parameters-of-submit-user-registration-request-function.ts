import { WritableSignal } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

import { UserRegistrationRequest } from "../model/user/user-registration-request";

export class ParametersOfSubmitUserRegistrationRequestFunction {
	private userRegistrationRequest: UserRegistrationRequest;
	/* REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private isRegistrationFormSubmitted: WritableSignal<boolean>;
	private hasEmailMessageForAccountActivationBeenSent: WritableSignal<boolean>;
	private snackBar: MatSnackBar;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(userRegistrationRequest: UserRegistrationRequest, isRegistrationFormSubmitted: WritableSignal<boolean>, 
			hasEmailMessageForAccountActivationBeenSent: WritableSignal<boolean>, snackBar: MatSnackBar) {
		this.userRegistrationRequest = userRegistrationRequest;
		this.isRegistrationFormSubmitted = isRegistrationFormSubmitted;
		this.hasEmailMessageForAccountActivationBeenSent = hasEmailMessageForAccountActivationBeenSent;
		this.snackBar = snackBar;
	}

	public getUserRegistrationRequest(): UserRegistrationRequest {
		return this.userRegistrationRequest;
	}

	public setUserRegistrationRequest(userRegistrationRequest: UserRegistrationRequest): void {
		this.userRegistrationRequest = userRegistrationRequest;
	}

	public getIsRegistrationFormSubmitted(): WritableSignal<boolean> {
		return this.isRegistrationFormSubmitted;
	}

	/** REFERENCES:<br />
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing<br />
	 * https://angular.dev/essentials/signals<br />
	 * https://angular.dev/guide/signals
	*/
	public setIsRegistrationFormSubmitted(isRegistrationFormSubmitted: boolean): void {
		this.isRegistrationFormSubmitted.set(isRegistrationFormSubmitted);
	}

	/** REFERENCES:<br />
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing<br />
	 * https://angular.dev/essentials/signals<br />
	 * https://angular.dev/guide/signals
	*/
	public setHasEmailMessageForAccountActivationBeenSent(hasEmailMessageForAccountActivationBeenSent: boolean): void {
		this.hasEmailMessageForAccountActivationBeenSent.set(hasEmailMessageForAccountActivationBeenSent);
	}

	public getSnackBar(): MatSnackBar {
		return this.snackBar;
	}
}
