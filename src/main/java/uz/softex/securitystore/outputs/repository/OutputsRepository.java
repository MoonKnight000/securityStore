package uz.softex.securitystore.outputs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.outputs.entity.Outputs;

import java.util.List;
import java.util.Optional;

public interface OutputsRepository extends JpaRepository<Outputs, Integer> {
    Optional<Outputs> findByStore_IdAndId(Integer store_id, Integer id);

    List<Outputs> findByStore_Id(Integer store_id);
    void  deleteByStoreId(Integer store_id);
}
