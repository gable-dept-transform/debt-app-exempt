package th.co.ais.mimo.debt.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDropDownResponse {
    private List<CommonDropdownListDto> resultList;
    private String errorMsg;
}
