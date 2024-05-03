package th.co.ais.mimo.debt.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.dto.DccExemptCateMaster;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExemptCateDetailResponse {
    private List<DccExemptCateDetail> resultList;
    private String errorMsg;
}
