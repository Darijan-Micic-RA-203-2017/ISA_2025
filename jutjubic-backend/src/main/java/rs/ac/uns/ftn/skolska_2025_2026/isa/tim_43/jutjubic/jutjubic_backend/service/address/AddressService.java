package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
public interface AddressService {
	@Transactional(readOnly = true)
	List<Address> findAll();

	@Transactional(readOnly = true)
	Address findById(long id);

	@Transactional(readOnly = false)
	Address create(Address address);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	Address update(Address address);

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	void deleteById(long id);
}
