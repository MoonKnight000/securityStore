package uz.softex.securitystore.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.address.entity.Address;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Integer id;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @Min(0)
    private Integer homeNumber;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.homeNumber = address.getHomeNumber();
    }
}
