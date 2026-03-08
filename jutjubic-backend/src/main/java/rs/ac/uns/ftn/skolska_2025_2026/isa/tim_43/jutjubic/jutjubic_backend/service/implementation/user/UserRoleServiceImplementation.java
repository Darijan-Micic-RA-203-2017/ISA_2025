package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user.UserRoleRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserRoleService;

/** REFERENCES<br />:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
@Service()
public class UserRoleServiceImplementation implements UserRoleService {
	private UserRoleRepository userRoleRepository;
	
	@Autowired()
	public UserRoleServiceImplementation(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Override()
	@Transactional(readOnly = true)
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override()
	@Transactional(readOnly = true)
	public UserRole findById(long id) {
		return userRoleRepository.findById(id).orElse(null);
	}

	@Override()
	@Transactional(readOnly = true)
	public UserRole findByName(String name) {
		return userRoleRepository.findByName(name);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteById(long id) {
		userRoleRepository.deleteById(id);
	}
}
