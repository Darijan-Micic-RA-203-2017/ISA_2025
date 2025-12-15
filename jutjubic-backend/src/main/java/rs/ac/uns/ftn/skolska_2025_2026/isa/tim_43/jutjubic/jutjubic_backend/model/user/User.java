package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public class User implements Comparable<User> {
	private long id;
	private boolean enabled;
	private Set<UserRole> roles;
	private String emailAddress;
	private String username;
	private String password;
	private Timestamp dateOfLastPasswordReset;
	private String firstName;
	private String lastName;
	private Address address;

	public User() {}

	public User(long id, boolean enabled, Set<UserRole> roles, String emailAddress, 
			String username, String password, Timestamp dateOfLastPasswordReset, 
			String firstName, String lastName, Address address) {
		this.id = id;
		this.enabled = enabled;
		this.roles = roles;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
		this.dateOfLastPasswordReset = dateOfLastPasswordReset;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDateOfLastPasswordReset() {
		return dateOfLastPasswordReset;
	}

	public void setDateOfLastPasswordReset(Timestamp dateOfLastPasswordReset) {
		this.dateOfLastPasswordReset = dateOfLastPasswordReset;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public int compareRolesWith(User otherUser) {
		int comparisonValue = 0;

		boolean endOfRolesLoopReached = false;
		Iterator<UserRole> iteratorOverRoles = roles.iterator();
		Iterator<UserRole> iteratorOverRolesOfSpecifiedUser = otherUser.roles.iterator();
		while (!endOfRolesLoopReached) {
			if (iteratorOverRolesOfSpecifiedUser.hasNext()) {
				if (iteratorOverRoles.hasNext()) {
					comparisonValue = iteratorOverRoles.next()
							.compareTo(iteratorOverRolesOfSpecifiedUser.next());
					if (comparisonValue != 0) {
						return comparisonValue;
					}
				} else {
					comparisonValue = -1;
					endOfRolesLoopReached = true;
				}
			} else {
				comparisonValue = 1;
				endOfRolesLoopReached = true;
			}
		}

		return comparisonValue;
	}

	/** REFERENCE: https://www.geeksforgeeks.org/java/comparable-interface-in-java-with-examples/ */
	@Override()
	public int compareTo(User o) {
		if (this == o) {
			return 0;
		}

		int comparisonValue = Long.compare(id, o.id);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = Boolean.compare(enabled, o.enabled);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = compareRolesWith(o);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = emailAddress.compareTo(o.emailAddress);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = username.compareTo(o.username);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = password.compareTo(o.password);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = dateOfLastPasswordReset.compareTo(o.dateOfLastPasswordReset);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = firstName.compareTo(o.firstName);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = lastName.compareTo(o.lastName);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = address.compareTo(o.address);
		if (comparisonValue != 0) {
			return comparisonValue;
		}

		return 0;
	}

	@Override()
	public int hashCode() {
		return Objects.hash(id, enabled, roles, emailAddress, username, password, 
				dateOfLastPasswordReset, firstName, lastName, address);
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof User)) {
			return false;
		}

		User other = (User) obj;

		return id == other.id && enabled == other.enabled 
				&& Objects.equals(roles, other.roles) 
				&& Objects.equals(emailAddress, other.emailAddress) 
				&& Objects.equals(username, other.username) 
				&& Objects.equals(password, other.password) 
				&& Objects.equals(dateOfLastPasswordReset, other.dateOfLastPasswordReset) 
				&& Objects.equals(firstName, other.firstName)  
				&& Objects.equals(lastName, other.lastName) 
				&& Objects.equals(address, other.address);
	}

	@Override()
	public String toString() {
		StringBuilder builder = new StringBuilder("User [");

		builder.append("id = ").append(id).append(", enabled = ").append(enabled)
				.append(", roles = ").append(roles)
				.append(", emailAddress = ").append(emailAddress)
				.append(", username = ").append(username)
				.append(", password = ").append(password)
				.append(", dateOfLastPasswordReset = ").append(dateOfLastPasswordReset)
				.append(", firstName = ").append(firstName)
				.append(", lastName = ").append(lastName)
				.append(", address = ").append(address)
				.append("]");

		return builder.toString();
	}
}
