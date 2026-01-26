package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.validation.constraint.ContainsLetters;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://stackoverflow.com/questions/74227120/how-can-i-create-custom-validator-on-java-list-type<br />
 * https://dev.to/eric6166/creating-custom-annotations-for-validation-in-spring-boot-16j1
*/
public class ContainsLettersValidator implements ConstraintValidator<ContainsLetters, String> {
	private static final Pattern REGULAR_EXPRESSION = Pattern.compile("\\p{L}");

	private int minLetters;
	private int maxLetters;

	public ContainsLettersValidator() {}

	@Override()
	public void initialize(ContainsLetters constraintAnnotation) {
		minLetters = constraintAnnotation.minLetters();
		maxLetters = constraintAnnotation.maxLetters();
	}

	@Override()
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank()) {
			return false;
		}
		String strippedValue = value.strip();
		int numberOfLetters = 0;
		for (int i = 0; i < strippedValue.length(); i++) {
			String character = strippedValue.substring(i, i + 1);
			if (REGULAR_EXPRESSION.matcher(character).matches()) {
				numberOfLetters++;
			}
		}

		if (numberOfLetters == 0) {
			return false;
		}

		boolean containingMoreThenOrExactlyMinLetters = true;
		if (minLetters > 0) {
			containingMoreThenOrExactlyMinLetters = numberOfLetters >= minLetters;
		}
		boolean containingLessThenOrExactlyMaxLetters = true;
		if (maxLetters > 0) {
			containingLessThenOrExactlyMaxLetters = numberOfLetters <= maxLetters;
		}

		return containingMoreThenOrExactlyMinLetters && containingLessThenOrExactlyMaxLetters;
	}
}
