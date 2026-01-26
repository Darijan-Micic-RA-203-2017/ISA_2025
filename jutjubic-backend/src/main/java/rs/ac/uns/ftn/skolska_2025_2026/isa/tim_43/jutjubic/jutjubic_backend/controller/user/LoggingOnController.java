package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.controller.user;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.ObjectWithTextualContextDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.TokenWithLifeDurationDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserCredentialsDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TokenUtilities;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe1/validation_example<br />
 * https://beanvalidation.org/1.0/spec/<br />
 * https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html
*/
@RestController()
@RequestMapping(path = {"/log-on"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, 
		produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoggingOnController {
	private AuthenticationManager authenticationManager;
	private TokenUtilities tokenUtilities;

	@Autowired()
	public LoggingOnController(AuthenticationManager authenticationManager, 
			TokenUtilities tokenUtilities) {
		this.authenticationManager = authenticationManager;
		this.tokenUtilities = tokenUtilities;
	}

	@PostMapping(path = {""})
	public ResponseEntity<ObjectWithTextualContextDTO> logOnWith(
			@Valid() @RequestBody() UserCredentialsDTO userCredentialsDTO) {
		String username = userCredentialsDTO.getUsername();
		String password = userCredentialsDTO.getPassword();

		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException dE) {
			System.out.println(dE.getMessage());

			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The user with the username \"").append(username);
			textualContextBuilder.append("\" has not been logged on because they are ");
			textualContextBuilder.append("disabled!");

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.6
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(
							new TokenWithLifeDurationDTO(null, 0L), 
							textualContextBuilder.toString()), 
					HttpStatus.NOT_ACCEPTABLE);
		} catch (LockedException lE) {
			System.out.println(lE.getMessage());

			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The user with the username \"").append(username);
			textualContextBuilder.append("\" has not been logged on because they are ");
			textualContextBuilder.append("locked!");

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc4918#section-11.3
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(
							new TokenWithLifeDurationDTO(null, 0L), 
							textualContextBuilder.toString()), 
					HttpStatus.LOCKED);
		} catch (BadCredentialsException bCE) {
			System.out.println(bCE.getMessage());

			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The user with the username \"").append(username);
			textualContextBuilder.append("\" has not been logged on because the entered ");
			textualContextBuilder.append("password is invalid!");

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(
							new TokenWithLifeDurationDTO(null, 0L), 
							textualContextBuilder.toString()), 
					HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException e) {
			System.out.println(e.getMessage());

			StringBuilder textualContextBuilder = new StringBuilder();
			textualContextBuilder.append("The user with the username \"").append(username);
			textualContextBuilder.append("\" has not been logged on because an internal ");
			textualContextBuilder.append("authentication exception has occured!\n");
			textualContextBuilder.append("The user is not disabled, not locked and the entered");
			textualContextBuilder.append(" password is valid.");

			// REFERENCE: https://datatracker.ietf.org/doc/html/rfc4918#section-11.2
			return new ResponseEntity<ObjectWithTextualContextDTO>(
					new ObjectWithTextualContextDTO(
							new TokenWithLifeDurationDTO(null, 0L), 
							textualContextBuilder.toString()), 
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User userWhoWasLoggedOn = (User) authentication.getPrincipal();
		String token = tokenUtilities.generateTokenForUserWith(userWhoWasLoggedOn.getUsername());
		long lifeDurationOfTokenInMilliseconds = 
				tokenUtilities.getLifeDurationOfTokenInMilliseconds();

		StringBuilder textualContextBuilder = new StringBuilder();
		textualContextBuilder.append("The user with the username \"").append(username);
		textualContextBuilder.append("\" has been logged on.");
		String textualContext = textualContextBuilder.toString();
		System.out.println(textualContext);

		return new ResponseEntity<ObjectWithTextualContextDTO>(
				new ObjectWithTextualContextDTO(
						new TokenWithLifeDurationDTO(token, lifeDurationOfTokenInMilliseconds), 
						textualContext), 
				HttpStatus.OK);
	}
}
