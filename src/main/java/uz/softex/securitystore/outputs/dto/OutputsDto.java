package uz.softex.securitystore.outputs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.outputs.entity.Outputs;

import javax.validation.constraints.Min;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputsDto {
    private Integer id;
    private Integer products;
    @Min(0)
    private Integer count;
    private Double amount;
    private Integer paymentType;

    public OutputsDto(Outputs outputs) {
        id = outputs.getId();
        products = outputs.getProducts().getId();
        count = outputs.getCount();
        amount = outputs.getAmount();
        paymentType = outputs.getPaymentType().getId();
    }
}
