package uz.softex.securitystore.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.softex.securitystore.product.entity.Drinks;
import uz.softex.securitystore.product.entity.DrinksType;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

public interface DrinksRepository extends JpaRepository<Drinks, Integer> {
    Page<Drinks> findByStore_IdAndDeletedIsFalse(Integer store_id, Pageable pageable);


    Optional<Drinks> findByStore_IdAndIdAndDeletedIsFalse(Integer store_id, Integer id);
    @Query()
    Page<Drinks>
    findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndBrandContainingAndTypeAndDeletedIsFalse
            (String name, Integer minCount, Integer maxCount,
             Integer  storeId, Double minPrice, Double maxPrice, String brand, DrinksType type,Pageable pageable);
}
