package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UserCredentialsDTO {
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

	@NotBlank(message = "The password has to be non-blank!")
	@Pattern(regexp = "^\\S+$", 
			message = "The password has to not contain any white-space characters!")
	@Size(min = 8, max = 16, 
			message = "The password has to contain at least 8 and at most 16 characters!")
	private String password;

	public UserCredentialsDTO() {}

	public UserCredentialsDTO(String username, String password) {
		this.username = username;
		this.password = password;
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
}
