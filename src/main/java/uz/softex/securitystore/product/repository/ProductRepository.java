package uz.softex.securitystore.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.softex.securitystore.product.entity.Products;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findAllByStoreIdAndDeletedIsFalse(Integer store_id);

    Optional<Products> findByStore_IdAndIdAndDeletedIsFalse(Integer store_id, Integer id);

    void deleteByStoreId(Integer store_id);
@Query(nativeQuery = true,value ="select * from products where search_word(name,:name) and count between :minCount and :maxCount2 " +
        "and price between :minPrice and :maxPrice and store_id = :storeId")
    Page<Products> findByNameContainsAndCountBetweenAndPriceBetweenAndStoreIdAndDeletedIsFalse
            (String name, Integer minCount, Integer maxCount2, Double minPrice, Double maxPrice,
             Integer storeId, Pageable pageable);

    Page<Products> findByStoreIdAndDeletedIsFalse(Integer store_id, Pageable pageable);

    Optional<Products> findAllByStoreIdAndIdAndDeletedIsFalse(Integer store_id, Integer id);

    @Query(nativeQuery = true, value = "select count(id) from products where store_id = :storeId")
    Integer countById(Integer storeId);
}
