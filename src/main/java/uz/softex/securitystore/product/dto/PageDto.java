package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    @Min(0)
    private Integer size;
    @Min(0)
    private Integer page;
    private T list;
}
