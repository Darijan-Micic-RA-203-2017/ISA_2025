package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.validator.ContainsLettersValidator;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://stackoverflow.com/questions/74227120/how-can-i-create-custom-validator-on-java-list-type<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1
*/
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContainsLettersValidator.class)
public @interface ContainsLetters {
	int minLetters() default 0;
	int maxLetters() default 0;

	String message() default "{rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.constraint.ContainsLetters.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
