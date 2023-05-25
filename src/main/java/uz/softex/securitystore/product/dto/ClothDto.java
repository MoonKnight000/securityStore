package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.Cloth;
import uz.softex.securitystore.product.entity.ClothSize;
import uz.softex.securitystore.product.entity.Color;
import uz.softex.securitystore.product.entity.TypeCloth;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothDto extends ProductDto {

    private Color color;
    private ClothSize clothSize;
    private TypeCloth typeCloth;

    public ClothDto(Cloth cloth) {
        super(cloth.getId(), cloth.getName(), cloth.getCount(), cloth.getPrice(), cloth.getMyDtype());
        this.color = cloth.getColor();
        this.clothSize = cloth.getClothSize();
        this.typeCloth = cloth.getTypeCloth();
    }
}