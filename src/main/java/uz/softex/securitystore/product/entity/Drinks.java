package uz.softex.securitystore.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.dto.DrinksDto;
import uz.softex.securitystore.store.entity.Store;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorColumn(name = "myDtype")
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueForDrinks", columnNames = {"name", "type"})})
public class Drinks extends Products {
    @Enumerated(value = EnumType.STRING)
    private DrinksType type;

    private String brand;

    public Drinks(DrinksDto dto, Store store) {
        super(dto, store);
        this.type = dto.getType();
        this.brand = dto.getBrand();
    }
}