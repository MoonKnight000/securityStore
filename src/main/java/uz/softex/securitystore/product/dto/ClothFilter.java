package uz.softex.securitystore.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.softex.securitystore.product.entity.ClothSize;
import uz.softex.securitystore.product.entity.ClothSize.*;
import uz.softex.securitystore.product.entity.Color;
import uz.softex.securitystore.product.entity.TypeCloth;

import java.lang.reflect.Array;
import java.util.List;

import static uz.softex.securitystore.product.entity.ClothSize.*;
import static uz.softex.securitystore.product.entity.TypeCloth.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClothFilter {
    private String name = "";

    private Integer MaxCount = 2_000_000_000;
    private Integer MinCount = 0;

    private Double MinPrice = 0.0;
    private Double MaxPrice = 1_000_000_000_000_000.0;

    private Color color ;
    private List<ClothSize> clothSize = List.of(X, XL, XLS, S, SL, SXL);
    private List<TypeCloth> typeCloth = List.of(FOR_MEN, FOR_WOMEN, FOR_BABY);
}
