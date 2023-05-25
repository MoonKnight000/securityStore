package uz.softex.securitystore.outputs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.config.AbstractEntity;
import uz.softex.securitystore.inputs.entity.PaymentType;
import uz.softex.securitystore.outputs.dto.OutputsDto;
import uz.softex.securitystore.product.entity.Products;
import uz.softex.securitystore.store.entity.Store;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outputs extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Products products;
    private Integer count;
    private Double amount;
    @ManyToOne
    private PaymentType paymentType;
    @ManyToOne
    private Store store;

    public Outputs(OutputsDto outputsDto, Products products, PaymentType paymentType, Store store) {
        this.id = outputsDto.getId();
        this.products = products;
        this.paymentType = paymentType;
        this.store = store;
        this.count = outputsDto.getCount();
        this.amount = count * products.getPrice();
    }
}
