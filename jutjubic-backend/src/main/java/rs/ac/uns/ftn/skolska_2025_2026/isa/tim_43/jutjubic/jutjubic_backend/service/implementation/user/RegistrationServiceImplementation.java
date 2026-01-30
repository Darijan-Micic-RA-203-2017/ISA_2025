package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.dto.user.UserRegistrationRequestDTO;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.RegistrationService;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserService;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Service()
public class RegistrationServiceImplementation implements RegistrationService {
	private AddressService addressService;
	private UserService userService;
	private PasswordEncoder passwordEncoder;

	@Autowired()
	public RegistrationServiceImplementation(AddressService addressService, 
			UserService userService, PasswordEncoder passwordEncoder) {
		this.addressService = addressService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override()
	public User registerWith(UserRegistrationRequestDTO userRegistrationRequestDTO) {
		User possiblyExistentUserWithSameEmailAddress = 
				userService.findByEmailAddress(userRegistrationRequestDTO.getEmailAddress());
		if (possiblyExistentUserWithSameEmailAddress != null) {
			return null;
		}
		User possiblyExistentUserWithSameUsername = 
				userService.findByUsername(userRegistrationRequestDTO.getUsername());
		if (possiblyExistentUserWithSameUsername != null) {
			return null;
		}

		Address addressOfNewUser = new Address();
		addressOfNewUser.setStreet(userRegistrationRequestDTO.getAddress().getStreet());
		addressOfNewUser.setNumber(userRegistrationRequestDTO.getAddress().getNumber());
		addressOfNewUser.setPostalCode(userRegistrationRequestDTO.getAddress().getPostalCode());
		addressOfNewUser.setPlace(userRegistrationRequestDTO.getAddress().getPlace());
		addressOfNewUser.setCountry(userRegistrationRequestDTO.getAddress().getCountry());
		addressOfNewUser.setLatitude(userRegistrationRequestDTO.getAddress().getLatitude());
		addressOfNewUser.setLongitude(userRegistrationRequestDTO.getAddress().getLongitude());
		Address returnValueOfSaveAddressMethod = addressService.save(addressOfNewUser);
		if (returnValueOfSaveAddressMethod == null || returnValueOfSaveAddressMethod.getId() < 1) {
			return null;
		}

		User newUser = new User();
		newUser.setEnabled(false);
		newUser.setEmailAddress(userRegistrationRequestDTO.getEmailAddress());
		newUser.setUsername(userRegistrationRequestDTO.getUsername());
		newUser.setPassword(passwordEncoder.encode(userRegistrationRequestDTO.getPassword()));
		newUser.setFirstName(userRegistrationRequestDTO.getFirstName());
		newUser.setLastName(userRegistrationRequestDTO.getLastName());
		newUser.setAddress(returnValueOfSaveAddressMethod);

		return userService.save(newUser);
	}
}
