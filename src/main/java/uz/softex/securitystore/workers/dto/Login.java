package uz.softex.securitystore.workers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @NotBlank
    @Schema(defaultValue = "murodjon000")
    private   String username;
    @NotBlank
    @Schema(defaultValue = "12345678")//bu default nima qoyishni
    private String password;
}
