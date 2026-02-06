package rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.implementation.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.model.address.Address;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.repository.address.AddressRepository;
import rs.ac.uns.ftn.skolska_2025_2026.isa.tim_43.jutjubic.jutjubic_backend.service.address.AddressService;

/** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example */
@Service()
public class AddressServiceImplementation implements AddressService {
	private AddressRepository addressRepository;
	
	@Autowired()
	public AddressServiceImplementation(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override()
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override()
	public Address findById(long id) {
		return addressRepository.findById(id).orElse(null);
	}

	@Override()
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	@Override()
	public void deleteById(long id) {
		addressRepository.deleteById(id);
	}
}
