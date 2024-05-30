package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionInfoResp {
	
	String errorMsg;
	List<CommonDropdownListDto> listRegion;

}
