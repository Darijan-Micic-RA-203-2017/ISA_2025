export class WrappedEncodedId {
	private encodedId: string;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.encodedId = '';

			return;
		}

		this.encodedId = value['encodedId'];
	}

	public getEncodedId(): string {
		return this.encodedId;
	}

	public setEncodedId(encodedId: string): void {
		this.encodedId = encodedId;
	}
}
