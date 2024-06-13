package th.co.ais.mimo.debt.exempt.service;

import java.util.List;

import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.DistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.GetRefAssignDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;
import th.co.ais.mimo.debt.exempt.model.AddReasonDto;
import th.co.ais.mimo.debt.exempt.model.CategoryDto;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentDto;
import th.co.ais.mimo.debt.exempt.model.CommonDropdownDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.InsertAssignDto;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumReq;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumResp;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdReq;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdResp;
import th.co.ais.mimo.debt.exempt.model.SubCategoryDto;

public interface DMSEM004CriteriaMasterService {

	List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception;

	String updateInfo(String lastUpdateBy, String blacklistDatFlag, String blacklistDatFrom, String blacklistDatTo, String modeId, Long criteriaId, String criteriaType) throws Exception;

	String deleteInfo(String modeId, Long criteriaId) throws Exception;

	List<CommonDropdownListDto> getRegionInfoCaseDropdown() throws Exception;

	List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(List<String> provinveCodeList) throws Exception;

	List<DistrictDropdownListDto> getDistrictInfoCaseDropdown(List<String> codeList) throws Exception;

	List<SubDistrictDropdownListDto> getSubDistrictInfoCaseDropdown(List<String> aumphurCodeList) throws Exception;

	List<ZipCodeDropdownListDto> getZipCodeInfoCaseDropdown(List<String> cityCodeList) throws Exception;

	List<CollectionSegmentDto> getCollectionSegment() throws Exception;

	List<CommonDropdownDto> getBastatus() throws Exception;

	List<CommonDropdownDto> getMobilestatus() throws Exception;

	List<CommonDropdownDto> getModule() throws Exception;

	List<CommonDropdownDto> getExemptLevel() throws Exception;

	List<CommonDropdownDto> getMode(String module) throws Exception;

	List<CommonDropdownDto> getBillCycle() throws Exception;

	List<AddReasonDto> getReason() throws Exception;

	List<CategoryDto> getCategory() throws Exception;

	List<SubCategoryDto> getSubCategory(String category) throws Exception;

	List<CommonDropdownDto> getCompanyByCode(String companyCode) throws Exception;

	List<GetRefAssignDto> getRefAssignId(String assignId) throws Exception;
	
	String insetRefAssignId(InsertAssignDto assignDto) throws Exception;

	InsertAssignIdResp insertOrUpdateCriteriaMaster(InsertAssignIdReq req) throws Exception;

	GetBillAccNumByMobileNumResp validateGetBillAccNumByMobileNum(GetBillAccNumByMobileNumReq req) throws Exception;

	InsertAssignIdResp insertRefAssign(InsertAssignIdReq req) throws Exception;

	InsertAssignIdResp uploadBillAccMobileNum(InsertAssignIdReq request) throws Exception;

}
