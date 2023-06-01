package uz.softex.securitystore.inputs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.config.AbstractEntity;
import uz.softex.securitystore.inputs.dto.InputsDto;
import uz.softex.securitystore.product.entity.Products;
import uz.softex.securitystore.saledProducts.dto.SaledProductsCountDto;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;
import uz.softex.securitystore.store.entity.Store;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Inputs extends AbstractEntity {

    private Double amount;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SaledProductsCount> saledProductsCounts;
    @ManyToOne
    private PaymentType paymentType;
    @ManyToOne
    private Store store;

    public Inputs(InputsDto dto, PaymentType paymentType, Store store, List<Products> productsList, List<SaledProductsCountDto> saledProductsCountDtoList) {
        this.saledProductsCounts = new ArrayList<>();
        this.amount = 0.0;
        this.paymentType = paymentType;
        this.store = store;
        for (int i = 0; i < saledProductsCountDtoList.size(); i++) {
           this.amount += saledProductsCountDtoList.get(i).getCount() * productsList.get(i).getPrice();
            saledProductsCounts.add(new SaledProductsCount(saledProductsCountDtoList.get(i), productsList.get(i)));
        }
    }
}
