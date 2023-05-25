package uz.softex.securitystore.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
}
