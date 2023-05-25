package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.Drinks;
import uz.softex.securitystore.product.entity.DrinksType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinksDto extends ProductDto {
    private DrinksType type;

    private String brand;

    public DrinksDto(Drinks drinks) {
        super(drinks.getId(), drinks.getName(), drinks.getCount(), drinks.getPrice(), drinks.getMyDtype());
        this.type = drinks.getType();
        this.brand = drinks.getBrand();
    }

}
