/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class UserRole {
	private id: number;
	private name: string;
	/** REFERENCES:<br />
	 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
	 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
	*/
	private version: number;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.id = 0;
			this.name = '';
			this.version = 1;

			return;
		}

		this.id = value['id'];
		this.name = value['name'];
		this.version = value['version'];
	}

	public getId(): number {
		return this.id;
	}

	public setId(id: number): void {
		this.id = id;
	}

	public getName(): string {
		return this.name;
	}

	public setName(name: string): void {
		this.name = name;
	}

	public getVersion(): number {
		return this.version;
	}

	public setVersion(version: number): void {
		this.version = version;
	}
}
