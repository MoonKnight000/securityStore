package uz.softex.securitystore.inputs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.inputs.entity.Inputs;
import uz.softex.securitystore.saledProducts.dto.SaledProductsCountDto;
import uz.softex.securitystore.saledProducts.entity.SaledProductsCount;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputsDto {
    private Integer id;
    private Double amount;
    @NotNull
    private List<@Valid SaledProductsCountDto> salesProducts;
    private Integer paymentType;
    private String createdAt;

    public InputsDto(Inputs inputs) {
        salesProducts = new ArrayList<>();
        id = inputs.getId();
        amount = inputs.getAmount();
        paymentType = inputs.getPaymentType().getId();
        createdAt = inputs.getCreatedTime().toString();
        List<SaledProductsCount> saledProductsCounts = inputs.getSaledProductsCounts();
        saledProductsCounts.forEach(i -> salesProducts.add(new SaledProductsCountDto(i)));
    }
}