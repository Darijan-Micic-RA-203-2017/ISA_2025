import { WritableSignal } from "@angular/core";
import { Router } from "@angular/router";

import { MatSnackBar } from "@angular/material/snack-bar";

import { UserCredentials } from "../../model/user/user-credentials";

export class ParametersOfSubmitUserCredentialsFunction {
	private userCredentials: UserCredentials;
	/* REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private isLoggingOnFormSubmitted: WritableSignal<boolean>;
	private snackBar: MatSnackBar;
	private router: Router;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(userCredentials: UserCredentials, isLoggingOnFormSubmitted: WritableSignal<boolean>, snackBar: MatSnackBar, 
			router: Router) {
		this.userCredentials = userCredentials;
		this.isLoggingOnFormSubmitted = isLoggingOnFormSubmitted;
		this.snackBar = snackBar;
		this.router = router;
	}

	public getUserCredentials(): UserCredentials {
		return this.userCredentials;
	}

	public setUserCredentials(userCredentials: UserCredentials): void {
		this.userCredentials = userCredentials;
	}

	public getIsLoggingOnFormSubmitted(): WritableSignal<boolean> {
		return this.isLoggingOnFormSubmitted;
	}

	/** REFERENCES:<br />
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing<br />
	 * https://angular.dev/essentials/signals<br />
	 * https://angular.dev/guide/signals
	*/
	public setIsLoggingOnFormSubmitted(isLoggingOnFormSubmitted: boolean): void {
		this.isLoggingOnFormSubmitted.set(isLoggingOnFormSubmitted);
	}

	public getSnackBar(): MatSnackBar {
		return this.snackBar;
	}

	public getRouter(): Router {
		return this.router;
	}
}
