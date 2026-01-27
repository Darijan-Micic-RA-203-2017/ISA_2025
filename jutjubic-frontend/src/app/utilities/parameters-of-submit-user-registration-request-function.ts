import { WritableSignal } from "@angular/core";
import { Router } from "@angular/router";

import { MatSnackBar } from "@angular/material/snack-bar";

import { UserRegistrationRequest } from "../model/user/user-registration-request";

export class ParametersOfSubmitUserRegistrationRequestFunction {
	private userRegistrationRequest: UserRegistrationRequest;
	/** REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private isRegistrationFormSubmitted: WritableSignal<boolean>;
	private snackBar: MatSnackBar;
	private router: Router;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(userRegistrationRequest: UserRegistrationRequest, isRegistrationFormSubmitted: WritableSignal<boolean>, 
			snackBar: MatSnackBar, router: Router) {
		this.userRegistrationRequest = userRegistrationRequest;
		this.isRegistrationFormSubmitted = isRegistrationFormSubmitted;
		this.snackBar = snackBar;
		this.router = router;
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

	/** REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	public setIsRegistrationFormSubmitted(isRegistrationFormSubmitted: boolean): void {
		this.isRegistrationFormSubmitted.set(isRegistrationFormSubmitted);
	}

	public getSnackBar(): MatSnackBar {
		return this.snackBar;
	}

	public getRouter(): Router {
		return this.router;
	}
}
