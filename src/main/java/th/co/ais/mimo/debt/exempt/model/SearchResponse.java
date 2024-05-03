package th.co.ais.mimo.debt.exempt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;

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
