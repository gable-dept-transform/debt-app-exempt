package th.co.ais.mimo.debt.exempt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import th.co.ais.mimo.debt.exempt.dao.CommonDao;
import th.co.ais.mimo.debt.exempt.dto.CommonCheckListDto;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.CpLocationDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.exempt.dto.GenReportSeqDto;
import th.co.ais.mimo.debt.exempt.enums.ConfigLovEnums;
import th.co.ais.mimo.debt.exempt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.repo.AscLocationRelationRepository;
import th.co.ais.mimo.debt.exempt.repo.CpLocationMasterRepository;
import th.co.ais.mimo.debt.exempt.repo.DccGlobalParameterRepo;
import th.co.ais.mimo.debt.exempt.repo.DccReasonRepo;
import th.co.ais.mimo.debt.exempt.repo.DccReportMasterRepo;
import th.co.ais.mimo.debt.exempt.repo.SffLovMasterRepository;

@Service
@Transactional
public class CommonService {

    @Autowired
    private DccGlobalParameterRepo dccGlobalParameterRepo;

    @Autowired
    private DccReasonRepo dccReasonRepo;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private DccReportMasterRepo dccReportMasterRepo;

    @Autowired
    private SffLovMasterRepository sffLovMasterRepository;

    @Autowired
    private AscLocationRelationRepository ascLocationRelationRepository;

    @Autowired
    private CpLocationMasterRepository cpLocationMasterRepository;

