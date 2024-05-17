package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.CommonCheckListDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCheckListResponse {
    private List<CommonCheckListDto> resultList;
    private String errorMsg;
}
