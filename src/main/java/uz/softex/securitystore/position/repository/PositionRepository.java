package uz.softex.securitystore.position.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.position.entity.Position;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Integer> {
    Optional<Position> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}
