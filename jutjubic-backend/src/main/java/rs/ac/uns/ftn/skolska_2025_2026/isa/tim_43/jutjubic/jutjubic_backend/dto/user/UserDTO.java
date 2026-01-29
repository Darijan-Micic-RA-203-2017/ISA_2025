package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.address.AddressDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.constraint.ContainsLetters;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://stackoverflow.com/questions/74227120/how-can-i-create-custom-validator-on-java-list-type<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex<br />
 * https://forum.knime.com/t/string-manipulation-multi-column-regex-patternsyntaxexception-illegal-repetition/60894/4
*/
public class UserDTO {
	@PositiveOrZero(message = "The id has to be positive or zero!")
	private long id;

	@NotNull(message = "The \"enabled\" field has to have a non-null value!")
	private boolean enabled;

	@NotEmpty(message = "There has to be at least one role assigned to the user!")
	private List<UserRoleDTO> roles;

	@NotBlank(message = "The e-mail address has to be non-blank!")
	@Email(message = "The e-mail address has to be well-formed!")
	private String emailAddress;

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

	@NotBlank(message = "The first name has to be non-blank!")
	@Pattern(regexp = "^\\p{Lu}('\\p{Lu})?\\p{Ll}+([ \\-]\\p{Lu}('\\p{Lu})?\\p{Ll}+){1,2}$", 
			flags = {Pattern.Flag.UNICODE_CASE}, 
			message = "The first name has to not contain any non-letters, " 
					+ "except space, hyphen and single quotation mark!")
	private String firstName;

	@NotBlank(message = "The last name has to be non-blank!")
	@Pattern(regexp = "^\\p{L}('\\p{Lu})?\\p{Ll}+([ \\-]\\p{L}('\\p{Lu})?\\p{Ll}+){1,2}$", 
			flags = {Pattern.Flag.UNICODE_CASE}, 
			message = "The last name has to not contain any non-letters, " 
					+ "except space, hyphen and single quotation mark!")
	private String lastName;

	@NotNull(message = "The address has to not be null!")
	private AddressDTO address;

	public UserDTO() {}

	public UserDTO(long id, boolean enabled, List<UserRoleDTO> roles, String emailAddress, 
			String username, String firstName, String lastName, AddressDTO address) {
		this.id = id;
		this.enabled = enabled;
		this.roles = roles;
		this.emailAddress = emailAddress;
		this.username = username;
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

	public List<UserRoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoleDTO> roles) {
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
}
