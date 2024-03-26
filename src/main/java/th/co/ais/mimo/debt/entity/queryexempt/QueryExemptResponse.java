package th.co.ais.mimo.debt.entity.queryexempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.DcExemptDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryExemptResponse {
    private String status;
    private String message;

    private List<DcExemptDto> currentDataList;
}
