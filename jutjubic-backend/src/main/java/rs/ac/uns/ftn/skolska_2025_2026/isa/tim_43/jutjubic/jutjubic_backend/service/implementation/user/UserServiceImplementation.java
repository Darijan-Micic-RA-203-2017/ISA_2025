package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user.UserRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserService;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
@Service()
public class UserServiceImplementation implements UserDetailsService, UserService {
	@Autowired()
	private UserRepository userRepository;

	public UserServiceImplementation() {}

	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override()
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			StringBuilder exceptionMessageBuilder = new StringBuilder("The user with the username");
			exceptionMessageBuilder.append(" \"").append(username).append("\" has not been found!");

			throw new UsernameNotFoundException(exceptionMessageBuilder.toString());
		}

		return user;
	}

	@Override()
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.fetchAll();
	}

	@Override()
	@Transactional(readOnly = true)
	public User findById(long id) {
		return userRepository.fetchById(id);
	}

	@Override()
	@Transactional(readOnly = true)
	public User findByEmailAddress(String emailAddress) {
		return userRepository.findByEmailAddress(emailAddress);
	}

	@Override()
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override()
	@Transactional(readOnly = true)
	public User findByDigestedIdentificator(String digestedIdentificator) {
		return userRepository.findByDigestedIdentificator(digestedIdentificator);
	}

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/53647672/how-to-save-parent-and-child-in-one-shot-jpa-hibernate<br />
	 * https://stackoverflow.com/a/39615854
	*/
	@Override()
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	public User create(User user) {
		return userRepository.save(user);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	public User enable(User userToBeEnabled) {
		userToBeEnabled.setEnabled(true);

		return userRepository.save(userToBeEnabled);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public User update(User user) {
		User userToBeUpdated = findById(user.getId());
		if (userToBeUpdated == null) {
			return null;
		}

		userToBeUpdated.setFirstName(user.getFirstName());
		userToBeUpdated.setLastName(user.getLastName());
		userToBeUpdated.setAddress(user.getAddress());

		return userRepository.save(userToBeUpdated);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
