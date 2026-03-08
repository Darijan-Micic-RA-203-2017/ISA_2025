package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
*/
@Entity()
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority, Comparable<UserRole> {
	private static final long serialVersionUID = -2725148852941001777L;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-numeric.html */
	@Id()
	@SequenceGenerator(name = "generator_of_user_roles_ids", sequenceName = "user_roles_id_seq", 
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_of_user_roles_ids")
	@Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigserial")
	@PositiveOrZero(message = "The id has to be positive or zero!")
	private long id;

	@Column(name = "name", nullable = false)
	@NotBlank(message = "The name has to be non-blank!")
	@Pattern(regexp = "^ROLE_[A-Z0-9]+(_[A-Z0-9]+)*$", 
			message = "The name has to be written in the upper case snake case " 
					+ "(\"UPPER_CASE_SNAKE_CASE\") and start with \"ROLE_\"!")
	private String name;

	/** REFERENCES:<br />
	 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
	 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
	*/
	@Version()
	@Column(name = "version", nullable = false)
	@Positive(message = "The version has to be positive!")
	private int version;

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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override()
	public String getAuthority() {
		return name;
	}

	/** REFERENCE: https://www.geeksforgeeks.org/java/comparable-interface-in-java-with-examples/ */
	@Override()
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
		comparisonValue = Integer.compare(version, o.version);
		if (comparisonValue != 0) {
			return comparisonValue;
		}

		return 0;
	}

	@Override()
	public int hashCode() {
		return Objects.hash(id, name, version);
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserRole)) {
			return false;
		}

		UserRole other = (UserRole) obj;

		return id == other.id && Objects.equals(name, other.name) && version == other.version;
	}

	@Override()
	public String toString() {
		StringBuilder builder = new StringBuilder("UserRole [");

		builder.append("id = ").append(id).append(", name = ").append(name)
				.append(", version = ").append(version)
				.append("]");

		return builder.toString();
	}
}
