import { WritableSignal } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

import { WrappedDigestedIdentificator } from "../model/user/wrapped-digested-identificator";

export class ParametersOfActivateAccountOfUserFunction {
	private wrappedDigestedIdentificator: WrappedDigestedIdentificator;
	/* REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private hasAccountBeenActivated: WritableSignal<boolean>;
	private snackBar: MatSnackBar;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(wrappedDigestedIdentificator: WrappedDigestedIdentificator, hasAccountBeenActivated: WritableSignal<boolean>, 
			snackBar: MatSnackBar) {
		this.wrappedDigestedIdentificator = wrappedDigestedIdentificator;
		this.hasAccountBeenActivated = hasAccountBeenActivated;
		this.snackBar = snackBar;
	}

	public getWrappedDigestedIdentificator(): WrappedDigestedIdentificator {
		return this.wrappedDigestedIdentificator;
	}

	public setWrappedDigestedIdentificator(wrappedDigestedIdentificator: WrappedDigestedIdentificator): void {
		this.wrappedDigestedIdentificator = wrappedDigestedIdentificator;
	}

	/** REFERENCES:<br />
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing<br />
	 * https://angular.dev/essentials/signals<br />
	 * https://angular.dev/guide/signals
	*/
	public setHasAccountBeenActivated(hasAccountBeenActivated: boolean): void {
		this.hasAccountBeenActivated.set(hasAccountBeenActivated);
	}

	public getSnackBar(): MatSnackBar {
		return this.snackBar;
	}
}
