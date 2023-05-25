package uz.softex.securitystore.position.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.softex.securitystore.config.AbstractEntity;
import uz.softex.securitystore.position.dto.PositionDto;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Position extends AbstractEntity {
    private String name;
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)//bu turli xil aobektlar toplamiligini bildiradi
    private List<PositionTypes> permissionList;

    public Position(PositionDto dto) {
        setId(dto.getId());
        this.name = dto.getName();
        this.permissionList = dto.getPermissionList();
    }
    public void claim(PositionDto dto) {
        setId(dto.getId());
        this.name = dto.getName();
        this.permissionList = dto.getPermissionList();
    }
}