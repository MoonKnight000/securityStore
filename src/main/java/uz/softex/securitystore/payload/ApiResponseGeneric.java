package uz.softex.securitystore.payload;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.views.Views;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseGeneric<T> {
    @JsonView(Views.class)
    private String message = "Success";
    @JsonView(Views.class)
    private boolean success = true;
    @JsonView(Views.class)
    private T data;

    public ApiResponseGeneric(T data){
        this.data = data;
    }
}