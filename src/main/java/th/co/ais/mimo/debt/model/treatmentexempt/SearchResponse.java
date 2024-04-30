package th.co.ais.mimo.debt.model.treatmentexempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.treatment.ExemptDetailDto;
import th.co.ais.mimo.debt.dto.treatment.SearchTreatmentDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String errorMsg;
    private List<SearchTreatmentDto> resultList;
    private List<ExemptDetailDto> resultDetailList;
}
