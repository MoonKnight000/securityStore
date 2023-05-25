package uz.softex.securitystore.inputs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.softex.securitystore.inputs.entity.Inputs;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InputsRepository extends JpaRepository<Inputs, Integer> {
    List<Inputs> findByStore_Id(Integer store_id);

    Optional<Inputs> findByIdAndStoreId(Integer id, Integer store_id);

    void deleteByStoreId(Integer store_id);

    @Query(value = "select inp from Inputs inp where inp.createdTime >= :createdTime and inp.store.id=:storeId")
    Page<Inputs> findByStore_IdAndCreatedTimeBefore(Integer storeId, Timestamp createdTime, Pageable pageable);

    Page<Inputs> findByStore_IdAndCreatedTimeBetween(Integer store_id, Timestamp createdTime, Timestamp createdTime2, Pageable pageable);

    List<Inputs> findByStoreId(Integer store_id);

}