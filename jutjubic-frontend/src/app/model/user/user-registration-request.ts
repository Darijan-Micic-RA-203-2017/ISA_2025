import { Address } from "../address/address";

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class UserRegistrationRequest {
	private emailAddress: string;
	private username: string;
	private password: string;
	private firstName: string;
	private lastName: string;
	private address: Address;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.emailAddress = '';
			this.username = '';
			this.password = '';
			this.firstName = '';
			this.lastName = '';
			this.address = new Address(null);

			return;
		}

		this.emailAddress = value['emailAddress'];
		this.username = value['username'];
		this.password = value['password'];
		this.firstName = value['firstName'];
		this.lastName = value['lastName'];
		this.address = value['address'];
	}

	public getEmailAddress(): string {
		return this.emailAddress;
	}

	public setEmailAddress(emailAddress: string): void {
		this.emailAddress = emailAddress;
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

	public getFirstName(): string {
		return this.firstName;
	}

	public setFirstName(firstName: string): void {
		this.firstName = firstName;
	}

	public getLastName(): string {
		return this.lastName;
	}

	public setLastName(lastName: string): void {
		this.lastName = lastName;
	}

	public getAddress(): Address {
		return this.address;
	}

	public setAddress(address: Address): void {
		this.address = address;
	}
}
