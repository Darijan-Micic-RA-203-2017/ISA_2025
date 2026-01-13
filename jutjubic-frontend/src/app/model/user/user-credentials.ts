/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class UserCredentials {
	private username: string;
	private password: string;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.username = '';
			this.password = '';

			return;
		}

		this.username = value['username'];
		this.password = value['password'];
	}

	public getUsername(): string {
		return this.username;
	}

	public setUsername(username: string): void {
		this.username = username;
	}

	public getPassword(): string {
		return this.password;
	}

	public setPassword(password: string): void {
		this.password = password;
	}
}
