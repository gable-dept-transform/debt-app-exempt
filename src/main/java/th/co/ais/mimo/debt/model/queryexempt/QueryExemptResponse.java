package th.co.ais.mimo.debt.model.queryexempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptCurrentDtoMapping;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryExemptResponse {
    private String errorMsg;
    private List<DcExemptCurrentDtoMapping> resultCurrentList;
    private List<DcExempHistoryDto> resultHistoryList;
}
