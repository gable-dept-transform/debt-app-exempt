package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DMSEM004SearchDataResp {
	
	private String errorMsg;
	private List<DMSEM004CriteriaMasterBean> criteriaMasterDto; 

}
