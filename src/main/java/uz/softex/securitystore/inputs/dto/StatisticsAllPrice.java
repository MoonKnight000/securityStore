package uz.softex.securitystore.inputs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsAllPrice {
    private Double amount;
    private String name;
}