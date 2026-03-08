package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.address.AddressRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;

/** REFERENCES:<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe3/jpa_example<br />
 * https://github.com/isa-asistent/Vezbe-2025/blob/main/vezbe6/Transakcije.pdf<br />
 * https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe6/tx-optimistic-example
*/
@Service()
public class AddressServiceImplementation implements AddressService {
	private AddressRepository addressRepository;
	
	@Autowired()
	public AddressServiceImplementation(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override()
	@Transactional(readOnly = true)
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override()
	@Transactional(readOnly = true)
	public Address findById(long id) {
		return addressRepository.findById(id).orElse(null);
	}

	@Override()
	@Transactional(readOnly = false)
	public Address create(Address address) {
		return addressRepository.save(address);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Address update(Address address) {
		Address addressToBeUpdated = findById(address.getId());
		if (addressToBeUpdated == null) {
			return null;
		}

		addressToBeUpdated.setStreet(address.getStreet());
		addressToBeUpdated.setNumber(address.getNumber());
		addressToBeUpdated.setPostalCode(address.getPostalCode());
		addressToBeUpdated.setPlace(address.getPlace());
		addressToBeUpdated.setCountry(address.getCountry());
		addressToBeUpdated.setLatitude(address.getLatitude());
		addressToBeUpdated.setLongitude(address.getLongitude());

		return addressRepository.save(addressToBeUpdated);
	}

	@Override()
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteById(long id) {
		addressRepository.deleteById(id);
	}
}
