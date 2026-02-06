package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address;

import java.util.List;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;

/** REFERENCES:
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example
*/
public interface AddressService {
	List<Address> findAll();
	Address findById(long id);
	Address save(Address address);
	void deleteById(long id);
}
