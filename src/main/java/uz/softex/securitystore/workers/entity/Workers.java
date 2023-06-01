package uz.softex.securitystore.workers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.softex.securitystore.store.entity.Store;
import uz.softex.securitystore.position.entity.Position;
import uz.softex.securitystore.position.entity.PositionTypes;
import uz.softex.securitystore.workers.dto.WorkersDto;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Workers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String fullname;

    private Integer salary;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne
    private Position position;

    private boolean enabled;

    public Workers(WorkersDto dto, Store store, Position position) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.fullname = dto.getFullname();
        this.salary = dto.getSalary();
        this.store = store;
        this.position = position;
        this.enabled = false;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<PositionTypes> permissionList = this.position.getPermissionList();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (PositionTypes positionTypes : permissionList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(positionTypes.name()));//bu granted authities dan extends olgan
        }
        return grantedAuthorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
