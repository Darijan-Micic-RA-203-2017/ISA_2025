package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.EmailAddressAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UsernameAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public interface RegistrationService {
	boolean isEmailAddressAlreadyAssociatedWithSomeUser(String emailAddress);
	boolean isUsernameAlreadyAssociatedWithSomeUser(String username);
	User registerWith(UserRegistrationRequestDTO userRegistrationRequestDTO) 
			throws EmailAddressAlreadyAssociatedWithSomeUserException, 
			UsernameAlreadyAssociatedWithSomeUserException;
}
