/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class TokenWithLifeDuration {
	private token: string;
	private lifeDurationOfTokenInMilliseconds: number;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
    */
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.token = '';
			this.lifeDurationOfTokenInMilliseconds = 0;

			return;
		}

		this.token = value['token'];
		this.lifeDurationOfTokenInMilliseconds = value['lifeDurationOfTokenInMilliseconds'];
	}

	public getToken(): string {
		return this.token;
	}

	public setToken(token: string): void {
		this.token = token;
	}

	public getLifeDurationOfTokenInMilliseconds(): number {
		return this.lifeDurationOfTokenInMilliseconds;
	}

	public setLifeDurationOfTokenInMilliseconds(lifeDurationOfTokenInMilliseconds: number): void {
		this.lifeDurationOfTokenInMilliseconds = lifeDurationOfTokenInMilliseconds;
	}
}
