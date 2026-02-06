package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import org.springframework.mail.MailException;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.EmailAddressAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UsernameAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe2/async_example<br />
 * https://mailtrap.io/blog/spring-send-email/
*/
public interface RegistrationService {
	boolean isEmailAddressAlreadyAssociatedWithSomeUser(String emailAddress);
	boolean isUsernameAlreadyAssociatedWithSomeUser(String username);
	Address saveAddressOfNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO);
	User saveNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO, 
			Address addressOfNewUser);
	User registerWith(UserRegistrationRequestDTO userRegistrationRequestDTO) 
			throws EmailAddressAlreadyAssociatedWithSomeUserException, 
			UsernameAlreadyAssociatedWithSomeUserException;
	void sendEmailMessageForAccountActivationOf(User newUser) throws MailException;
}
