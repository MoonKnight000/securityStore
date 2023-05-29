package uz.softex.securitystore.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.softex.securitystore.product.entity.Cloth;
import uz.softex.securitystore.product.entity.ClothSize;
import uz.softex.securitystore.product.entity.Color;
import uz.softex.securitystore.product.entity.TypeCloth;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface ClothRepository extends JpaRepository<Cloth, Integer> {

    Optional<Cloth> findByStore_IdAndIdAndDeletedIsFalse(Integer store_id, Integer id);

    Page<Cloth> findAllByStoreIdAndDeletedIsFalse(Integer storeId, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from products where search_word(name, :name) and count between :minCount and :maxCount" +
            " and price between :minPrice and :maxPrice and color in(:color) and type_cloth in(:typeCloth )" +
            "and cloth_size in (:clothSize) and  store_id =:storeId")
    Page<Cloth> findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndColorContainsAndClothSizeAndTypeClothAndDeletedIsFalse
            (String name, Integer minCount, Integer maxCount, Integer storeId, Double minPrice, Double maxPrice,
             List<String> color, List<String> clothSize, List<String> typeCloth, Pageable pageable);

    @Query(nativeQuery = true, value = "select count(id) from products where  store_id = :storeId")
    Integer countById(Integer storeId);
}

