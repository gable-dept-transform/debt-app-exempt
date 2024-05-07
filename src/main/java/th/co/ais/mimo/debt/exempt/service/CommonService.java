package th.co.ais.mimo.debt.exempt.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.dao.CommonDao;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.exempt.dto.GenReportSeqDto;
import th.co.ais.mimo.debt.exempt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.repo.DccGlobalParameterRepo;
import th.co.ais.mimo.debt.exempt.repo.DccReasonRepo;
import th.co.ais.mimo.debt.exempt.repo.DccReportMasterRepo;

import java.util.List;
import java.util.stream.Collectors;

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

}
