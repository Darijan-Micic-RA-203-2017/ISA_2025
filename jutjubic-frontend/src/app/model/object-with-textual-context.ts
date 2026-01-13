export class ObjectWithTextualContext {
	private object: any;
	private textualContext: string;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.object = null;
			this.textualContext = '';

			return;
		}

		this.object = value['object'];
		this.textualContext = value['textualContext'];
	}

	public getObject(): any {
		return this.object;
	}

	public setObject(object: any): void {
		this.object = object;
	}

	public getTextualContext(): string {
		return this.textualContext;
	}

	public setTextualContext(textualContext: string): void {
		this.textualContext = textualContext;
	}
}
