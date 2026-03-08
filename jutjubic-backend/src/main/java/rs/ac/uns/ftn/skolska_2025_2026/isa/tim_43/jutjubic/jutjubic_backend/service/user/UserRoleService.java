package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;

/** REFERENCES<br />:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
public interface UserRoleService {
	@Transactional(readOnly = true)
	List<UserRole> findAll();

	@Transactional(readOnly = true)
	UserRole findById(long id);

	@Transactional(readOnly = true)
	UserRole findByName(String name);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void deleteById(long id);
}
