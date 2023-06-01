package uz.softex.securitystore.currency.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.softex.securitystore.config.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Currency extends AbstractEntity {
    @JsonProperty("Code")
    private String Code;
    @JsonProperty("Ccy")
    private String Ccy;
    @JsonProperty("CcyNm_RU")
    private String CcyNm_RU;
    @JsonProperty("CcyNm_UZ")
    private String CcyNm_UZ;
    @JsonProperty("CcyNm_UZC")
    private String CcyNm_UZC;
    @JsonProperty("CcyNm_EN")
    private String CcyNm_EN;
    @JsonProperty("Nominal")
    private String Nominal;
    @JsonProperty("Rate")
    private String rate;
    @JsonProperty("Diff")
    private String diff;
    @JsonProperty("Date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

}
