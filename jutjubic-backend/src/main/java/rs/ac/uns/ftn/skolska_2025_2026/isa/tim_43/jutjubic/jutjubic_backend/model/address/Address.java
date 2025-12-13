package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address;

import java.util.Objects;

public class Address {
	private long id;
	private String street;
	private String number;
	private String postalCode;
	private String place;
	private String country;
	private double latitude;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, street, number, postalCode, place, country, latitude, longitude);
	}

	@Override
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

	@Override
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
