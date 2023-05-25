package uz.softex.securitystore.saledProducts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaledProductsCountDto {
    private Integer products;
    @Min(0)
    private Integer count;

    public SaledProductsCountDto(SaledProductsCount count) {
        this.products = count.getProducts().getId();
        this.count = count.getCount();
    }
}
