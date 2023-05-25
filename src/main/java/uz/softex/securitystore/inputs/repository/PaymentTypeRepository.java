package uz.softex.securitystore.inputs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.inputs.entity.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

}