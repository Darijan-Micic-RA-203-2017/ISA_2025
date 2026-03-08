package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
public interface UserService {
	@Transactional(readOnly = true)
	List<User> findAll();

	@Transactional(readOnly = true)
	User findById(long id);

	@Transactional(readOnly = true)
	User findByEmailAddress(String emailAddress);

	@Transactional(readOnly = true)
	User findByUsername(String username);

	@Transactional(readOnly = true)
	User findByDigestedIdentificator(String digestedIdentificator);

	/** REFERENCES:<br />
	 * https://stackoverflow.com/questions/53647672/how-to-save-parent-and-child-in-one-shot-jpa-hibernate<br />
	 * https://stackoverflow.com/a/39615854
	*/
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	User create(User user);

	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	User enable(User userToBeEnabled);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	User update(User user);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void deleteById(long id);
}
