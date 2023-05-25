package uz.softex.securitystore.workers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.workers.entity.Workers;

import java.util.List;
import java.util.Optional;

public interface WorkersRepository extends JpaRepository<Workers, Integer> {
    Optional<Workers> findByUsername(String username);

    boolean existsByUsername(String username);
    void deleteByPositionId(Integer position_id);
    List<Workers> findByPositionName(String position_name);

    List<Workers> findByStoreIdAndEnabledIsTrue(Integer store_id);
    List<Workers> findByEnabledIsTrue();
    Optional<Workers> findByIdAndEnabledIsTrue(Integer id);
}
