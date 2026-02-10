package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html
*/
@SpringBootApplication()
public class JutjubicBackEndApplication {
	public JutjubicBackEndApplication() {}

	@Bean()
	public Validator validator() {
		ValidatorFactory validatorFactory = 
				Validation.byDefaultProvider().configure().buildValidatorFactory();

		return validatorFactory.getValidator();
	}

	public static void main(String[] args) {
		SpringApplication.run(JutjubicBackEndApplication.class, args);
	}
}
