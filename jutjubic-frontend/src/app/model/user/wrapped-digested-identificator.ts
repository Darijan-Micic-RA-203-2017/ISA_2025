export class WrappedDigestedIdentificator {
	private digestedIdentificator: string;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.digestedIdentificator = '';

			return;
		}

		this.digestedIdentificator = value['digestedIdentificator'];
	}

	public getDigestedIdentificator(): string {
		return this.digestedIdentificator;
	}

	public setDigestedIdentificator(digestedIdentificator: string): void {
		this.digestedIdentificator = digestedIdentificator;
	}
}
