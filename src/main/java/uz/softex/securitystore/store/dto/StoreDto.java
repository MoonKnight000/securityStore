package uz.softex.securitystore.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.store.entity.Store;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    @NotBlank
    private String name;
    private Double balance;
    @NotNull
    private Integer address;

    public StoreDto(Store store) {
        name = store.getName();
        balance = store.getBalance();
        address = store.getAddress().getId();
    }
}
