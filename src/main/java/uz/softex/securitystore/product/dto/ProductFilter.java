package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {
    private String name = "";
    @Min(0)
    private Double MinPrice = 0.0;
    @Min(0)
    private Integer MaxCount = 2_000_000_000;
    @Min(0)
    private Integer MinCount = 0;
    @Min(0)
    private Double MaxPrice = 1_000_000_000_000_000.0;

    private Map<String ,String > map;

}