package uz.softex.securitystore.payload;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.views.Views;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    @JsonView(Views.class)
    private String message="succesfully";
    @JsonView(Views.class)
    private boolean success=true;
}
