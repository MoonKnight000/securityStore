package uz.softex.securitystore.position.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.position.entity.Position;
import uz.softex.securitystore.position.entity.PositionTypes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private List<PositionTypes> permissionList;

    public PositionDto(Position position) {
        id = position.getId();
        name = position.getName();
        permissionList = position.getPermissionList();
    }
}
