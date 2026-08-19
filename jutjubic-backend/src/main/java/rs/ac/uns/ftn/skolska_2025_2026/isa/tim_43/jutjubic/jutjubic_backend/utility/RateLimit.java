package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** REFERENCES:<br />
 * https://medium.com/@vivekrajyaguru1993/how-i-built-a-simple-rate-limiter-in-spring-boot-using-redis-step-by-step-3b70d6dee066<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1
*/
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RateLimit {
	int limit() default 0;
	long timeWindowInMilliseconds() default 0L;
}
