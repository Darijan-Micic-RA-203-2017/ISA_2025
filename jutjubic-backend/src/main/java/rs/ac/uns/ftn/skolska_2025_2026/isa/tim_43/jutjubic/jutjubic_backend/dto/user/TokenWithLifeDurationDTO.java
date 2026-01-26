package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html
*/
public class TokenWithLifeDurationDTO {
	private String token;

	@Min(value = 0L, 
			message = "The life duration of token in milliseconds has to be positive or zero!")
	@Max(value = 3600000L, 
			message = "The life duration of token in milliseconds has to be <= 3600000!")
	private long lifeDurationOfTokenInMilliseconds;

	public TokenWithLifeDurationDTO() {}

	public TokenWithLifeDurationDTO(String token, long lifeDurationOfTokenInMilliseconds) {
		this.token = token;
		this.lifeDurationOfTokenInMilliseconds = lifeDurationOfTokenInMilliseconds;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getLifeDurationOfTokenInMilliseconds() {
		return lifeDurationOfTokenInMilliseconds;
	}

	public void setLifeDurationOfTokenInMilliseconds(long lifeDurationOfTokenInMilliseconds) {
		this.lifeDurationOfTokenInMilliseconds = lifeDurationOfTokenInMilliseconds;
	}
}
