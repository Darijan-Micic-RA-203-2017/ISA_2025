package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;

/** REFERENCES:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
*/
@Entity()
@Table(name = "user_roles")
public class UserRole implements GrantedAuthority, Comparable<UserRole> {
	private static final long serialVersionUID = -2725148852941001777L;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-numeric.html */
	@Id()
	@SequenceGenerator(name = "generatorOfUserRolesIds", sequenceName = "sequenceOfUserRolesIds", 
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatorOfUserRolesIds")
	@Column(name = "id", nullable = false, updatable = false, columnDefinition = "bigserial")
	private long id;

	/** REFERENCE: https://www.postgresql.org/docs/current/datatype-character.html */
	@Column(name = "name", nullable = false, columnDefinition = "text")
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

		return 0;
	}

	@Override()
	public int hashCode() {
		return Objects.hash(id, name);
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

		return id == other.id && Objects.equals(name, other.name);
	}

	@Override()
	public String toString() {
		StringBuilder builder = new StringBuilder("UserRole [");

		builder.append("id = ").append(id).append(", name = ").append(name)
				.append("]");

		return builder.toString();
	}
}
