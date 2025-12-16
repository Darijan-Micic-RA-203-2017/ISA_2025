package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;

/** REFERENCES:<br/>
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br/>
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
*/
@Entity()
@Table(name = "users")
public class User implements UserDetails, Comparable<User> {
	private static final long serialVersionUID = 3526152576620195123L;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-numeric.html */
	@Id()
	@SequenceGenerator(name = "generatorOfUsersIds", sequenceName = "sequenceOfUsersIds", 
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatorOfUsersIds")
	@Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigserial")
	private long id;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "joining_table_of_users_and_roles", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", 
					nullable = false, columnDefinition = "bigserial")}, 
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", 
					nullable = false, columnDefinition = "bigserial")}
	)
	private Set<UserRole> roles;

	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	@Column(name = "username", nullable = false)
	private String username;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-character.html */
	@Column(name = "password", nullable = false, columnDefinition = "text")
	private String password;

	@Column(name = "date_of_last_password_reset", nullable = true)
	private Timestamp dateOfLastPasswordReset;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", nullable = true, 
			referencedColumnName = "id", columnDefinition = "bigserial")
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

	@Override()
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

	@Override()
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override()
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

	@Override()
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override()
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override()
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override()
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
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
