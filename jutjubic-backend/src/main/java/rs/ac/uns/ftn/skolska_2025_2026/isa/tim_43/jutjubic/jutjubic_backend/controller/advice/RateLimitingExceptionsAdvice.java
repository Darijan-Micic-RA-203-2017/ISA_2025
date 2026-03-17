package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.controller.advice;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.ObjectWithTextualContextDTO;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe5/ratelimiter-example
*/
@RestControllerAdvice()
public class RateLimitingExceptionsAdvice {
	public RateLimitingExceptionsAdvice() {}

	@ExceptionHandler(exception = {RequestNotPermitted.class})
	public ResponseEntity<ObjectWithTextualContextDTO> handleRequestNotPermittedException(
			RequestNotPermitted rNPE) {
		System.err.println(rNPE.getMessage());

		StringBuilder textualContextBuilder = new StringBuilder("The server endpoint's rate limit");
		textualContextBuilder.append(" has been exceeded! Please try again later.");

		// REFERENCE: https://datatracker.ietf.org/doc/html/rfc6585#section-4
		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(null, textualContextBuilder.toString()), 
				HttpStatus.TOO_MANY_REQUESTS);
	}
}
