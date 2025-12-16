package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.UserRole;

/** REFERENCES:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
*/
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	UserRole findByName(String name);
}
