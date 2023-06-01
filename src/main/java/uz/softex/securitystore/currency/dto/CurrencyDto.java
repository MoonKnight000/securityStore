package uz.softex.securitystore.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDto {
    private Integer id;
    @JsonProperty("Code")
    private String code;
    private String  Ccy;
    private String CcyNm_RU;
    private String CcyNm_UZ;
    private String CcyNm_UZC;
    private String CcyNm_EN;
    private String Nominal;
    private  String Rate;
    private  String Diff;
    private java.util.Date Date;
}
