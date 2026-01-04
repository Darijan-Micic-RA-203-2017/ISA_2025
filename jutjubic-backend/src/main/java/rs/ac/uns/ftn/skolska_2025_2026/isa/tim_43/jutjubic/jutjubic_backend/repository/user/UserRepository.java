package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.user.User;

/** REFERENCES:<br/>
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br/>
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
 * https://thorben-janssen.com/initialize-associations-spring-data-jpa/
 * https://thorben-janssen.com/hibernate-tips-difference-join-left-join-fetch-join/
*/
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT u FROM User u JOIN FETCH u.roles r WHERE u.username = ?1")
	User fetchByUsernameAsNeededForAuthentication(String username);

	@Query(value = "SELECT u FROM User u JOIN FETCH u.roles r " 
			+ "JOIN FETCH u.address a")
	List<User> fetchAll();

	@Query(value = "SELECT u FROM User u JOIN FETCH u.roles r " 
			+ "JOIN FETCH u.address a " 
			+ "WHERE u.id = ?1")
	User fetchById(long id);

	@Query(value = "SELECT u FROM User u JOIN FETCH u.roles r " 
			+ "JOIN FETCH u.address a " 
			+ "WHERE u.username = ?1")
	User findByUsername(String username);
}
