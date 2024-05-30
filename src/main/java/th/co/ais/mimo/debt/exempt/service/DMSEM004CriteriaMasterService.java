package th.co.ais.mimo.debt.exempt.service;

import java.util.List;

import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.DistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;


public interface DMSEM004CriteriaMasterService {
	
	List<DMSEM004CriteriaMasterBean> searchData( String modeId, Long criteriaId, String description) throws Exception;
	
	String updateInfo( String lastUpdateBy, String blacklistDatFlag, String blacklistDatFrom, String blacklistDatTo, String modeId, Long criteriaId, String criteriaType) throws Exception;
	
	String deleteInfo( String modeId, Long criteriaId) throws Exception;
	
	List<CommonDropdownListDto> getRegionInfoCaseDropdown() throws Exception;
	
	List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(List<String> provinveCodeList) throws Exception;

	List<DistrictDropdownListDto> getDistrictInfoCaseDropdown(List<String> codeList) throws Exception ;

	List<SubDistrictDropdownListDto> getSubDistrictInfoCaseDropdown(List<String> aumphurCodeList) throws Exception;
	
	List<ZipCodeDropdownListDto> getZipCodeInfoCaseDropdown(List<String> cityCodeList) throws Exception;
}
