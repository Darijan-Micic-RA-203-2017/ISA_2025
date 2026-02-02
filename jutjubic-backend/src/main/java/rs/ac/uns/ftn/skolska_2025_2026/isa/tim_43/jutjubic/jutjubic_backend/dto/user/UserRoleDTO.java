package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
*/
public class UserRoleDTO {
	@PositiveOrZero(message = "The id has to be positive or zero!")
	private long id;

	@NotBlank(message = "The name has to be non-blank!")
	@Pattern(regexp = "^ROLE_[A-Z0-9]+(_[A-Z0-9]+)*$", 
			message = "The name has to be written in the upper case snake case " 
					+ "(\"UPPER_CASE_SNAKE_CASE\") and start with \"ROLE_\"!")
	private String name;

	public UserRoleDTO() {}

	public UserRoleDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserRoleDTO(UserRole userRole) {
		this.id = userRole.getId();
		this.name = userRole.getName();
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
}
