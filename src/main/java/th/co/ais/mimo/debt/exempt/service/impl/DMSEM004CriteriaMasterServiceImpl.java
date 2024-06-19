package th.co.ais.mimo.debt.exempt.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaHistory;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaHistoryId;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMaster;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMasterId;
import th.co.ais.mimo.debt.exempt.entity.DccTempTransaction;
import th.co.ais.mimo.debt.exempt.entity.DccTempTransactionId;
import th.co.ais.mimo.debt.exempt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exempt.enums.GlobalParameterKeywordEnums;
import th.co.ais.mimo.debt.exempt.model.AddReasonDto;
import th.co.ais.mimo.debt.exempt.model.CategoryDto;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentDto;
import th.co.ais.mimo.debt.exempt.model.CommonDropdownDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.InsertAssignDto;
import th.co.ais.mimo.debt.exempt.model.SubCategoryDto;
import th.co.ais.mimo.debt.exempt.repo.DMSEM004CriteriaMasterRepo;
import th.co.ais.mimo.debt.exempt.repo.DccCriteriaHistoryRepo;
import th.co.ais.mimo.debt.exempt.model.DMSEM004GetBaByMobileNumDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004GetLoadTextDataDto;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumReq;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumResp;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdReq;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdResp;
import th.co.ais.mimo.debt.exempt.repo.DccCriteriaHistoryRepository;
import th.co.ais.mimo.debt.exempt.repo.DccGlobalParameterRepo;
import th.co.ais.mimo.debt.exempt.repo.DccTempTransactionRepository;
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

	@Autowired
	DccCriteriaHistoryRepo criteriaHistoryRepo;

	@Autowired
	DccTempTransactionRepository dccTempTransactionRepository;

	@Autowired
	DccCriteriaHistoryRepository dccCriteriaHistoryRepository;

	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception {
		return criteriaMasterDao.searchData(modeId, criteriaId, description);
	}

	@Override
	public String updateInfo(String lastUpdateBy, String blacklistDatFlag, String blacklistDatFrom, String blacklistDatTo, String modeId, Long criteriaId, String criteriaType) throws Exception {
		String errorMsg = null;
		try {
			criteriaMasterRepo.updateCriteriaInfo(lastUpdateBy, blacklistDatFlag, blacklistDatFrom, blacklistDatTo, modeId, criteriaId, criteriaType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}

	@Override
	public String deleteInfo(String modeId, Long criteriaId) throws Exception {
		String errorMsg = null;
		try {
			criteriaMasterRepo.deleteCriteriaInfo(modeId, criteriaId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}

	@Override
	public List<CommonDropdownListDto> getRegionInfoCaseDropdown() throws Exception {
		List<CommonDropdownListDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getRegionInfoCaseDropdown(ConfigSectionNameEnums.CRITERIA.toString(), GlobalParameterKeywordEnums.REGION_CODE.toString());
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<ProvinceDropdownListDto> getProvinceInfoCaseDropdown(List<String> provinveCodeList) throws Exception {
		List<ProvinceDropdownListDto> list = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(provinveCodeList)) {
				list = globalParameterRepo.getProvinceAllInfoCaseDropdown();
			} else {
				list = globalParameterRepo.getProvinceInfoCaseDropdown(StringUtils.join(provinveCodeList, ","));
			}

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<DistrictDropdownListDto> getDistrictInfoCaseDropdown(List<String> codeList) throws Exception {
		List<DistrictDropdownListDto> list = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(codeList)) {
				list = globalParameterRepo.getdistrictAllInfoCaseDropdown();
			} else {
				list = globalParameterRepo.getdistrictInfoCaseDropdown(StringUtils.join(codeList, ","));
			}

		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<SubDistrictDropdownListDto> getSubDistrictInfoCaseDropdown(List<String> aumphurCodeList) throws Exception {

		List<SubDistrictDropdownListDto> list = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(aumphurCodeList)) {
				list = globalParameterRepo.getAllSubDistrictInfoCaseDropdown();
			} else {
				list = globalParameterRepo.getSubDistrictInfoCaseDropdown(StringUtils.join(aumphurCodeList, ","));
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<ZipCodeDropdownListDto> getZipCodeInfoCaseDropdown(List<String> cityCodeList) throws Exception {
		List<ZipCodeDropdownListDto> list = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(cityCodeList)) {
				list = globalParameterRepo.getZipCodeAllInfoCaseDropdown();
			} else {
				list = globalParameterRepo.getZipCodeInfoCaseDropdown(StringUtils.join(cityCodeList, ","));
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CollectionSegmentDto> getCollectionSegment() throws Exception {
		List<CollectionSegmentDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getCollectionSegment();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getBastatus() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getBastatus();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getMobilestatus(String baStatus) throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getMobilestatus(baStatus);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getModule() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getModule();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getExemptLevel() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getExemptLevel();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getMode(String module) throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getMode(module);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getBillCycle() throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getBillCycle();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<AddReasonDto> getReason() throws Exception {
		List<AddReasonDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getReason();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CategoryDto> getCategory() throws Exception {
		List<CategoryDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getCategory();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<SubCategoryDto> getSubCategory(String category) throws Exception {
		List<SubCategoryDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getSubCategory(category);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<CommonDropdownDto> getCompanyByCode(String companyCode) throws Exception {
		List<CommonDropdownDto> list = new ArrayList<>();
		try {
			list = globalParameterRepo.getCompanyByCode(companyCode);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<GetRefAssignDto> getRefAssignId(String assignId) throws Exception {
		List<GetRefAssignDto> list = new ArrayList<>();
		try {
			list = criteriaMasterRepo.getRefAssignId(assignId);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public String insetRefAssignId(InsertAssignDto assignDto) throws Exception {
		String errorMsg = null;
		try {
			DccCriteriaMaster criteriaMaster = new DccCriteriaMaster();
			DccCriteriaMasterId criteriaMasterId = new DccCriteriaMasterId();

			criteriaMasterId.setModeId(assignDto.getModeId());
			criteriaMasterId.setCriteriaId(assignDto.getCriteriaId());
			criteriaMaster.setId(criteriaMasterId);
			criteriaMaster.setCriteriaType(assignDto.getCriteriaType());
			criteriaMaster.setCriteriaDescription(null);
			criteriaMaster.setCompanyCode(assignDto.getCompanyCode());
			criteriaMaster.setCollectionSegmentList(assignDto.getCollectionSegmentList());
			criteriaMaster.setPaymentTypeList(assignDto.getPaymentTypeList());
			criteriaMaster.setBillCycleList(assignDto.getBillCycleList());
			criteriaMaster.setRegionList(assignDto.getRegionList());
			criteriaMaster.setMobileStatusList(assignDto.getMobileStatusList());
			criteriaMaster.setProductGroupList(assignDto.getProductGroupList());
			criteriaMaster.setCateSubcateList(assignDto.getCateSubcateList());
			criteriaMaster.setBaStatusList(assignDto.getBaStatusList());
			criteriaMaster.setMinInvoiceMny(assignDto.getMinInvoiceMny());
			criteriaMaster.setInvoiceCount(assignDto.getInvoiceCount());
			criteriaMaster.setDebtMnyFrom(assignDto.getDebtMnyFrom());
			criteriaMaster.setDebtMnyTo(assignDto.getDebtMnyTo());
			criteriaMaster.setArMnyFrom(assignDto.getArMnyFrom());
			criteriaMaster.setArMnyTo(assignDto.getArMnyTo());
			criteriaMaster.setDebtAgeFrom(assignDto.getDebtAgeFrom());
			criteriaMaster.setDebtAgeTo(assignDto.getDebtAgeTo());
			criteriaMaster.setStatusAgeFrom(assignDto.getStatusAgeFrom());
			criteriaMaster.setStatusAgeTo(assignDto.getStatusAgeTo());
			criteriaMaster.setServiceAgeFrom(assignDto.getServiceAgeFrom());
			criteriaMaster.setServiceAgeTo(assignDto.getServiceAgeTo());
			criteriaMaster.setFirstArDatFrom(assignDto.getFirstArDatFrom());
			criteriaMaster.setFirstArDatTo(assignDto.getFirstArDatTo());
			criteriaMaster.setBaInactiveDatFrom(assignDto.getBaInactiveDatFrom());
			criteriaMaster.setBaInactiveDatTo(assignDto.getBaInactiveDatTo());
			criteriaMaster.setStatusReasonList(assignDto.getStatusReasonList());
			criteriaMaster.setActionReasonList(assignDto.getActionReasonList());
			criteriaMaster.setNegoFlag(assignDto.getNegoFlag());
			criteriaMaster.setExemptFlag(assignDto.getExemptFlag());
			criteriaMaster.setMaxAccount(assignDto.getMaxAccount());
			criteriaMaster.setMaxAmount(assignDto.getMaxAmount());
			criteriaMaster.setAssignDuration(assignDto.getAssignDuration());
			criteriaMaster.setCampaignCode(assignDto.getCampaignCode());
			criteriaMaster.setAgentType(assignDto.getAgentType());
			criteriaMaster.setAssignType(assignDto.getAssignType());
			criteriaMaster.setAssignJob(assignDto.getAssignJob());
			criteriaMaster.setDueDay(assignDto.getDueDay());
			criteriaMaster.setCallType(assignDto.getCallType());
			criteriaMaster.setMessageId(assignDto.getMessageId());
			criteriaMaster.setMaxCall(assignDto.getMaxCall());
			criteriaMaster.setMaxRedial(assignDto.getMaxRedial());
			criteriaMaster.setPriorityNo(null);
			criteriaMaster.setTemplateId(assignDto.getTemplateId());
			criteriaMaster.setInvoiceBackDat(assignDto.getInvoiceBackDat());
			criteriaMaster.setInvoiceInterval(assignDto.getInvoiceInterval());
			criteriaMaster.setRunType(null);
			criteriaMaster.setRunAt(null);
			criteriaMaster.setRunBillType(null);
			criteriaMaster.setRunBillDay(null);
			criteriaMaster.setRunStartDat(null);
			criteriaMaster.setRunEndDat(null);
			criteriaMaster.setAutoAssignFlag(assignDto.getAutoAssignFlag());
			criteriaMaster.setCheckDatFrom(assignDto.getCheckDatFrom());
			criteriaMaster.setCheckDatTo(assignDto.getCheckDatTo());
			criteriaMaster.setOrderLevel(assignDto.getOrderLevel());
			criteriaMaster.setBlacklistType(assignDto.getBlacklistType());
			criteriaMaster.setBlacklistSubtype(assignDto.getBlacklistSubtype());
			criteriaMaster.setLetterPaymentDue(assignDto.getLetterPaymentDue());
			criteriaMaster.setLetterDat(assignDto.getLetterDat());
			criteriaMaster.setLetterAddressType(assignDto.getLetterAddressType());
			criteriaMaster.setLetterLevel(assignDto.getLetterLevel());
			criteriaMaster.setTpDebtType(null);
			criteriaMaster.setProvinceList(assignDto.getProvinceList());
			criteriaMaster.setValueSegmentList(assignDto.getValueSegmentList());
			criteriaMaster.setDfDatFrom(assignDto.getDfDatFrom());
			criteriaMaster.setDfDatTo(assignDto.getDfDatTo());
			criteriaMaster.setLastUpdateBy(assignDto.getLastUpdateBy());
			criteriaMaster.setLastUpdateDtm(assignDto.getLastUpdateDtm());
			criteriaMaster.setOrderType(assignDto.getOrderType());
			criteriaMaster.setReasonCodeList(assignDto.getReasonCodeList());
			criteriaMaster.setBlacklistDatFlag(null);
			criteriaMaster.setBlacklistDatFrom(null);
			criteriaMaster.setBlacklistDatTo(null);
			criteriaMaster.setCaStatus(null);
			criteriaMaster.setCaInactiveDatFrom(assignDto.getCaInactiveDatFrom());
			criteriaMaster.setCaInactiveDatTo(assignDto.getCaInactiveDatTo());
			criteriaMaster.setCreditTermFlag(assignDto.getCreditTermFlag());
			criteriaMaster.setThaiLetterFlag(null);
			criteriaMaster.setIncEnvelopeFlag(null);
			criteriaMaster.setCnFlag(null);
			criteriaMaster.setUnlockModeFlag(null);
			criteriaMaster.setCompanyTypeList(assignDto.getCompanyTypeList());
			criteriaMaster.setMessageIdFbb(null);
			criteriaMaster.setTemplateIdCtype(assignDto.getTemplateIdCtype());
			criteriaMaster.setSuperDealFlag(assignDto.getSuperDealFlag());
			criteriaMaster.setFixCompanyTypeFlag(assignDto.getFixCompanyTypeFlag());
			criteriaMaster.setDeviceDiscountFrom(assignDto.getDeviceDiscountFrom());
			criteriaMaster.setDeviceDiscountTo(assignDto.getDeviceDiscountTo());
			criteriaMaster.setCreateBy(assignDto.getCreateBy());
			criteriaMaster.setCaDebtMnyFrom(null);
			criteriaMaster.setCaDebtMnyTo(null);
			criteriaMaster.setGenDetailRptFlag(assignDto.getGenDetailRptFlag());
			criteriaMaster.setBillSystemList(assignDto.getBillSystemList());
			criteriaMaster.setAutoSmsFlag(null);
			criteriaMaster.setCaDeviceDiscountFrom(null);
			criteriaMaster.setCaDeviceDiscountTo(null);
			criteriaMaster.setCpeFlag(assignDto.getCpeFlag());
			criteriaMaster.setCpeBrandList(null);
			criteriaMaster.setIvrRobotFlag(null);
			criteriaMaster.setCpePenaltyFlag(null);
			criteriaMaster.setCpePenaltyAmount(null);
			criteriaMaster.setBundlingFlag(null);
			criteriaMaster.setExemptReason(null);
			criteriaMaster.setCaAmountFlag(null);
			criteriaMaster.setCaAmount(null);
			criteriaMasterRepo.save(criteriaMaster);

			DccCriteriaHistory criteriaHistory = new DccCriteriaHistory();
			DccCriteriaHistoryId criteriaHistoryId = new DccCriteriaHistoryId();
			criteriaHistoryId.setModeId(assignDto.getModeId());
			criteriaHistoryId.setPreassignId(assignDto.getPreassignId());
			criteriaHistory.setCriteriaId(assignDto.getCriteriaId());
			criteriaHistory.setCriteriaType(assignDto.getCriteriaType());
			criteriaHistory.setPreassignDat(assignDto.getPreassignDate());
			criteriaHistory.setAssignId(assignDto.getAssignId());
			criteriaHistory.setAssignDat(assignDto.getAssignDat());
			criteriaHistory.setAssignStatus(assignDto.getAssignStatus());
			criteriaHistory.setUnassignDat(assignDto.getUnassignDat());
			criteriaHistory.setCompanyCode(assignDto.getCompanyCode());
			criteriaHistory.setCollectionSegmentList(assignDto.getCollectionSegmentList());
			criteriaHistory.setPaymentTypeList(assignDto.getPaymentTypeList());
			criteriaHistory.setBillCycleList(assignDto.getBillCycleList());
			criteriaHistory.setRegionList(assignDto.getRegionList());
			criteriaHistory.setMobileStatusList(assignDto.getMobileStatusList());
			criteriaHistory.setProductGroupList(assignDto.getProductGroupList());
			criteriaHistory.setCateSubcateList(assignDto.getCateSubcateList());
			criteriaHistory.setBaStatusList(assignDto.getBaStatusList());
			criteriaHistory.setMinInvoiceMny(assignDto.getMinInvoiceMny());
			criteriaHistory.setInvoiceCount(assignDto.getInvoiceCount());
			criteriaHistory.setDebtMnyFrom(assignDto.getDebtMnyFrom());
			criteriaHistory.setDebtMnyTo(assignDto.getDebtMnyTo());
			criteriaHistory.setArMnyFrom(assignDto.getArMnyFrom());
			criteriaHistory.setArMnyTo(assignDto.getArMnyTo());
			criteriaHistory.setDebtAgeFrom(assignDto.getDebtAgeFrom());
			criteriaHistory.setDebtAgeTo(assignDto.getDebtAgeTo());
			criteriaHistory.setStatusAgeFrom(assignDto.getStatusAgeFrom());
			criteriaHistory.setStatusAgeTo(assignDto.getStatusAgeTo());
			criteriaHistory.setServiceAgeFrom(assignDto.getServiceAgeFrom());
			criteriaHistory.setServiceAgeTo(assignDto.getServiceAgeTo());
			criteriaHistory.setFirstArDatFrom(assignDto.getFirstArDatFrom());
			criteriaHistory.setFirstArDatTo(assignDto.getFirstArDatTo());
			criteriaHistory.setBaInactiveDatFrom(assignDto.getBaInactiveDatFrom());
			criteriaHistory.setBaInactiveDatTo(assignDto.getBaInactiveDatTo());
			criteriaHistory.setStatusReasonList(assignDto.getStatusReasonList());
			criteriaHistory.setActionReasonList(assignDto.getActionReasonList());
			criteriaHistory.setNegoFlag(assignDto.getNegoFlag());
			criteriaHistory.setExemptFlag(assignDto.getExemptFlag());
			criteriaHistory.setMaxAccount(assignDto.getMaxAccount());
			criteriaHistory.setMaxAmount(assignDto.getMaxAmount());
			criteriaHistory.setAssignDuration(assignDto.getAssignDuration());
			criteriaHistory.setAssignType(assignDto.getAssignType());
			criteriaHistory.setAgentType(assignDto.getAgentType());
			criteriaHistory.setCampaignCode(assignDto.getCampaignCode());
			criteriaHistory.setDueDay(assignDto.getDueDay());
			criteriaHistory.setMessageId(assignDto.getMessageId());
			criteriaHistory.setMaxCall(assignDto.getMaxCall());
			criteriaHistory.setMaxRedial(assignDto.getMaxRedial());
			criteriaHistory.setTemplateId(assignDto.getTemplateId());
			criteriaHistory.setInvoiceBackDat(assignDto.getInvoiceBackDat());
			criteriaHistory.setAutoAssignFlag(assignDto.getAutoAssignFlag());
			criteriaHistory.setLastUpdateBy(assignDto.getLastUpdateBy());
			criteriaHistory.setLastUpdateDtm(assignDto.getLastUpdateDtm());
			criteriaHistory.setCheckDatFrom(assignDto.getCheckDatFrom());
			criteriaHistory.setCheckDatTo(assignDto.getCheckDatTo());
			criteriaHistory.setInvoiceInterval(assignDto.getInvoiceInterval());
			criteriaHistory.setUnassignReason(null);
			criteriaHistory.setCreateDtm(assignDto.getCreateDtm());
			criteriaHistory.setRunDb(assignDto.getRunDb());
			criteriaHistory.setCollectionSegmentAllFlag(assignDto.getCollectionSegmentAllFlag());
			criteriaHistory.setPaymentTypeAllFlag(assignDto.getPaymentTypeAllFlag());
			criteriaHistory.setBillCycleAllFlag(assignDto.getBillCycleAllFlag());
			criteriaHistory.setRegionAllFlag(assignDto.getRegionAllFlag());
			criteriaHistory.setMobileStatusAllFlag(assignDto.getMobileStatusAllFlag());
			criteriaHistory.setProductGroupAllFlag(assignDto.getProductGroupAllFlag());
			criteriaHistory.setCateSubcateAllFlag(assignDto.getCateSubcateAllFlag());
			criteriaHistory.setBaStatusAllFlag(assignDto.getBaStatusAllFlag());
			criteriaHistory.setStatusReasonAllFlag(assignDto.getStatusReasonAllFlag());
			criteriaHistory.setActionReasonAllFlag(assignDto.getActionReasonAllFlag());
			criteriaHistory.setOrderLevel(assignDto.getOrderLevel());
			criteriaHistory.setCancelAssignFlag(assignDto.getCancelAssignFlag());
			criteriaHistory.setBlacklistType(assignDto.getBlacklistType());
			criteriaHistory.setBlacklistSubtype(assignDto.getBlacklistSubtype());
			criteriaHistory.setLetterLevel(assignDto.getLetterLevel());
			criteriaHistory.setLetterAddressType(assignDto.getLetterAddressType());
			criteriaHistory.setLetterDat(assignDto.getLetterDat());
			criteriaHistory.setLetterPaymentDue(assignDto.getLetterPaymentDue());
			criteriaHistory.setTpDebtType(null);
			criteriaHistory.setProvinceList(assignDto.getProvinceList());
			criteriaHistory.setConfirmDat(assignDto.getConfirmDat());
			criteriaHistory.setProcessStatus(assignDto.getProcessStatus());
			criteriaHistory.setWoYear(null);
			criteriaHistory.setWoNo(null);
			criteriaHistory.setValueSegmentList(assignDto.getValueSegmentList());
			criteriaHistory.setValueSegmentAllFlag(null);
			criteriaHistory.setTextFileName(null);
			criteriaHistory.setDfDatTo(assignDto.getDfDatTo());
			criteriaHistory.setDfDatFrom(assignDto.getDfDatFrom());
			criteriaHistory.setBlacklistSeq(null);
			criteriaHistory.setOrderType(assignDto.getOrderType());
			criteriaHistory.setReasonCodeList(assignDto.getReasonCodeList());
			criteriaHistory.setAssignProcessStatus(null);
			criteriaHistory.setBlacklistDatFlag(null);
			criteriaHistory.setBlacklistDatFrom(null);
			criteriaHistory.setBlacklistDatTo(null);
			criteriaHistory.setSendAdaDtm(null);
			criteriaHistory.setCaStatus(null);
			criteriaHistory.setCaInactiveDatFrom(assignDto.getCaInactiveDatFrom());
			criteriaHistory.setCaInactiveDatTo(assignDto.getCaInactiveDatTo());
			criteriaHistory.setCriteriaDescription(null);
			criteriaHistory.setAssignJob(assignDto.getAssignJob());
			criteriaHistory.setModuleName(null);
			criteriaHistory.setInitialFlag(null);
			criteriaHistory.setAutoCriteriaFlag(null);
			criteriaHistory.setTotalAccountBa(null);
			criteriaHistory.setTotalAmount(null);
			criteriaHistory.setTotalAccountCa(null);
			criteriaHistory.setCallFlag(null);
			criteriaHistory.setCreditTermFlag(assignDto.getCreditTermFlag());
			criteriaHistory.setThaiLetterFlag(null);
			criteriaHistory.setIncEnvelopeFlag(null);
			criteriaHistory.setUnlockModeFlag(null);
			criteriaHistory.setCompanyTypeList(assignDto.getCompanyTypeList());
			criteriaHistory.setMessageIdFbb(null);
			criteriaHistory.setTemplateIdCtype(assignDto.getTemplateIdCtype());
			criteriaHistory.setSuperDealFlag(assignDto.getSuperDealFlag());
			criteriaHistory.setFixCompanyTypeFlag(assignDto.getFixCompanyTypeFlag());
			criteriaHistory.setDeviceDiscountFrom(assignDto.getDeviceDiscountFrom());
			criteriaHistory.setDeviceDiscountTo(assignDto.getDeviceDiscountTo());
			criteriaHistory.setCreateBy(assignDto.getCreateBy());
			criteriaHistory.setCaDebtMnyFrom(null);
			criteriaHistory.setCaDebtMnyTo(null);
			criteriaHistory.setGenDetailRptFlag(assignDto.getGenDetailRptFlag());
			criteriaHistory.setBillSystemList(assignDto.getBillSystemList());
			criteriaHistory.setAutoSmsFlag(null);
			criteriaHistory.setCaDeviceDiscountFrom(null);
			criteriaHistory.setCaDeviceDiscountTo(null);
			criteriaHistory.setCpeFlag(assignDto.getCpeFlag());
			criteriaHistory.setCpeBrandList(assignDto.getCpeBrandList());
			criteriaHistory.setIvrRobotFlag(null);
			criteriaHistory.setCpePenaltyFlag(null);
			criteriaHistory.setCpePenaltyAmount(null);
			criteriaHistory.setBundlingFlag(assignDto.getBundlingFlag());
			criteriaHistory.setExemptReason(assignDto.getExemptReason());
			criteriaHistory.setCaAmountFlag(null);
			criteriaHistory.setCaAmount(null);
			criteriaHistoryRepo.save(criteriaHistory);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			e.printStackTrace();
		}
		return errorMsg;
	}

	public InsertAssignIdResp insertOrUpdateCriteriaMaster(InsertAssignIdReq req) {
		InsertAssignIdResp response = new InsertAssignIdResp();
		try {
			response = validateInsertAssignIdResp(req);
			if (response.getErrorMsg() == null) {
				if (req.getCriteriaId() != null) {
					criteriaMasterDao.updateCriteriaMaster(req);
				} else {
					Long maxCriteriaId = criteriaMasterRepo.getMaxDccCriteriaId(req.getModeId());
					Long newCriteriaId = maxCriteriaId + 1;
					System.out.println("newCriteriaId : " + newCriteriaId);
					criteriaMasterDao.insertCriteriaMaster(req, newCriteriaId);
				}
			}
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}

	@Override
	public InsertAssignIdResp insertRefAssign(InsertAssignIdReq req) {
		InsertAssignIdResp response = new InsertAssignIdResp();
		try {
			response = validateInsertAssignIdResp(req);
			if (response.getErrorMsg() == null) {
				Long maxCriteriaId = criteriaMasterRepo.getMaxDccCriteriaId(req.getModeId());
				Long newCriteriaId = maxCriteriaId + 1;
				criteriaMasterDao.insertCriteriaMaster(req, newCriteriaId);

				long preAssignId = criteriaMasterRepo.getMaxPreAssignId();
				long newPreAssignId = preAssignId + 1;
				System.out.println("newCriteriaId : " + newCriteriaId + " newPreAssignId : " + newPreAssignId);
				insertDccCriteriaHistory(req, newCriteriaId, newPreAssignId);
				return response;

			}
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}

	private void insertDccCriteriaHistory(InsertAssignIdReq req, Long newCriteriaId, Long newPreAssignId) throws Exception {
		DccCriteriaHistoryId id = new DccCriteriaHistoryId("EM", Long.toString(newPreAssignId));
		DccCriteriaHistory entity = new DccCriteriaHistory();
		entity.setId(id);
		entity.setCriteriaId(newCriteriaId);
		entity.setConfirmDat(convertStringToDate(req.getProcessDate()));
		entity.setOrderType(req.getOrderLevel());
		entity.setReasonCodeList(req.getReasonCodeList());
		entity.setBlacklistSeq(0L);
		entity.setValueSegmentList(null);
		entity.setCriteriaType(req.getCriteriaType());
		entity.setPreassignDat(new Date());
		entity.setAssignStatus("");
		entity.setCompanyCode(req.getCompanyCode());
		entity.setCollectionSegmentList(req.getCollectionSegmentList());
		entity.setPaymentTypeList(null);
		entity.setBillCycleList(req.getBillCycleList());
		entity.setRegionList(req.getRegionList());
		entity.setMobileStatusList(req.getMobileStatusList());
		entity.setProductGroupList(null);
		entity.setCateSubcateList(req.getCateSubcateList());
		entity.setBaStatusList(req.getBaStatusList());
		entity.setMinInvoiceMny(BigDecimal.ZERO);
		entity.setInvoiceCount(0L);
		entity.setDebtMnyFrom(BigDecimal.ZERO);
		entity.setDebtMnyTo(BigDecimal.ZERO);
		entity.setArMnyFrom(req.getArMnyFrom());
		entity.setArMnyTo(req.getArMnyTo());
		entity.setDebtAgeFrom(0L);
		entity.setDebtAgeTo(0L);
		entity.setStatusAgeFrom(0L);
		entity.setStatusAgeTo(0L);
		entity.setServiceAgeFrom(0L);
		entity.setServiceAgeTo(0L);
		entity.setFirstArDatFrom(new Date());
		entity.setFirstArDatTo(new Date());
		entity.setBaInactiveDatFrom(convertStringToDate(req.getBaInactiveDatFrom()));
		entity.setBaInactiveDatTo(convertStringToDate(req.getBaInactiveDatTo()));
		entity.setStatusReasonList(null);
		entity.setActionReasonList(req.getActionReasonList());
		entity.setNegoFlag("S");
		entity.setExemptFlag(null);
		entity.setMaxAccount(0L);
		entity.setMaxAmount(BigDecimal.ZERO);
		entity.setAssignDuration(0L);
		entity.setAssignType(req.getAssignType());
		entity.setAgentType(req.getAgentType());
		entity.setCampaignCode(null);
		entity.setDueDay(0L);
		entity.setMessageId("");
		entity.setMaxCall(0L);
		entity.setMaxRedial(0L);
		entity.setTemplateId(null);
		entity.setCheckDatFrom(convertStringToDate(req.getCheckDatFrom()));
		entity.setCheckDatTo(convertStringToDate(req.getCheckDatTo()));
		entity.setInvoiceBackDat(null);
		entity.setInvoiceInterval(0L);
		entity.setAutoAssignFlag(req.getAutoAssignFlag());
		entity.setLastUpdateBy(req.getLastUpdateBy());
		entity.setLastUpdateDtm(new Date());
		entity.setOrderLevel(req.getOrderLevel());
		entity.setCollectionSegmentAllFlag(req.getCollectionSegmentList().equals("ALL") ? "Y" : "N");
		entity.setMobileStatusAllFlag(req.getMobileStatusList().equals("ALL") ? "Y" : "N");
		entity.setRegionAllFlag(req.getRegionList().equals("ALL") ? "Y" : "N");
		entity.setProductGroupAllFlag("N");
		entity.setCateSubcateAllFlag("N");
		entity.setBaStatusAllFlag(req.getBaStatusList().equals("ALL") ? "Y" : "N");
		entity.setActionReasonAllFlag(req.getActionReasonList().equals("ALL") ? "Y" : "N");
		entity.setStatusReasonAllFlag("N");
		entity.setPaymentTypeAllFlag("N");
		entity.setBillCycleAllFlag(req.getBillCycleList().equals("ALL") ? "Y" : "N");
		entity.setCreateDtm(new Date());
		entity.setRunDb(1L);
		entity.setProvinceList(req.getProvinceList());
		entity.setTpDebtType(null);
		entity.setLetterPaymentDue(0L);
		entity.setLetterDat(null);
		entity.setLetterAddressType(null);
		entity.setLetterLevel(null);
		entity.setDfDatFrom(convertStringToDate(req.getDfDatFrom()));
		entity.setDfDatTo(convertStringToDate(req.getDfDatTo()));
		entity.setBlacklistType(req.getBlacklistType());
		entity.setBlacklistSubtype(req.getBlacklistSubtype());
		entity.setAssignId(null);
		entity.setValueSegmentAllFlag(null);
		entity.setAssignDat(new Date());
		entity.setBlacklistDatFlag(null);
		entity.setBlacklistDatFrom(new Date());
		entity.setBlacklistDatTo(new Date());
		entity.setCaInactiveDatFrom(convertStringToDate(req.getCaInactiveDatFrom()));
		entity.setCaInactiveDatTo(convertStringToDate(req.getCaInactiveDatTo()));
		entity.setAssignJob(req.getAssignJob());
		entity.setCreditTermFlag(null);
		entity.setThaiLetterFlag('Y');
		entity.setIncEnvelopeFlag('N');
		entity.setUnlockModeFlag('N');
		entity.setCompanyTypeList("ALL");
		entity.setMessageIdFbb(null);
		entity.setTemplateIdCtype(null);
		entity.setSuperDealFlag(null);
		entity.setFixCompanyTypeFlag(null);
		entity.setDeviceDiscountFrom(BigDecimal.ZERO);
		entity.setDeviceDiscountTo(BigDecimal.ZERO);
		entity.setCreateBy(null);
		entity.setGenDetailRptFlag(null);
		entity.setBillSystemList(null);
		entity.setAutoSmsFlag('N');
		entity.setCpeBrandList(null);
		entity.setCpeFlag(null);
		entity.setIvrRobotFlag(null);
		entity.setCpePenaltyFlag(null);
		entity.setCpePenaltyAmount(BigDecimal.ZERO);
		entity.setBundlingFlag(null);
		entity.setCaAmountFlag(null);
		entity.setCaAmount(BigDecimal.ZERO);
		entity.setNegoFlag(req.getNegoFlag());
		dccCriteriaHistoryRepository.save(entity);
	}

	private Date convertStringToDate(String dateStr) throws ParseException {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY");
		return formatter.parse(dateStr);
	}

	@Override
	public GetBillAccNumByMobileNumResp validateGetBillAccNumByMobileNum(GetBillAccNumByMobileNumReq req) throws Exception {
		GetBillAccNumByMobileNumResp response = new GetBillAccNumByMobileNumResp();
		try {
			List<DMSEM004GetBaByMobileNumDto> results = new ArrayList<>();
			if (req.getMobileNumList().size() > 0) {
				for (String mobileNum : req.getMobileNumList()) {
					DMSEM004GetBaByMobileNumDto result = criteriaMasterRepo.getBaAccByMobileNum(mobileNum);
					if (result != null) {
						results.add(result);
					} else {
						DMSEM004GetBaByMobileNumDto resultNotFound = DMSEM004GetBaByMobileNumDto.createWithMobileNum(mobileNum);
						results.add(resultNotFound);
					}
				}
				response.setResultList(results);
			} else {
				response.setErrorMsg("Mobile Num is invalid.");
			}
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}

	public InsertAssignIdResp validateInsertAssignIdResp(InsertAssignIdReq req) throws Exception {
		InsertAssignIdResp response = new InsertAssignIdResp();
		String msgError = null;
		try {
			if (StringUtils.isBlank(req.getOrderType())) {
				msgError = "OrderType is invalid";
			} else if (StringUtils.isBlank(req.getReasonCodeList())) {
				msgError = "ReasonCodeList is invalid";
			} else if (StringUtils.isBlank(req.getModeId())) {
				msgError = "ModeId is invalid";
			} else if (StringUtils.isBlank(req.getCriteriaType())) {
				msgError = "CriteriaType is invalid";
			} else
			//				if (StringUtils.isBlank(req.getCriteriaDescription())) {
			//				msgError = "CriteriaDescription is invalid";
			//			} else 
			if (StringUtils.isBlank(req.getCompanyCode())) {
				msgError = "CompanyCode is invalid";
			} else if (StringUtils.isBlank(req.getCollectionSegmentList())) {
				msgError = "CollectionSegmentList is invalid";
			} else if (StringUtils.isBlank(req.getBillCycleList())) {
				msgError = "BillCycleList is invalid";
			} else if (StringUtils.isBlank(req.getRegionList())) {
				msgError = "RegionList is invalid";
			} else if (StringUtils.isBlank(req.getMobileStatusList())) {
				msgError = "MobileStatusList is invalid";
			} else if (StringUtils.isBlank(req.getCateSubcateList())) {
				msgError = "CateSubcateList is invalid";
			} else if (StringUtils.isBlank(req.getBaStatusList())) {
				msgError = "BaStatusList is invalid";
			} else if (req.getArMnyFrom() == null) {
				msgError = "ArMnyFrom is invalid";
			} else if (req.getArMnyTo() == null) {
				msgError = "ArMnyTo is invalid";
			} else if (StringUtils.isBlank(req.getBaInactiveDatFrom())) {
				msgError = "BaInactiveDatFrom is invalid";
			} else if (StringUtils.isBlank(req.getBaInactiveDatTo())) {
				msgError = "BaInactiveDatTo is invalid";
			} else if (StringUtils.isBlank(req.getActionReasonList())) {
				msgError = "ActionReasonList is invalid";
			} else if (req.getAssignDuration() == null) {
				msgError = "AssignDuration is invalid";
			} else if (StringUtils.isBlank(req.getAgentType())) {
				msgError = "AgentType is invalid";
			} else if (StringUtils.isBlank(req.getAssignType())) {
				msgError = "AssignType is invalid";
			} else if (StringUtils.isBlank(req.getAssignJob())) {
				msgError = "AssignJob is invalid";
			} else if (StringUtils.isBlank(req.getCheckDatFrom())) {
				msgError = "CheckDatFrom is invalid";
			} else if (StringUtils.isBlank(req.getCheckDatTo())) {
				msgError = "CheckDatTo is invalid";
			} else if (StringUtils.isBlank(req.getProcessDate())) {
				msgError = "ProcessDate is invalid";
			} else if (StringUtils.isBlank(req.getEffectiveDate())) {
				msgError = "EffectiveDate is invalid";
			} else if (StringUtils.isBlank(req.getEffectiveEndDate())) {
				msgError = "EffectiveEndDate is invalid";
			} else if (StringUtils.isBlank(req.getAutoAssignFlag())) {
				msgError = "AutoAssignFlag is invalid";
			} else if (StringUtils.isBlank(req.getLastUpdateBy())) {
				msgError = "LastUpdateBy is invalid";
			} else if (StringUtils.isBlank(req.getOrderLevel())) {
				msgError = "OrderLevel is invalid";
			} else if (StringUtils.isBlank(req.getBlacklistType())) {
				msgError = "BlacklistType is invalid";
			} else if (StringUtils.isBlank(req.getBlacklistSubtype())) {
				msgError = "BlacklistSubtype is invalid";
			} else if (StringUtils.isBlank(req.getProvinceList())) {
				msgError = "ProvinceList is invalid";
			} else if (StringUtils.isBlank(req.getDfDatFrom())) {
				msgError = "DfDatFrom is invalid";
			} else if (StringUtils.isBlank(req.getDfDatTo())) {
				msgError = "DfDatTo is invalid";
			} else if (StringUtils.isBlank(req.getCreateBy())) {
				msgError = "CreateBy is invalid";
			} else if (StringUtils.isBlank(req.getCaInactiveDatFrom())) {
				msgError = "CaInactiveDatFrom is invalid";
			} else if (StringUtils.isBlank(req.getCaInactiveDatTo())) {
				msgError = "CaInactiveDatTo is invalid";
			}
			response.setErrorMsg(msgError);
		} catch (Exception e) {
			log.error("Exception insertCriteriaMaster : {}", e.getMessage(), e);
			throw e;
		}
		return response;
	}

	public InsertAssignIdResp uploadBillAccMobileNum(InsertAssignIdReq req) {
		InsertAssignIdResp response = new InsertAssignIdResp();
		try {
			//			response = validateInsertAssignIdResp(req);
			if (response.getErrorMsg() == null) {
				Long maxCriteriaId = criteriaMasterRepo.getMaxDccCriteriaId(req.getModeId());
				Long newCriteriaId = maxCriteriaId + 1;
				criteriaMasterDao.insertCriteriaMaster(req, newCriteriaId);

				long preAssignId = criteriaMasterRepo.getMaxPreAssignId();
				long newPreAssignId = preAssignId + 1;
				System.out.println("newCriteriaId : " + newCriteriaId + " newPreAssignId : " + newPreAssignId);
				insertDccCriteriaHistory(req, newCriteriaId, newPreAssignId);

				List<DccTempTransaction> listSave = new ArrayList<>();
				for (DMSEM004GetLoadTextDataDto tempTransactionList : req.getLoadTextData()) {
					DccTempTransactionId tempTransactionId = new DccTempTransactionId();
					DccTempTransaction tempTransaction = new DccTempTransaction();

					tempTransactionId.setModeId(req.getModeId());
					tempTransactionId.setPreAssignId(Long.toString(newPreAssignId));
					tempTransactionId.setBillingAccNum(tempTransactionList.getBillAccNum());
					tempTransactionId.setMobileNum(tempTransactionList.getMobileNum() != null ? tempTransactionList.getMobileNum() : "0000000000");
					tempTransaction.setId(tempTransactionId);
					tempTransaction.setCriteriaId(newCriteriaId);
					tempTransaction.setCompanyCode(req.getCompanyCode());
					tempTransaction.setCollectionSegment(req.getCollectionSegmentList());
					tempTransaction.setBillCycle(req.getBillCycleList());
					tempTransaction.setRegionCode(req.getRegionList());
					tempTransaction.setBaStatus(req.getBaStatusList());
					tempTransaction.setLastUpdateBy(req.getLastUpdateBy());
					tempTransaction.setProvinceCode(req.getProvinceList());
					tempTransaction.setZipCode(req.getAgentType());
					tempTransaction.setStatusReason(req.getStatusReasonList());
					tempTransaction.setMobileStatus(req.getMobileStatusList());
					tempTransaction.setOrderType(req.getOrderType());
					tempTransaction.setReasonCodeList(req.getReasonCodeList());
					listSave.add(tempTransaction);
				}
				dccTempTransactionRepository.saveAll(listSave);

				criteriaMasterRepo.dccLoadTextExempt(req.getModeId(), Long.toString(newPreAssignId));
				//				CALL PLUGIN.DCCP_LOAD_TEXT_EXEMPT(:V_PS_MODE_ID, :V_PS_PREASSIGN_ID);
			}
		} catch (Exception e) {
			log.error("Exception uploadBillAccMobileNum : {}", e.getMessage(), e);
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}
}
