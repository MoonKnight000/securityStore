package uz.softex.securitystore.saledProducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;

public interface SaledProductsCountRepository extends JpaRepository<SaledProductsCount, Integer> {
}
