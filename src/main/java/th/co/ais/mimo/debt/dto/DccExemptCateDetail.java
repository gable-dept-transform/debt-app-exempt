package th.co.ais.mimo.debt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DccExemptCateDetail {
    private String modeId;
    private String moduleCode;
    private Long exemptDuration;
    private String expireDate;
    private String lastUpdateBy;
    private String lastUpdateDtm;
    private String exemptLevel;
}
