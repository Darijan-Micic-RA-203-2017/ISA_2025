package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserAccountActivationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UserRegistrationException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.notification.EmailService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.RegistrationService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserRoleService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.utility.TextualEmailMessageData;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://mailtrap.io/blog/spring-send-email/
*/
@Service()
public class RegistrationServiceImplementation implements RegistrationService {
	private AddressService addressService;
	private UserRoleService userRoleService;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private Environment environment;
	private EmailService emailService;

	@Autowired()
	public RegistrationServiceImplementation(AddressService addressService, 
			UserRoleService userRoleService, UserService userService, 
			PasswordEncoder passwordEncoder, Environment environment, EmailService emailService) {
		this.addressService = addressService;
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.environment = environment;
		this.emailService = emailService;
	}

	@Override()
	public boolean isEmailAddressAlreadyAssociatedWithSomeUser(String emailAddress) {
		User possibleExistingUserWithSameEmailAddress = 
				userService.findByEmailAddress(emailAddress);
		if (possibleExistingUserWithSameEmailAddress != null) {
			return true;
		}

		return false;
	}

	@Override()
	public boolean isUsernameAlreadyAssociatedWithSomeUser(String username) {
		User possibleExistingUserWithSameUsername = userService.findByUsername(username);
		if (possibleExistingUserWithSameUsername != null) {
			return true;
		}

		return false;
	}

