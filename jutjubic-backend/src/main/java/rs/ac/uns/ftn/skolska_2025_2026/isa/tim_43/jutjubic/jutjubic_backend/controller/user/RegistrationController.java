package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.controller.user;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.ObjectWithTextualContextDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.WrappedDigestedIdentificatorDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserAccountActivationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserRegistrationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.RegistrationService;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html
*/
@RestController()
@RequestMapping(path = {"/register"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, 
		produces = {MediaType.APPLICATION_JSON_VALUE})
public class RegistrationController {
	private RegistrationService registrationService;

	@Autowired()
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping(path = {""})
	public ResponseEntity<ObjectWithTextualContextDTO> registerUserBasedOn(
			@Valid() @RequestBody() UserRegistrationRequestDTO userRegistrationRequestDTO) {
		User registeredUser = null;
		UserDTO registeredUserDTO = null;
		try {
			registeredUser = registrationService.registerUserBasedOn(userRegistrationRequestDTO);
		} catch (UserRegistrationException uRE) {
			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.6
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(registeredUserDTO, uRE.getMessage()), 
					HttpStatus.NOT_ACCEPTABLE);
		}

		if (registeredUser == null) {
			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The user with the e-mail address \"");
			textualContextBuilder.append(userRegistrationRequestDTO.getEmailAddress());
			textualContextBuilder.append("\" has not been registered because an internal ");
			textualContextBuilder.append("server error has occured!");
			String textualContext = textualContextBuilder.toString();
			System.out.println(textualContext);

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.6.1
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(registeredUserDTO, textualContext), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			registrationService.sendEmailMessageForAccountActivationOf(registeredUser);
		} catch (MailException mE) {
			mE.printStackTrace();

			registrationService.cancelRegistrationOf(registeredUser);

			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The registration of the user with the e-mail address \"");
			textualContextBuilder.append(userRegistrationRequestDTO.getEmailAddress());
			textualContextBuilder.append("\" has been cancelled because an e-mail exception has ");
			textualContextBuilder.append("occurred! The user has been deleted from the database!");
			String textualContext = textualContextBuilder.toString();
			System.out.println(textualContext);

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.8
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(registeredUserDTO, textualContext), 
					HttpStatus.CONFLICT);
		}

		StringBuilder textualContextBuilder = new StringBuilder();
		textualContextBuilder.append("The user with the e-mail address \"");
		textualContextBuilder.append(userRegistrationRequestDTO.getEmailAddress());
		textualContextBuilder.append("\" has been registered.");
		String textualContext = textualContextBuilder.toString();
		System.out.println(textualContext);

		registeredUserDTO = new UserDTO(registeredUser);

		// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.3.2
		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(registeredUserDTO, textualContext), 
				HttpStatus.CREATED);
	}

	@PutMapping(path = {"/activate-account"})
	public ResponseEntity<ObjectWithTextualContextDTO> activateAccountOfUserWith(
			@RequestBody() WrappedDigestedIdentificatorDTO wrappedDigestedIdentificatorDTO) {
		User activatedUser = null;
		UserDTO activatedUserDTO = null;
		try {
			activatedUser = registrationService.activateAccountOfUserWith(
					wrappedDigestedIdentificatorDTO.getDigestedIdentificator());
		} catch (UserAccountActivationException uAAE) {
			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.6
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(activatedUserDTO, uAAE.getMessage()), 
					HttpStatus.NOT_ACCEPTABLE);
		}

		StringBuilder textualContextBuilder = new StringBuilder();
		textualContextBuilder.append("The account of the user with the e-mail address \"");
		textualContextBuilder.append(activatedUser.getEmailAddress());
		textualContextBuilder.append("\" has been activated.");
		String textualContext = textualContextBuilder.toString();
		System.out.println(textualContext);

		activatedUserDTO = new UserDTO(activatedUser);

		// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.3.1
		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(activatedUserDTO, textualContext), 
				HttpStatus.OK);
	}
}
