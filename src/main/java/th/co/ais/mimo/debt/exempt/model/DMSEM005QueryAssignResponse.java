package th.co.ais.mimo.debt.exempt.model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005QueryAssignDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DMSEM005QueryAssignResponse {
	private List<DMSEM005QueryAssignDto> resultList;
	private String errorMsg;
}
