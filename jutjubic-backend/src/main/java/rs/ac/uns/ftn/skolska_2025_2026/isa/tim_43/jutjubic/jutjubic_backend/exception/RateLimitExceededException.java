package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception;

/** REFERENCES:<br />
 * https://www.wscubetech.com/resources/java/custom-exception<br />
 * https://medium.com/@vivekrajyaguru1993/how-i-built-a-simple-rate-limiter-in-spring-boot-using-redis-step-by-step-3b70d6dee066
*/
public class RateLimitExceededException extends RuntimeException {
	private static final long serialVersionUID = 6438483090585672769L;

	public RateLimitExceededException() {
		super();
	}

	public RateLimitExceededException(String message) {
		super(message);
	}
}
