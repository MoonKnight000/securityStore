package uz.softex.securitystore.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkersChangePassword {
    private String OldPassword;
    private String NewPassword;
}
