package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user.UserRoleRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.user.UserRoleService;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Service()
public class UserRoleServiceImplementation implements UserRoleService {
	private UserRoleRepository userRoleRepository;
	
	@Autowired()
	public UserRoleServiceImplementation(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Override()
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override()
	public UserRole findById(long id) {
		return userRoleRepository.findById(id).orElse(null);
	}

	@Override()
	public UserRole findByName(String name) {
		return userRoleRepository.findByName(name);
	}
}
