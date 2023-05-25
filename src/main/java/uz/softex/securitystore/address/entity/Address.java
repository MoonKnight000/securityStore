package uz.softex.securitystore.address.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.softex.securitystore.address.dto.AddressDto;
import uz.softex.securitystore.config.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)//403
public class Address extends AbstractEntity {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @Min(1)
    private Integer homeNumber;

    public Address (AddressDto addressDto){
        this.city = addressDto.getCity();
        this.street = addressDto.getStreet();
        this.homeNumber = addressDto.getHomeNumber();
    }

    public void claim(AddressDto addressDto) {
        setId(addressDto.getId());
        this.city = addressDto.getCity();
        this.street = addressDto.getStreet();
        this.homeNumber = addressDto.getHomeNumber();
    }
}