    public List<CommonDropdownListDto> getMobileStatus() throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.MOBILE_STATUS.toString(),
                ConfigSectionNameEnums.EXEMPT_CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getExemptAction() throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.EXEMPT_ACTION.toString(),
                ConfigSectionNameEnums.CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getGlobalParameter(String keyword, String sectionName) throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(keyword, sectionName);
    }

    public List<CommonDropdownListDto> getDccReason(String reasonType) throws Exception {
        return dccReasonRepo.findByReasonType(reasonType);
    }

    public List<CommonDropdownListDto> getDccReason(String reasonCode, String reasonType) throws Exception {
        return dccReasonRepo.findReasonByCodeAndType(reasonCode, reasonType);
    }

    public List<CommonDropdownListDto> getPopupMode(String userLocationId, String module, String level)
            throws Exception {

        List<CommonDropdownListDto> responseDD = null;

        // 1 : search
        // DCC_SECTION_NAME : EXEMPT_LOCATION
        // DCC_KEYWORD : EXEMPT_BACKOFFICE_LOCATION
        // join location with ,
        List<CommonDropdownListDto> locationDD = dccGlobalParameterRepo
                .getInfoByKeyWordAndSectionName("EXEMPT_BACKOFFICE_LOCATION", "EXEMPT_LOCATION");

        List<String> locations = locationDD.stream().map(CommonDropdownListDto::getVal).collect(Collectors.toList());

        // String locations[] = (String[])
        // locationDD.stream().map(CommonDropdownListDto::getVal).;

        // String locationLists = String.join(",",locations);

        // 2 if userLocation not in result from step 1
        // DCC_SECTION_NAME : EXEMPT_LOCATION_MODE
        // DCC_KEYWORD : EXEMPT_" & mStrModule_code & "_LOCATION_MOD
        // join touchpoint with ,

        // else
        // touchpoint = ''
        String touchPointList = "";
        if (!locations.contains(userLocationId)) {
            String sectionName = "EXEMPT_LOCATION_MODE";
            String keyword = "EXEMPT_" + module + "_LOCATION_MODE";
            List<CommonDropdownListDto> touchPointDD = dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(keyword,
                    sectionName);

            // String touchPoints[] = (String[]) touchPointDD.stream().map(loc->
            // loc.getVal()).toArray();
            List<String> touchPoints = touchPointDD.stream().map(CommonDropdownListDto::getVal)
                    .collect(Collectors.toList());

            touchPointList = String.join(",", touchPoints);

        }

        if ("".equals(touchPointList)) {
            // If mStrTouchPoint_Mode_list = "" Then
            // ffPopMode = pop.GetFix_Mode(pFetchStyle, True, strLevel, strKey, pCondition,
            // mStrTouchPoint_Mode_list, "W", "IHL")
            String sectionName = "EXEMPT_LEVEL_" + module;
            String keywordValue = "IHL";
            responseDD = dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameIgnoreKeyword(level, sectionName,
                    keywordValue);
        } else {
            // Else
            // ffPopMode = pop.GetALL_Mode(pFetchStyle, True, strLevel, strKey, pCondition,
            // mStrTouchPoint_Mode_list)
            String sectionName = "EXEMPT_LEVEL_" + module;
            responseDD = dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameIncludeKeyword(level, sectionName,
                    touchPointList);
            // End If
        }

        return responseDD;
    }

    public List<DccExemptCateMaster> searchExemptCateMaster() throws ExemptException {
        return commonDao.searchExemptCateMaster();
    }

    public List<DccExemptCateDetail> searchExemptCateDetail(String cateCode) throws ExemptException {
        return commonDao.searchExemptCateDetail(cateCode);

    }

    public GenReportSeqDto getReportSeq(String reportId) {
        GenReportSeqDto response;
        try {
            response = dccReportMasterRepo.genReportSeq(reportId);
        } catch (Exception e) {
            // log.error("Exception getReportSeq : {}", e.getMessage(), e);
            throw e;
        }
        return response;
    }

    public void updateReportSeq(String reportId, String reportSeq, String updateBy) {
        try {
            dccReportMasterRepo.updateReportSeq(reportId, reportSeq, updateBy);
        } catch (Exception e) {
            // log.error("Exception getGenReportSeq : {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<CommonDropdownListDto> getReportStatus() throws Exception {
        return dccGlobalParameterRepo.getInfoBySectionName(ConfigSectionNameEnums.REPORT_STATUS.toString());
    }

    public List<CommonDropdownListDto> getCompanyCode(String sectionName) throws Exception {
        if (StringUtils.isBlank(sectionName)) {
            sectionName = ConfigSectionNameEnums.CRITERIA.toString();
        }

        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.COMPANY_CODE.toString(),
                sectionName);

    }

    public List<CommonDropdownListDto> getExemptStatus(String sectionName) throws Exception {
        if (StringUtils.isBlank(sectionName)) {
            sectionName = ConfigSectionNameEnums.CRITERIA.toString();
        }

        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_STATUS.toString(),
                sectionName);
    }

    public List<CommonDropdownListDto> getExemptFormat(String sectionName) throws Exception {
        if (StringUtils.isBlank(sectionName)) {
            sectionName = ConfigSectionNameEnums.CRITERIA.toString();
        }

        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_FORMAT.toString(),
                sectionName);
    }

    public List<CommonDropdownListDto> getGroupByForSummary(String sectionName) throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_GROUP_REPORT.toString(),
                sectionName);
    }

    public List<CommonDropdownListDto> getExemptModeRep() throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(
                ConfigSectionNameEnums.EXEMPT_DMS_MODE_REP.toString(),
                ConfigSectionNameEnums.EXEMPT_MODE_REP.toString());
    }

    public List<CommonDropdownListDto> getExemptMode() throws Exception {
        return dccGlobalParameterRepo.getInfoBySectionNameCaseMulti(ConfigSectionNameEnums.EXEMPT_MODE.toString());
    }

    public List<CommonDropdownListDto> getExemptLevel(String sectionName) throws Exception {
        if (StringUtils.isBlank(sectionName)) {
            sectionName = ConfigSectionNameEnums.CRITERIA.toString();
        }

        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_LEVEL.toString(),
                sectionName);
    }

    public List<CommonDropdownListDto> getExemptActionCaseDropdown() throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_ACTION.toString(),
                ConfigSectionNameEnums.CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getMobileStatusCaseDropdown(String sectionName) throws Exception {
        if (StringUtils.isBlank(sectionName)) {
            sectionName = ConfigSectionNameEnums.CRITERIA.toString();
        }

        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.MOBILE_STATUS.toString(),
                sectionName);
    }

    public List<CommonDropdownListDto> getExemptMobileStatus() throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                ConfigSectionNameEnums.EXEMPT_MOBILE_STATUS.toString(),
                ConfigSectionNameEnums.EXEMPT_MODE_REP.toString());
    }

    public List<CommonCheckListDto> getAccountCategory() throws Exception {
        return sffLovMasterRepository.getLovByLovType(ConfigLovEnums.ACCOUNT_CATEGORY.toString());
    }

    public List<CommonCheckListDto> getAccountSubCategory(String categoryName) throws Exception {
        return sffLovMasterRepository.getSubLovByLovTypeandSublovTypeandCategory(
                ConfigLovEnums.ACCOUNT_CATEGORY.toString(),
                ConfigLovEnums.ACCOUNT_SUBCATEGORY.toString(), categoryName);
    }

    public Integer getASCLocationList(String username) throws Exception {
        return ascLocationRelationRepository.getAscLocationList(username).get(0);
    }

    public List<CpLocationDto> getLocationList(Integer locationId) throws Exception {
        if (locationId != null) {

            return cpLocationMasterRepository.getLocationListByLocationId(locationId);

        } else {
            return cpLocationMasterRepository.getLocationList();
        }
    }

    public List<CommonDropdownListDto> getInfoByKeyWordAndSectionNameCaseDropdown(String keyword, String sectionName) throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameCaseDropdown(
                keyword,
                sectionName);
    }

}
