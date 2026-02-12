package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions<br />
 * https://www.convex.dev/typescript/core-concepts/functions-methods/typescript-regex
*/
public class WrappedDigestedIdentificatorDTO {
	@NotBlank(message = "The digested identificator has to be non-blank!")
	@Pattern(regexp = "^[a-z0-9]+$", 
			message = "The digested identificator has to contain only small letters and digits!")
	@Size(min = 64, max = 64, 
			message = "The digested identificator has to contain exactly 64 characters!")
	private String digestedIdentificator;

	public WrappedDigestedIdentificatorDTO() {}

	public WrappedDigestedIdentificatorDTO(String digestedIdentificator) {
		this.digestedIdentificator = digestedIdentificator;
	}

	public String getDigestedIdentificator() {
		return digestedIdentificator;
	}

	public void setDigestedIdentificator(String digestedIdentificator) {
		this.digestedIdentificator = digestedIdentificator;
	}
}
