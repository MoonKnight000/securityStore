package uz.softex.securitystore.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.address.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
