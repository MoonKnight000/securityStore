package uz.softex.securitystore.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.dto.ClothDto;
import uz.softex.securitystore.store.entity.Store;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorColumn(name = "myDtype")

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndColor", columnNames = {"name", "color", "cloth_size", "type_cloth"})})
public class Cloth extends Products {

    @Enumerated(value = EnumType.STRING)
    private Color color;
    @Enumerated(value = EnumType.STRING)
    private ClothSize clothSize;


    @Enumerated(value = EnumType.STRING)
    private TypeCloth typeCloth;


    public Cloth(ClothDto dto, Store store) {
        this.setId(dto.getId());
        this.setName(dto.getName());
        this.setPrice(dto.getPrice());
        this.setCount(dto.getCount());
        this.setStore(store);
        this.clothSize = dto.getClothSize();
        this.typeCloth = dto.getTypeCloth();
        this.color = dto.getColor();
    }
}
