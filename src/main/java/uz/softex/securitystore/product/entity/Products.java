package uz.softex.securitystore.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.config.AbstractEntity;
import uz.softex.securitystore.product.dto.ProductDto;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.workers.entity.Workers;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "myDtype")

public class Products extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer count;
    private Double price;
    @ManyToOne
    private Store store;

    private boolean deleted = false;

    private LocalDateTime deletedAt;
    @ManyToOne
    private Workers deletedBy;
    @Column(insertable = false, updatable = false)
    private String myDtype;

    public Products(ProductDto dto, Store store) {
        this.name = dto.getName();
        this.count = dto.getCount();
        this.price = dto.getPrice();
        this.store = store;
    }
}

