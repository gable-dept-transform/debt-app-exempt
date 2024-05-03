package th.co.ais.mimo.debt.exempt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetail;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExemptCateDetailResponse {
    private List<DccExemptCateDetail> resultList;
    private String errorMsg;
}
