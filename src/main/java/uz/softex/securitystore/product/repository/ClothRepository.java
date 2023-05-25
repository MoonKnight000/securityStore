package uz.softex.securitystore.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.softex.securitystore.product.entity.Cloth;
import uz.softex.securitystore.product.entity.ClothSize;
import uz.softex.securitystore.product.entity.TypeCloth;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    List<Cloth> findByStore_IdAndDeletedIsFalse(Integer store_id);

    Optional<Cloth> findByStore_IdAndIdAndDeletedIsFalse(Integer store_id, Integer id);

    Page<Cloth> findAllByStoreIdAndDeletedIsFalse(Integer storeId, Pageable pageable);
    @Query(value = "select pr from Cloth pr where lower(pr.name) like (lower(concat('%',:name,'%'))) and pr.count >:minCount and " +
                   "pr.count < :maxCount and pr.price> :minPrice and pr.price < :maxPrice and lower(pr.color) like (lower(concat('%',:color,'%'))) " +
                   "and pr.typeCloth in (:typeCloth) and pr.clothSize in (:clothSize) and  pr.store.id=:storeId")
    Page<Cloth> findByNameContainsAndCountBetweenAndStoreIdAndAndPriceBetweenAndColorContainsAndClothSizeAndTypeClothAndDeletedIsFalse
                (String name, Integer minCount, Integer maxCount, Integer storeId, Double minPrice, Double maxPrice,
                String color, List<ClothSize> clothSize, List<TypeCloth> typeCloth, Pageable pageable);


}

