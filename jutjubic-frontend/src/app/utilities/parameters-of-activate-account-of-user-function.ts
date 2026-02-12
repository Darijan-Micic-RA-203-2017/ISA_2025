import { WritableSignal } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

import { WrappedEncodedId } from "../model/user/wrapped-encoded-id";

export class ParametersOfActivateAccountOfUserFunction {
	private wrappedEncodedId: WrappedEncodedId;
	/* REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private hasAccountBeenActivated: WritableSignal<boolean>;
	private snackBar: MatSnackBar;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(wrappedEncodedId: WrappedEncodedId, hasAccountBeenActivated: WritableSignal<boolean>, snackBar: MatSnackBar) {
		this.wrappedEncodedId = wrappedEncodedId;
		this.hasAccountBeenActivated = hasAccountBeenActivated;
		this.snackBar = snackBar;
	}

	public getWrappedEncodedId(): WrappedEncodedId {
		return this.wrappedEncodedId;
	}

	public setWrappedEncodedId(wrappedEncodedId: WrappedEncodedId): void {
		this.wrappedEncodedId = wrappedEncodedId;
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
