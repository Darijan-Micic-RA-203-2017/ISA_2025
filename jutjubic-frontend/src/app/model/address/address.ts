export class Address {
	private id: number;
	private street: string | null;
	private number: string | null;
	private postalCode: string | null;
	private place: string;
	private country: string;
	private latitude: number;
	private longitude: number;

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/56410007/cast-angular-http-response-into-class<br />
	 * https://stackoverflow.com/questions/51763745/angular-6-error-typeerror-is-not-a-function-but-it-is
	*/
	constructor(value: any) {
		if (value == undefined || value == null || typeof value != "object") {
			this.id = 0;
			this.street = null;
			this.number = null;
			this.postalCode = null;
			this.place = '';
			this.country = '';
			this.latitude = 0.0;
			this.longitude = 0.0;

			return;
		}

		this.id = value['id'];
		this.street = value['street'];
		this.number = value['number'];
		this.postalCode = value['postalCode'];
		this.place = value['place'];
		this.country = value['country'];
		this.latitude = value['latitude'];
		this.longitude = value['longitude'];
	}

	public getId(): number {
		return this.id;
	}

	public setId(id: number): void {
		this.id = id;
	}

	public getStreet(): string | null {
		return this.street;
	}

	public setStreet(street: string | null): void {
		this.street = street;
	}

	public getNumber(): string | null {
		return this.number;
	}

	public setNumber(number: string | null): void {
		this.number = number;
	}

	public getPostalCode(): string | null {
		return this.postalCode;
	}

	public setPostalCode(postalCode: string | null): void {
		this.postalCode = postalCode;
	}

	public getPlace(): string {
		return this.place;
	}

	public setPlace(place: string): void {
		this.place = place;
	}

	public getCountry(): string {
		return this.country;
	}

	public setCountry(country: string): void {
		this.country = country;
	}

	public getLatitude(): number {
		return this.latitude;
	}

	public setLatitude(latitude: number): void {
		this.latitude = latitude;
	}

	public getLongitude(): number {
		return this.longitude;
	}

	public setLongitude(longitude: number): void {
		this.longitude = longitude;
	}
}
