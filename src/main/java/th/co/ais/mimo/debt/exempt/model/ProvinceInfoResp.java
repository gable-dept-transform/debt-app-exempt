package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceInfoResp {
	
	String errorMsg;
	List<ProvinceDropdownListDto> listProvince;

}
