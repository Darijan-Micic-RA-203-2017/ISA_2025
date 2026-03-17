package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.controller.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.ObjectWithTextualContextDTO;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html<br />
 * https://stackoverflow.com/questions/74227120/how-can-i-create-custom-validator-on-java-list-type<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1
*/
@RestControllerAdvice()
public class ValidationExceptionsAdvice {
	public ValidationExceptionsAdvice() {}

	@ExceptionHandler(exception = {MethodArgumentNotValidException.class})
	public ResponseEntity<ObjectWithTextualContextDTO> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException mANVE) {
		List<ObjectError> listOfErrors = mANVE.getBindingResult().getAllErrors();

		StringBuilder textualContextBuilder = new StringBuilder("The HTTP request failed because");
		textualContextBuilder.append(" of the following validation errors:");
		for (ObjectError oError: listOfErrors) {
			textualContextBuilder.append("\n\n");

			FieldError fieldError = (FieldError) oError;
			textualContextBuilder.append(fieldError.getField())
					.append(": ").append(fieldError.getDefaultMessage());
		}

		// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1
		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(null, textualContextBuilder.toString()), 
				HttpStatus.BAD_REQUEST);
	}
}
