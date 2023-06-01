package uz.softex.securitystore.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.softex.securitystore.currency.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
}
