package uz.softex.securitystore.inputs.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.inputs.entity.Inputs;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;
import uz.softex.securitystore.views.Views;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDto {
    private Integer count;

    private String productName;

    private Double amount;

    public StatisticsDto(SaledProductsCount saledProductsCount) {
        count = saledProductsCount.getCount();
        productName = saledProductsCount.getProducts().getName();
        amount = count * saledProductsCount.getProducts().getPrice();
    }

    public void add(StatisticsDto dto) {
        count += dto.getCount();
        amount += dto.getAmount();
    }
}