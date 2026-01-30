package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import java.util.List;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public interface UserService {
	List<User> findAll();
	User findById(long id);
	User findByEmailAddress(String emailAddress);
	User findByUsername(String username);
	User save(User user);
}
