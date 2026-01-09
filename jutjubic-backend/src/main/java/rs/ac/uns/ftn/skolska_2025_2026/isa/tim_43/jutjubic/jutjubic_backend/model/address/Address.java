package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
*/
@Entity()
@Table(name = "addresses")
public class Address implements Comparable<Address> {
	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-numeric.html */
	@Id()
	@SequenceGenerator(name = "generator_of_addresses_ids", sequenceName = "addresses_id_seq", 
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_of_addresses_ids")
	@Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigserial")
	private long id;

	@Column(name = "street", nullable = true)
	private String street;

	@Column(name = "number", nullable = true)
	private String number;

	@Column(name = "postal_code", nullable = true)
	private String postalCode;

	@Column(name = "place", nullable = false)
	private String place;

	@Column(name = "country", nullable = false)
	private String country;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	public Address() {}

	public Address(long id, String street, String number, String postalCode, String place, 
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

	/** REFERENCE: https://www.geeksforgeeks.org/java/comparable-interface-in-java-with-examples/ */
	@Override()
	public int compareTo(Address o) {
		if (this == o) {
			return 0;
		}

		int comparisonValue = Long.compare(id, o.id);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = street.compareTo(o.street);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = number.compareTo(o.number);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = postalCode.compareTo(o.postalCode);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = place.compareTo(o.place);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = country.compareTo(o.country);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = Double.compare(latitude, o.latitude);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = Double.compare(longitude, o.longitude);
		if (comparisonValue != 0) {
			return comparisonValue;
		}

		return 0;
	}

	@Override()
	public int hashCode() {
		return Objects.hash(id, street, number, postalCode, place, country, latitude, longitude);
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Address)) {
			return false;
		}

		Address other = (Address) obj;

		return id == other.id 
				&& Objects.equals(street, other.street) 
				&& Objects.equals(number, other.number) 
				&& Objects.equals(postalCode, other.postalCode) 
				&& Objects.equals(place, other.place) 
				&& Objects.equals(country, other.country) 
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude) 
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude);
	}

	@Override()
	public String toString() {
		StringBuilder builder = new StringBuilder("Address [");

		builder.append("id = ").append(id)
				.append(", street = ").append(street).append(", number = ").append(number)
				.append(", postalCode = ").append(postalCode).append(", place = ").append(place)
				.append(", country = ").append(country)
				.append(", latitude = ").append(latitude)
				.append(", longitude = ").append(longitude)
				.append("]");

		return builder.toString();
	}
}
