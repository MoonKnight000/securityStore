package uz.softex.securitystore.saledProducts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.Products;
import uz.softex.securitystore.saledProducts.dto.SaledProductsCountDto;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaledProductsCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Products products;
    private Integer count;

    public SaledProductsCount(SaledProductsCountDto dto, Products products){
        this.products = products;
        this.count = dto.getCount();
    }
}
