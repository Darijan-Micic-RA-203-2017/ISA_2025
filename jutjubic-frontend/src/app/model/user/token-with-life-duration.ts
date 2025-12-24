/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class TokenWithLifeDuration {
	private token: string;
	private lifeDurationOfTokenInMilliseconds: number;

	constructor(token: string, lifeDurationOfTokenInMilliseconds: number) {
		this.token = token;
		this.lifeDurationOfTokenInMilliseconds = lifeDurationOfTokenInMilliseconds;
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
