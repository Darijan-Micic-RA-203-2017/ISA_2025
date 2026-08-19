package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.ObjectWithTextualContextDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.RateLimitExceededException;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe5/ratelimiter-example */
@RestControllerAdvice()
public class RateLimitingExceptionsAdvice {
	public RateLimitingExceptionsAdvice() {}

	@ExceptionHandler(exception = {RateLimitExceededException.class})
	public ResponseEntity<ObjectWithTextualContextDTO> handleRateLimitExceededException(
			RuntimeException rE) {
		String textualContext = rE.getMessage();
		System.err.println(textualContext);

		// REFERENCE: https://datatracker.ietf.org/doc/html/rfc6585#section-4
		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(null, textualContext), 
				HttpStatus.TOO_MANY_REQUESTS);
	}
}
