package uz.softex.securitystore.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.address.entity.Address;
import uz.softex.securitystore.store.dto.StoreDto;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double balance;
    @OneToOne
    private Address address;

    public Store(StoreDto dto, Address address) {
        this.name = dto.getName();
        this.balance = dto.getBalance();
        this.address = address;
    }
}
