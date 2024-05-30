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
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exempt.enums.GlobalParameterKeywordEnums;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
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
	
	
}
