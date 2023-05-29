package uz.softex.securitystore.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.workers.entity.Workers;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkersDto {
    private Integer id;
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String fullname;

    private Integer salary;

    private Integer store;

    private Integer position;


    public WorkersDto(Workers workers) {
        id = workers.getId();
        username = workers.getUsername();
        password = workers.getPassword();
        fullname = workers.getFullname();
        salary = workers.getSalary();
        if(workers.getStore()!=null)
        store = workers.getStore().getId();
        position = workers.getPosition().getId();
    }
}
