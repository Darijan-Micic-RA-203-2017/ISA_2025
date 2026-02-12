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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.constraint.ContainsLetters;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://stackoverflow.com/questions/74227120/how-can-i-create-custom-validator-on-java-list-type<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex<br />
 * https://forum.knime.com/t/string-manipulation-multi-column-regex-patternsyntaxexception-illegal-repetition/60894/4<br />
 * https://www.geeksforgeeks.org/computer-networks/message-digest-in-information-security/<br />
 * https://www.geeksforgeeks.org/java/sha-256-hash-in-java/
*/
@Entity()
@Table(name = "users")
public class User implements UserDetails, Comparable<User> {
	private static final long serialVersionUID = 3526152576620195123L;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-numeric.html */
	@Id()
	@SequenceGenerator(name = "generator_of_users_ids", sequenceName = "users_id_seq", 
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_of_users_ids")
	@Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigserial")
	@PositiveOrZero(message = "The id has to be positive or zero!")
	private long id;

	@Column(name = "enabled", nullable = false)
	@NotNull(message = "The \"enabled\" field has to have a non-null value!")
	private boolean enabled;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "joining_table_of_users_and_roles", 
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", 
					nullable = false, columnDefinition = "bigserial")}, 
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", 
					nullable = false, columnDefinition = "bigserial")}
	)
	@NotEmpty(message = "There has to be at least one role assigned to the user!")
	private Set<UserRole> roles;

	@Column(name = "email_address", nullable = false)
	@NotBlank(message = "The e-mail address has to be non-blank!")
	@Email(message = "The e-mail address has to be well-formed!")
	private String emailAddress;

	@Column(name = "username", nullable = false)
	@NotBlank(message = "The username has to be non-blank!")
	@Pattern(regexp = "^[A-Za-z0-9~!@#\\$%\\^&\\*\\(\\)\\-_=\\+\\[\\]\\|:<>\\.]+$", 
			message = "The username has to not contain any white-space characters, " 
					+ "quotation marks, curly brackets, slashes, semicolons, commas " 
					+ "or question marks!")
	@ContainsLetters(minLetters = 4, 
			message = "The username has to contain at least 4 letters!")
	@Size(min = 8, max = 32, 
			message = "The username has to contain at least 8 and at most 32 characters!")
	private String username;

	@Column(name = "password", nullable = false)
	@NotBlank(message = "The password has to be non-blank!")
	@Pattern(regexp = "^\\S+$", 
			message = "The password has to not contain any white-space characters!")
	@Size(min = 8, max = 60, 
			message = "The password has to contain at least 8 and at most 16 characters! " 
					+ "The encrypted password has to contain at most 60 characters!")
	private String password;

	/** REFERENCES:<br />
	 * https://in.relation.to/2024/04/22/stop-using-date/<br />
	 * https://medium.com/decisionbrain/dates-time-in-modern-java-4ed9d5848a3e<br />
	 * https://medium.com/@ujjawalr/stop-using-java-util-date-heres-why-and-what-to-use-instead-a1e6023e3c58
	*/
	@Column(name = "date_and_time_of_last_password_change", nullable = true)
	@PastOrPresent(message = "The date and time of last password change has to be in the past " 
			+ "or in the present!")
	private ZonedDateTime dateAndTimeOfLastPasswordChange;

	/** REFERENCES:<br />
	 * https://www.geeksforgeeks.org/computer-networks/message-digest-in-information-security/<br />
	 * https://www.geeksforgeeks.org/java/sha-256-hash-in-java/
	*/
	@Column(name = "digested_identificator", nullable = false)
	@NotBlank(message = "The digested identificator has to be non-blank!")
	@Pattern(regexp = "^[a-z0-9]+$", 
			message = "The digested identificator has to contain only small letters and digits!")
	@Size(min = 64, max = 64, 
			message = "The digested identificator has to contain exactly 64 characters!")
	private String digestedIdentificator;

	@Column(name = "first_name", nullable = false)
	@NotBlank(message = "The first name has to be non-blank!")
	@Pattern(regexp = "^\\p{Lu}('\\p{Lu})?\\p{Ll}+([ \\-]\\p{Lu}('\\p{Lu})?\\p{Ll}+)?$", 
			flags = {Pattern.Flag.UNICODE_CASE}, 
			message = "The first name has to not contain any non-letters, " 
					+ "except space, hyphen and single quotation mark!")
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@NotBlank(message = "The last name has to be non-blank!")
	@Pattern(regexp = "^\\p{L}('\\p{Lu})?\\p{Ll}+([ \\-]\\p{L}('\\p{Lu})?\\p{Ll}+){0,2}$", 
			flags = {Pattern.Flag.UNICODE_CASE}, 
			message = "The last name has to not contain any non-letters, " 
					+ "except space, hyphen and single quotation mark!")
	private String lastName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id", nullable = true, 
			referencedColumnName = "id", columnDefinition = "bigserial")
	@NotNull(message = "The address has to not be null!")
	private Address address;

	public User() {}

	public User(long id, boolean enabled, Set<UserRole> roles, String emailAddress, 
			String username, String password, ZonedDateTime dateAndTimeOfLastPasswordChange, 
			String digestedIdentificator, String firstName, String lastName, Address address) {
		this.id = id;
		this.enabled = enabled;
		this.roles = roles;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = password;
		this.dateAndTimeOfLastPasswordChange = dateAndTimeOfLastPasswordChange;
		this.digestedIdentificator = digestedIdentificator;
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

	/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
	public void setPassword(String password) {
		this.password = password;

		// REFERENCE: https://mkyong.com/java/how-to-get-current-timestamps-in-java/
		ZonedDateTime currentDateAndTime = ZonedDateTime.now();
		setDateAndTimeOfLastPasswordChange(currentDateAndTime);
	}

	public ZonedDateTime getDateAndTimeOfLastPasswordChange() {
		return dateAndTimeOfLastPasswordChange;
	}

	public void setDateAndTimeOfLastPasswordChange(ZonedDateTime dateAndTimeOfLastPasswordChange) {
		this.dateAndTimeOfLastPasswordChange = dateAndTimeOfLastPasswordChange;
	}

	public String getDigestedIdentificator() {
		return digestedIdentificator;
	}

	public void setDigestedIdentificator(String digestedIdentificator) {
		this.digestedIdentificator = digestedIdentificator;
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
		comparisonValue = 
				dateAndTimeOfLastPasswordChange.compareTo(o.dateAndTimeOfLastPasswordChange);
		if (comparisonValue != 0) {
			return comparisonValue;
		}
		comparisonValue = digestedIdentificator.compareTo(o.digestedIdentificator);
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
				dateAndTimeOfLastPasswordChange, digestedIdentificator, firstName, lastName, 
				address);
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
				&& Objects.equals(dateAndTimeOfLastPasswordChange, 
						other.dateAndTimeOfLastPasswordChange) 
				&& Objects.equals(digestedIdentificator, other.digestedIdentificator) 
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
				.append(", dateAndTimeOfLastPasswordChange = ")
				.append(dateAndTimeOfLastPasswordChange)
				.append(", digestedIdentificator = ").append(digestedIdentificator)
				.append(", firstName = ").append(firstName)
				.append(", lastName = ").append(lastName)
				.append(", address = ").append(address)
				.append("]");

		return builder.toString();
	}
}
