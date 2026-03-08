package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import org.springframework.mail.MailException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserAccountActivationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserRegistrationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://mailtrap.io/blog/spring-send-email/<br />
 * https://www.geeksforgeeks.org/computer-networks/message-digest-in-information-security/<br />
 * https://www.geeksforgeeks.org/java/sha-256-hash-in-java/
*/
public interface RegistrationService {
	AddressService getAddressService();
	UserRoleService getUserRoleService();
	UserService getUserService();

	@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
	boolean isEmailAddressAlreadyAssociatedWithSomeUser(String emailAddress);

	@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
	boolean isUsernameAlreadyAssociatedWithSomeUser(String username);

	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	Address saveAddressOfNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO);

	/** REFERENCES:<br />
	 * https://www.geeksforgeeks.org/computer-networks/message-digest-in-information-security/<br />
	 * https://www.geeksforgeeks.org/java/sha-256-hash-in-java/
	*/
	String generateDigestedIdentificatorFrom(String username, String password);

	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	User createNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO, 
			Address addressOfNewUser);

	void clearListsOfEmailAddressesAndUsernamesAlreadyAllocatedBySomeUsers();

	/** REFERENCES:<br />
	 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
	 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example<br />
	 * https://stackoverflow.com/questions/53647672/how-to-save-parent-and-child-in-one-shot-jpa-hibernate<br />
	 * https://stackoverflow.com/a/39615854
	*/
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, 
			isolation = Isolation.DEFAULT, 
			rollbackFor = {UserRegistrationException.class, MailException.class})
	User registerUserBasedOn(UserRegistrationRequestDTO userRegistrationRequestDTO) 
			throws UserRegistrationException, MailException;

	/** REFERENCE: https://mailtrap.io/blog/spring-send-email/ */
	void sendEmailMessageForAccountActivationOf(User newUser) throws MailException;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, 
			rollbackFor = {UserAccountActivationException.class})
	User activateAccountOfUserWith(String digestedIdentificator) 
			throws UserAccountActivationException;
}
