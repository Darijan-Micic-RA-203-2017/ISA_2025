package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex<br />
 * https://forum.knime.com/t/string-manipulation-multi-column-regex-patternsyntaxexception-illegal-repetition/60894/4
*/
public class AddressDTO {
	@PositiveOrZero(message = "The id has to be positive or zero!")
	private long id;

	private String street;

	private String number;

	private String postalCode;

	@NotBlank(message = "The place has to be non-blank!")
	private String place;

	@NotBlank(message = "The country has to be non-blank!")
	@Pattern(regexp = "^\\p{Lu}\\p{Ll}+( \\p{Lu}\\p{Ll}+)?$", 
			flags = {Pattern.Flag.UNICODE_CASE}, 
			message = "The country has to contain capitalized words and " 
					+ "has to not contain any non-letters, except spaces!")
	private String country;

	private double latitude;

	private double longitude;

	public AddressDTO() {}

	public AddressDTO(long id, String street, String number, String postalCode, String place, 
			String country, double latitude, double longitude) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.postalCode = postalCode;
		this.place = place;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.street = address.getStreet();
		this.number = address.getNumber();
		this.postalCode = address.getPostalCode();
		this.place = address.getPlace();
		this.country = address.getCountry();
		this.latitude = address.getLatitude();
		this.longitude = address.getLongitude();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
