package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user;

import java.util.List;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
public interface UserRoleService {
	List<UserRole> findAll();
	UserRole findById(long id);
	UserRole findByName(String name);
	void deleteById(long id);
}
