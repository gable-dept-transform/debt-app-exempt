package th.co.ais.mimo.debt.exempt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import th.co.ais.mimo.debt.exempt.dao.DMSEM004CriteriaMasterDao;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.DistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.GetRefAssignDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;
import th.co.ais.mimo.debt.exempt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exempt.enums.GlobalParameterKeywordEnums;
import th.co.ais.mimo.debt.exempt.model.AddReasonDto;
import th.co.ais.mimo.debt.exempt.model.CategoryDto;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentDto;
import th.co.ais.mimo.debt.exempt.model.CommonDropdownDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.SubCategoryDto;
import th.co.ais.mimo.debt.exempt.repo.DMSEM004CriteriaMasterRepo;
import th.co.ais.mimo.debt.exempt.repo.DccGlobalParameterRepo;
import th.co.ais.mimo.debt.exempt.service.DMSEM004CriteriaMasterService;

@Service
@Transactional
public class DMSEM004CriteriaMasterServiceImpl implements DMSEM004CriteriaMasterService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DMSEM004CriteriaMasterRepo criteriaMasterRepo;
	
	@Autowired
	DMSEM004CriteriaMasterDao criteriaMasterDao;
	
	@Autowired
	DccGlobalParameterRepo globalParameterRepo;
	
	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception {
		return criteriaMasterDao.searchData(modeId,  criteriaId,  description);
	}

	@Override
	public String updateInfo(String lastUpdateBy, String blacklistDatFlag, String blacklistDatFrom,
			String blacklistDatTo, String modeId, Long criteriaId, String criteriaType) throws Exception {
		String errorMsg = null;
		try {
			 criteriaMasterRepo.updateCriteriaInfo(lastUpdateBy, blacklistDatFlag, blacklistDatFrom, blacklistDatTo, modeId, criteriaId, criteriaType);
		}catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}

	@Override
	public String deleteInfo(String modeId, Long criteriaId) throws Exception {
		String errorMsg = null;
		try {
			 criteriaMasterRepo.deleteCriteriaInfo(modeId, criteriaId); 
		}catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}

	@Override
	public List<CommonDropdownListDto> getRegionInfoCaseDropdown() throws Exception {
		List<CommonDropdownListDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getRegionInfoCaseDropdown(ConfigSectionNameEnums.CRITERIA.toString(), GlobalParameterKeywordEnums.REGION_CODE.toString());
		}catch (Exception e) {
		 throw e;
		}
		return list;
	}

	@Override
	public List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(List<String> provinveCodeList) throws Exception {
		List<ProvinceDropdownListDto> list = new ArrayList<>();
		try {
			if(CollectionUtils.isEmpty(provinveCodeList)) {
				list = globalParameterRepo.getProvinceAllInfoCaseDropdown();
			}else {
				list = globalParameterRepo.getProvinceInfoCaseDropdown(StringUtils.join(provinveCodeList, ","));
			}
			
		}catch (Exception e) {
		 throw e;
		}
		return list;
	}

	@Override
	public List<DistrictDropdownListDto> getDistrictInfoCaseDropdown(List<String> codeList) throws Exception {
		List<DistrictDropdownListDto> list = new ArrayList<>();
		try {
			if(CollectionUtils.isEmpty(codeList)) {
				list = globalParameterRepo.getdistrictAllInfoCaseDropdown();				
			}else {
				list = globalParameterRepo.getdistrictInfoCaseDropdown(StringUtils.join(codeList, ","));
			}
			
		}catch (Exception e) {
		 throw e;
		}
		return list;	
	}

	@Override
	public List<SubDistrictDropdownListDto> getSubDistrictInfoCaseDropdown(List<String> aumphurCodeList) throws Exception {
		
		List<SubDistrictDropdownListDto> list = new ArrayList<>();
		try {
			if(CollectionUtils.isEmpty(aumphurCodeList)) {
				list = globalParameterRepo.getAllSubDistrictInfoCaseDropdown();				
			}else {
				list = globalParameterRepo.getSubDistrictInfoCaseDropdown(StringUtils.join(aumphurCodeList, ","));
			}			
		}catch (Exception e) {
			throw e;
		}
		return list;	
	}

	@Override
	public List<ZipCodeDropdownListDto> getZipCodeInfoCaseDropdown(List<String> cityCodeList) throws Exception {
		List<ZipCodeDropdownListDto> list = new ArrayList<>();
		try {
			if(CollectionUtils.isEmpty(cityCodeList)) {
				list = globalParameterRepo.getZipCodeAllInfoCaseDropdown();				
			}else {
				list = globalParameterRepo.getZipCodeInfoCaseDropdown(StringUtils.join(cityCodeList, ","));
			}			
		}catch (Exception e) {
			throw e;
		}
		return list;	
	}

	@Override
	public List<CollectionSegmentDto> getCollectionSegment() throws Exception {
		List<CollectionSegmentDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getCollectionSegment();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getBastatus() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getBastatus();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getMobilestatus() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getMobilestatus();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getModule() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getModule();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getExemptLevel() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getExemptLevel();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getMode(String module) throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getMode(module);									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getBillCycle() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getBillCycle();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<AddReasonDto> getReason() throws Exception {
		List<AddReasonDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getReason();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CategoryDto> getCategory() throws Exception {
		List<CategoryDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getCategory();									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<SubCategoryDto> getSubCategory(String category) throws Exception {
		List<SubCategoryDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getSubCategory(category);									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getCompanyByCode(String companyCode) throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {			
				list = globalParameterRepo.getCompanyByCode(companyCode);									
		}catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<GetRefAssignDto> getRefAssignId(String assignId) throws Exception {
		List<GetRefAssignDto> list = new ArrayList<>();
		try {	
			list = criteriaMasterRepo.getRefAssignId(assignId);													
		}catch (Exception e) {
			throw e;
		}
		return list;
	}
	
}
