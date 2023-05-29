package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.DrinksType;

import javax.validation.constraints.Min;
import java.util.List;

import static uz.softex.securitystore.product.entity.DrinksType.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinksFilter {
    private String name = "";
    @Min(0)
    private Integer MaxCount = 2_000_000_000;
    @Min(0)
    private Integer MinCount = 0;

    @Min(0)
    private Double MinPrice = 0.0;
    @Min(0)
    private Double MaxPrice = 1_000_000_000_000_000.0;

    private String brand = "";

    private List<DrinksType> type= List.of(SHARBAT, GAZLI, MINERAL, ENERGETIK, ALKAGOL);

}
