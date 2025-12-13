package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user;

import java.util.Objects;

public class UserRole implements Comparable<UserRole> {
	private long id;
	private String name;

	public UserRole() {}

	public UserRole(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(UserRole o) {
		if (this == o) {
			return 0;
		}

		int comparisonValue = Long.compare(id, o.id);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = name.compareTo(o.name);
		if (comparisonValue != 0) {
			return comparisonValue;
		}

		return 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserRole)) {
			return false;
		}

		UserRole other = (UserRole) obj;

		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("UserRole [");

		builder.append("id = ").append(id).append(", name = ").append(name)
				.append("]");

		return builder.toString();
	}
}
