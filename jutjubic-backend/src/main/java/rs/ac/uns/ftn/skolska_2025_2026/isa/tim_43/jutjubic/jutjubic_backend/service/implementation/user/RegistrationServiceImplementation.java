package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.EmailAddressAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.exception.UsernameAlreadyAssociatedWithSomeUserException;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.RegistrationService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserRoleService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserService;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Service()
public class RegistrationServiceImplementation implements RegistrationService {
	private AddressService addressService;
	private UserRoleService userRoleService;
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	@Autowired()
	public RegistrationServiceImplementation(AddressService addressService, 
			UserRoleService userRoleService, UserService userService, 
			PasswordEncoder passwordEncoder) {
		this.addressService = addressService;
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
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
		newUser.setUsername(userRegistrationRequestDTO.getUsername());
		String encodedPassword = passwordEncoder.encode(userRegistrationRequestDTO.getPassword());
		newUser.setPassword(encodedPassword);
		newUser.setFirstName(userRegistrationRequestDTO.getFirstName());
		newUser.setLastName(userRegistrationRequestDTO.getLastName());
		newUser.setAddress(addressOfNewUser);

		return userService.save(newUser);
	}

	@Override()
	public User registerWith(UserRegistrationRequestDTO userRegistrationRequestDTO) 
			throws EmailAddressAlreadyAssociatedWithSomeUserException, 
			UsernameAlreadyAssociatedWithSomeUserException {
		String emailAddress = userRegistrationRequestDTO.getEmailAddress();
		if (isEmailAddressAlreadyAssociatedWithSomeUser(emailAddress)) {
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append("The e-mail address \"").append(emailAddress);
			exceptionMessageBuilder.append("\" is already associated with some user!");

			throw new EmailAddressAlreadyAssociatedWithSomeUserException(
					exceptionMessageBuilder.toString());
		}

		String username = userRegistrationRequestDTO.getUsername();
		if (isUsernameAlreadyAssociatedWithSomeUser(username)) {
			StringBuilder exceptionMessageBuilder = new StringBuilder();
			exceptionMessageBuilder.append("The username \"").append(username);
			exceptionMessageBuilder.append("\" is already associated with some user!");

			throw new UsernameAlreadyAssociatedWithSomeUserException(
					exceptionMessageBuilder.toString());
		}

		Address addressOfNewUser = saveAddressOfNewUser(userRegistrationRequestDTO);
		if (addressOfNewUser == null || addressOfNewUser.getId() < 1) {
			return null;
		}

		return saveNewUser(userRegistrationRequestDTO, addressOfNewUser);
	}
}
