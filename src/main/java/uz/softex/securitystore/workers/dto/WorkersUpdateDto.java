package uz.softex.securitystore.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkersUpdateDto {
    private Integer id;
    @NotBlank
    private String username;

    @NotBlank
    private String fullname;

    private Integer salary;

    private Integer store;

    private Integer position;
}
