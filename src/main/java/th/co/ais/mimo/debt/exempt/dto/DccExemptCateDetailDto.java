package th.co.ais.mimo.debt.exempt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DccExemptCateDetailDto {
    private String modeId;
    private String moduleCode;
    private Long exemptDuration;
    private String expireDate;
    private String lastUpdateBy;
    private String lastUpdateDtm;
    private String exemptLevel;
}
