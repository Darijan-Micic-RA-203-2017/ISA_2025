package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user.UserRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserService;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Service()
public class UserServiceImplementation implements UserDetailsService, UserService {
	private UserRepository userRepository;

	@Autowired()
	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			StringBuilder exceptionMessageBuilder = new StringBuilder("User with username \"");
			exceptionMessageBuilder.append(username).append("\" could not be found!");

			throw new UsernameNotFoundException(exceptionMessageBuilder.toString());
		}

		return user;
	}

	@Override()
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override()
	public User findById(long id) {
		return userRepository.findById(id).orElse(null);
	}
}