	@Override()
	public Address saveAddressOfNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO) {
		Address addressOfNewUser = new Address();
		addressOfNewUser.setStreet(userRegistrationRequestDTO.getAddress().getStreet());
		addressOfNewUser.setNumber(userRegistrationRequestDTO.getAddress().getNumber());
		addressOfNewUser.setPostalCode(userRegistrationRequestDTO.getAddress().getPostalCode());
		addressOfNewUser.setPlace(userRegistrationRequestDTO.getAddress().getPlace());
		addressOfNewUser.setCountry(userRegistrationRequestDTO.getAddress().getCountry());
		addressOfNewUser.setLatitude(userRegistrationRequestDTO.getAddress().getLatitude());
		addressOfNewUser.setLongitude(userRegistrationRequestDTO.getAddress().getLongitude());

		return addressService.save(addressOfNewUser);
	}

	@Override()
	public User saveNewUser(UserRegistrationRequestDTO userRegistrationRequestDTO, 
			Address addressOfNewUser) {
		User newUser = new User();
		newUser.setEnabled(false);
		Set<UserRole> roles = new TreeSet<UserRole>();
		roles.add(userRoleService.findById(1L));
		newUser.setRoles(roles);
		newUser.setEmailAddress(userRegistrationRequestDTO.getEmailAddress());
		String username = userRegistrationRequestDTO.getUsername();
		newUser.setUsername(username);
		String password = userRegistrationRequestDTO.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		newUser.setPassword(encodedPassword);
		newUser.setEncodedId(passwordEncoder.encode(username.concat(" ").concat(password)));
		newUser.setFirstName(userRegistrationRequestDTO.getFirstName());
		newUser.setLastName(userRegistrationRequestDTO.getLastName());
		newUser.setAddress(addressOfNewUser);

		return userService.save(newUser);
	}

	@Override()
	public User registerUserBasedOn(UserRegistrationRequestDTO userRegistrationRequestDTO) 
			throws UserRegistrationException {
		String emailAddress = userRegistrationRequestDTO.getEmailAddress();
		if (isEmailAddressAlreadyAssociatedWithSomeUser(emailAddress)) {
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append("The e-mail address \"").append(emailAddress);
			exceptionMessageBuilder.append("\" is already associated with some user!");

			throw new UserRegistrationException(exceptionMessageBuilder.toString());
		}

		String username = userRegistrationRequestDTO.getUsername();
		if (isUsernameAlreadyAssociatedWithSomeUser(username)) {
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append("The username \"").append(username);
			exceptionMessageBuilder.append("\" is already associated with some user!");

			throw new UserRegistrationException(exceptionMessageBuilder.toString());
		}

		Address addressOfNewUser = saveAddressOfNewUser(userRegistrationRequestDTO);
		if (addressOfNewUser == null || addressOfNewUser.getId() < 1) {
			return null;
		}

		return saveNewUser(userRegistrationRequestDTO, addressOfNewUser);
	}

	@Override()
	public void sendEmailMessageForAccountActivationOf(User newUser) throws MailException {
		TextualEmailMessageData dataOfEmailMessageForAccountActivation = 
				new TextualEmailMessageData();
		dataOfEmailMessageForAccountActivation.setBcc(null);
		dataOfEmailMessageForAccountActivation.setCc(null);
		dataOfEmailMessageForAccountActivation.setFrom(
				environment.getRequiredProperty("spring.mail.username"));
		dataOfEmailMessageForAccountActivation.setReplyTo(null);
		// REFERENCE: https://mkyong.com/java/how-to-get-current-timestamps-in-java/
		ZonedDateTime currentDateAndTime = ZonedDateTime.now();
		dataOfEmailMessageForAccountActivation.setSentDate(currentDateAndTime);
		dataOfEmailMessageForAccountActivation.setSubject(
				"Јутјубић - омогућавање деловања корисничког налога");
		StringBuilder emailMessageTextBuilder = new StringBuilder("Поштовани/а ");
		emailMessageTextBuilder.append(newUser.getFirstName()).append(",\n\n");
		emailMessageTextBuilder.append("Хвала Вам што сте започели поступак регистрације на ");
		emailMessageTextBuilder.append("нашој платформи! Отворите следећу повезницу (кликом ");
		emailMessageTextBuilder.append("на њу или њеним копирањем у адресно поље друге картице ");
		emailMessageTextBuilder.append("Веб читача и посетом к њој) да бисте успешно завршили ");
		emailMessageTextBuilder.append("регистрацију Вашег корисничког налога:\n");
		emailMessageTextBuilder.append("http://localhost:4200/activate-account/");
		emailMessageTextBuilder.append(newUser.getEncodedId()).append("\n\n");
		emailMessageTextBuilder.append("Поздрав!\nЈутјубић\n\n\n");
		emailMessageTextBuilder.append("Respected ").append(newUser.getFirstName()).append(",\n\n");
		emailMessageTextBuilder.append("Thank you for beginning the registration process on ");
		emailMessageTextBuilder.append("our platform! Please open the following link (by ");
		emailMessageTextBuilder.append("clicking on it or copying it into the address field of ");
		emailMessageTextBuilder.append("the other Web browser tab and visiting it) in order to ");
		emailMessageTextBuilder.append("successfully complete the registration of your user ");
		emailMessageTextBuilder.append("account:\n");
		emailMessageTextBuilder.append("http://localhost:4200/activate-account/");
		emailMessageTextBuilder.append(newUser.getEncodedId()).append("\n\n");
		emailMessageTextBuilder.append("Best regards,\nJutjubic\n");
		dataOfEmailMessageForAccountActivation.setText(emailMessageTextBuilder.toString());
		dataOfEmailMessageForAccountActivation.setTo(new String[] {newUser.getEmailAddress()});

		emailService.sendTextualEmailMessageWith(dataOfEmailMessageForAccountActivation);
	}

	@Override()
	public void cancelRegistrationOf(User newUser) {
		long idOfAddressOfNewUser = newUser.getAddress().getId();
		userService.deleteById(newUser.getId());
		addressService.deleteById(idOfAddressOfNewUser);
	}

	@Override()
	public User activateAccountOfUserWith(String encodedId) throws UserAccountActivationException {
		User userPendingActivationOfAccount = userService.findByEncodedId(encodedId);
		if (userPendingActivationOfAccount == null) {
			throw new UserAccountActivationException("There is no user with such encoded id!");
		}
		if (userPendingActivationOfAccount.isEnabled()) {
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append("The account of the user with the encoded id \"");
			exceptionMessageBuilder.append(encodedId).append("\" is already activated!");

			throw new UserAccountActivationException(exceptionMessageBuilder.toString());
		}

		userPendingActivationOfAccount.setEnabled(true);

		return userService.save(userPendingActivationOfAccount);
	}
}
