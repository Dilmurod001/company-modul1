package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.payload.AddressDto;
import uz.pdp.appcompany.payload.ResponseApi;
import uz.pdp.appcompany.repositrory.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public ResponseApi add(AddressDto addressDto) {
        boolean existsByHomeNumberAndStreet = addressRepository.existsByHomeNumberAndStreet(addressDto.getHomeNumber(), addressDto.getStreet());

        if (existsByHomeNumberAndStreet) {
            return new ResponseApi("Home number already exist in this street", false);
        }
        Address address = new Address();
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setStreet(addressDto.getStreet());
        addressRepository.save(address);
        return new ResponseApi("Successfully added", true);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address getOneById(Integer id) {

        Optional<Address> addressOptional = addressRepository.findById(id);
        return addressOptional.orElse(null);

    }

    public ResponseApi delete(Integer id) {
        try {
            addressRepository.deleteById(id);
            return new ResponseApi("Successfully deleted", true);
        } catch (Exception e) {
            return new ResponseApi("Error in deleting", false);
        }
    }

    public ResponseApi edit(Integer id, AddressDto addressDto) {
        Optional<Address> addressRepositoryById = addressRepository.findById(id);
        if (!addressRepositoryById.isPresent())
            return new ResponseApi("Address not found", false);
        Address address = addressRepositoryById.get();

        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ResponseApi("Successfully edited",true);

    }
}
