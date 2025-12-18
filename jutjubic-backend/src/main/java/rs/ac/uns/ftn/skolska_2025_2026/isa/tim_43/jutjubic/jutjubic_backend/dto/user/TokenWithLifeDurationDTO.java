package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public class TokenWithLifeDurationDTO {
	private String token;
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
