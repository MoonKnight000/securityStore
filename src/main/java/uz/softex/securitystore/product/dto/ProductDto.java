package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.Products;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = "name is empty")
    private String name;
    @Min(value = 0, message = "count must be bigger than zero ")
    private Integer count;
    @Min(value = 0, message = "price must be bigger than zero")
    private Double price;
    private String dtype;


    public ProductDto(Products products) {
        this.price = products.getPrice();
        this.name = products.getName();
        this.count = products.getCount();
        this.dtype = products.getMyDtype();
        this.id = products.getId();
    }
}
