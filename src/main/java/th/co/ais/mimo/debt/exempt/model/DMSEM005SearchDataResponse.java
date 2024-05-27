package th.co.ais.mimo.debt.exempt.model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005SearchDataDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DMSEM005SearchDataResponse {
	private List<DMSEM005SearchDataDto> resultList;
	private String errorMsg;
}
