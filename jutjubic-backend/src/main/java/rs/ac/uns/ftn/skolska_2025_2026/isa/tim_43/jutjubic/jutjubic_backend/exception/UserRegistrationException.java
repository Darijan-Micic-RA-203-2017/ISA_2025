package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://www.wscubetech.com/resources/java/custom-exception
*/
public class UserRegistrationException extends Exception {
	private static final long serialVersionUID = 389138303502434023L;

	public UserRegistrationException() {
		super();
	}

	public UserRegistrationException(String message) {
		super(message);
	}
}
