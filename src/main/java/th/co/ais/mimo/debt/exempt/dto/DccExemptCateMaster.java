package th.co.ais.mimo.debt.exempt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DccExemptCateMaster {
    private String cateCode;
    private String cateDescription;
    private String exemptReason;
    private String activeFlag;
    private String lastUpdateBy;
    private String lastUpdateDtm;
}
