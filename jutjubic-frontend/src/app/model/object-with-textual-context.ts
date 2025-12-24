export class ObjectWithTextualContext {
	private object: any;
	private textualContext: string;

	constructor(object: any, textualContext: string) {
		this.object = object;
		this.textualContext = textualContext;
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
