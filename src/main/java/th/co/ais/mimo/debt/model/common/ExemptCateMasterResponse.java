package th.co.ais.mimo.debt.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExemptCateMasterResponse {
    private List<DccExemptCateMaster> resultList;
    private String errorMsg;
}
