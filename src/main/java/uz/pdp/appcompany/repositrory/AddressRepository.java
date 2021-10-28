package uz.pdp.appcompany.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByHomeNumberAndStreet(String homeNumber, String street);
}
