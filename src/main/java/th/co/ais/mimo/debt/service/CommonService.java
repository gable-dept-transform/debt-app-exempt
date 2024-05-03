package th.co.ais.mimo.debt.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.repo.DccGlobalParameterRepo;
import th.co.ais.mimo.debt.repo.DccReasonRepo;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommonService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DccGlobalParameterRepo dccGlobalParameterRepo;

    @Autowired
    private DccReasonRepo dccReasonRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<CommonDropdownListDto> getMobileStatus()  throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.MOBILE_STATUS.toString(),ConfigSectionNameEnums.EXEMPT_CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getExemptAction()  throws Exception{
        return  dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.EXEMPT_ACTION.toString(),ConfigSectionNameEnums.CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getGlobalParameter(String keyword,String sectionName)  throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(keyword,sectionName);
    }

    public List<CommonDropdownListDto> getDccReason(String reasonType)  throws Exception {
        return dccReasonRepo.findByReasonType(reasonType);
    }

    public List<CommonDropdownListDto> getDccReason(String reasonCode,String reasonType)  throws Exception {
        return dccReasonRepo.findReasonByCodeAndType(reasonCode,reasonType);
    }

    public List<CommonDropdownListDto> getPopupMode(String userLocationId,String module,String level)throws Exception{

        List<CommonDropdownListDto> responseDD = null;

        //1 : search
        //      DCC_SECTION_NAME : EXEMPT_LOCATION
        //      DCC_KEYWORD : EXEMPT_BACKOFFICE_LOCATION
        // join location with ,
        List<CommonDropdownListDto> locationDD =  dccGlobalParameterRepo.getInfoByKeyWordAndSectionName("EXEMPT_BACKOFFICE_LOCATION","EXEMPT_LOCATION");

        List<String> locations = locationDD.stream().map(CommonDropdownListDto::getVal).collect(Collectors.toList());

        //String locations[] = (String[]) locationDD.stream().map(CommonDropdownListDto::getVal).;

        //String locationLists = String.join(",",locations);

        //2 if userLocation not in result from step 1
        // DCC_SECTION_NAME : EXEMPT_LOCATION_MODE
        // DCC_KEYWORD : EXEMPT_" & mStrModule_code & "_LOCATION_MOD
        // join touchpoint with ,

        // else
        // touchpoint = ''
        String touchPointList = "";
        if(!locations.contains(userLocationId)){
            String sectionName = "EXEMPT_LOCATION_MODE";
            String keyword = "EXEMPT_"+module+"_LOCATION_MODE";
            List<CommonDropdownListDto> touchPointDD =  dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(keyword,sectionName);

            //String touchPoints[] = (String[]) touchPointDD.stream().map(loc-> loc.getVal()).toArray();
            List<String> touchPoints = touchPointDD.stream().map(CommonDropdownListDto::getVal).collect(Collectors.toList());


            touchPointList = String.join(",",touchPoints);

        }

        if("".equals(touchPointList)) {
            //If mStrTouchPoint_Mode_list = "" Then
            //            ffPopMode = pop.GetFix_Mode(pFetchStyle, True, strLevel, strKey, pCondition, mStrTouchPoint_Mode_list, "W", "IHL")
            String sectionName = "EXEMPT_LEVEL_"+module;
            String keywordValue = "IHL";
            responseDD = dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameIgnoreKeyword(level, sectionName, keywordValue);
        }else {
            //    Else
            //            ffPopMode = pop.GetALL_Mode(pFetchStyle, True, strLevel, strKey, pCondition, mStrTouchPoint_Mode_list)
            String sectionName = "EXEMPT_LEVEL_"+module;
            responseDD = dccGlobalParameterRepo.getInfoByKeyWordAndSectionNameIncludeKeyword(level, sectionName, touchPointList);
            //    End If
        }

        return responseDD;
    }

    public List<DccExemptCateMaster> searchExemptCateMaster() throws ExemptException{
        try{
            String sql = "select cate_code, cate_description,exempt_reason,active_flag, \n" +
                    "  last_update_by, to_char(last_update_dtm,'YYYY/MM/DD  HH24:MI:SS') last_update_dtm\n" +
                    " from dcc_exempt_cate_master\n" +
                    " where active_flag = 'Y'\n" +
                    " order by cate_code";
            Query query = entityManager.createNativeQuery(sql, "searchExemptCateMasterDtoMapping");
            //query.setParameter("mobileNum",request.getMobileNum());
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("exception occur when searchExemptCateMaster ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

    public List<DccExemptCateDetail> searchExemptCateDetail(String cateCode)throws ExemptException{
        try{
            String sql = "select mode_id,module_code,exempt_duration, to_char(expire_dat,'YYYY/MM/DD') expire_date, \n" +
                    "last_update_by, to_char(last_update_dtm,'YYYY/MM/DD  HH24:Mi:SS') last_update_dtm, exempt_level  \n" +
                    "from dcc_exempt_cate_detail where cate_code = :cate_code\n" +
                    "order by mode_id";
            Query query = entityManager.createNativeQuery(sql, "searchExemptCateDetailDtoMapping");
            query.setParameter("cate_code",cateCode);
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("exception occur when searchExemptCateMaster ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }
}
