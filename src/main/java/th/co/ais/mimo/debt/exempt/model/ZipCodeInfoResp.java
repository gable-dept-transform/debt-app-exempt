package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZipCodeInfoResp {
	
	String errorMsg;
	List<ZipCodeDropdownListDto> listSubDistrict;
	

}
