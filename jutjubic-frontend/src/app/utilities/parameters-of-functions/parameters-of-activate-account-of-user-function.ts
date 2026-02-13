import { WritableSignal } from "@angular/core";

import { WrappedDigestedIdentificator } from "../../model/user/wrapped-digested-identificator";

export class ParametersOfActivateAccountOfUserFunction {
	private wrappedDigestedIdentificator: WrappedDigestedIdentificator;
	/* REFERENCES:
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing
	 * https://angular.dev/essentials/signals
	 * https://angular.dev/guide/signals
	*/
	private hasAccountBeenActivated: WritableSignal<boolean | null>;
	private reasonForFailureOfAccountActivation: WritableSignal<string | null>;

	/** REFERENCE: https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing */
	constructor(wrappedDigestedIdentificator: WrappedDigestedIdentificator, 
			hasAccountBeenActivated: WritableSignal<boolean | null>, 
			reasonForFailureOfAccountActivation: WritableSignal<string | null>) {
		this.wrappedDigestedIdentificator = wrappedDigestedIdentificator;
		this.hasAccountBeenActivated = hasAccountBeenActivated;
		this.reasonForFailureOfAccountActivation = reasonForFailureOfAccountActivation;
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
	public setHasAccountBeenActivated(hasAccountBeenActivated: boolean | null): void {
		this.hasAccountBeenActivated.set(hasAccountBeenActivated);
	}

	/** REFERENCES:<br />
	 * https://www.programfarmer.com/en-US/articles/2021/javascript-pass-by-value-pass-by-reference-pass-by-sharing<br />
	 * https://angular.dev/essentials/signals<br />
	 * https://angular.dev/guide/signals
	*/
	public setReasonForFailureOfAccountActivation(reasonForFailureOfAccountActivation: string | null): void {
		this.reasonForFailureOfAccountActivation.set(reasonForFailureOfAccountActivation);
	}
}
