import { Address } from "../address/address";
import { UserRole } from "./user-role";

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
export class User {
	private id: number;
	private enabled: boolean;
	private roles: UserRole[];
	private emailAddress: string;
	private username: string;
	private firstName: string;
	private lastName: string;
	private address: Address;
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
			this.enabled = false;
			this.roles = [];
			this.emailAddress = '';
			this.username = '';
			this.firstName = '';
			this.lastName = '';
			this.address = new Address(null);
			this.version = 1;

			return;
		}

		this.id = value['id'];
		this.enabled = value['enabled'];
		this.roles = value['roles'];
		this.emailAddress = value['emailAddress'];
		this.username = value['username'];
		this.firstName = value['firstName'];
		this.lastName = value['lastName'];
		this.address = value['address'];
		this.version = value['version'];
	}

	public getId(): number {
		return this.id;
	}

	public setId(id: number): void {
		this.id = id;
	}

	public getEnabled(): boolean {
		return this.enabled;
	}

	public setEnabled(enabled: boolean): void {
		this.enabled = enabled;
	}

	public getRoles(): UserRole[] {
		return this.roles;
	}

	public setRoles(roles: UserRole[]): void {
		this.roles = roles;
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

	public getVersion(): number {
		return this.version;
	}

	public setVersion(version: number): void {
		this.version = version;
	}
}
