package th.co.ais.mimo.debt.exempt.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM002SetTreatmentExemptDao;
import th.co.ais.mimo.debt.exempt.dto.*;
import th.co.ais.mimo.debt.exempt.entity.*;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.*;
import th.co.ais.mimo.debt.exempt.repo.DccExemptBosRepo;
import th.co.ais.mimo.debt.exempt.repo.DccExemptHistoryRepo;
import th.co.ais.mimo.debt.exempt.repo.DccExemptRepo;
import th.co.ais.mimo.debt.exempt.repo.DccGlobalParameterRepo;

import th.co.ais.mimo.debt.exempt.service.DMSEM002SetTreatmentExemptService;
import th.co.ais.mimo.debt.exempt.utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DMSEM002SetTreatmentExemptServiceImpl implements DMSEM002SetTreatmentExemptService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String EXEMPT_BY_MODE = "MODE";
    private static final String EXEMPT_BY_CATE = "CATE";

    private static final String EXEMPT_ACTION_TYPE_ADD = "ADD";
    private static final String EXEMPT_ACTION_TYPE_UPDATE = "UPDATE";
    private static final String EXEMPT_ACTION_TYPE_DELETE = "DELETE";

    private static final String EXEMPT_SENT_INTERFACE_FLAG_N = "N";

    private static final String EXEMPT_LEVEL_CA = "CA";
    private static final String EXEMPT_LEVEL_BA = "BA";
    private static final String EXEMPT_LEVEL_MO = "MO";

    @Autowired
    private DMSEM002SetTreatmentExemptDao dmsem002SetTreatmentExemptDao;

    @Autowired
    private DccExemptHistoryRepo dccExemptHistoryRepo;

    @Autowired
    private DccExemptRepo dccExemptRepo;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DccExemptBosRepo dccExemptBosRepo;

    @Override
    public List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException {

        return dmsem002SetTreatmentExemptDao.searchData(request);
    }

    @Override
    public List<ExemptDetailDto> searchExemptDetail(String searchType, String value) throws ExemptException {
        return dmsem002SetTreatmentExemptDao.searchExemptDetail(searchType,value);
    }

    @Override
    public AddExemptResponse insertExempt(AddExemptRequest addExemptRequest, Integer location, String addBy)throws ExemptException{



        //1. D_GetDCExemptH
        //2. D_InsDCExempt
        //3. D_InsDCExemptH
        String sentInterfaceFlag = EXEMPT_SENT_INTERFACE_FLAG_N;
        if(EXEMPT_BY_MODE.equals(addExemptRequest.getExemptBy())) {

            List<AddExemptCustAccDto> listCustAccDto = new ArrayList<>();
            if (EXEMPT_LEVEL_CA.equals(addExemptRequest.getExemptLevel())) {
                //if exempt level = CA,BA then insert only 1 record
                //find unique ca
                Map<String, AddExemptCustAccDto> caMap = new HashMap<>();
                addExemptRequest.getCustAccNo().forEach(custAccDto -> {
                    caMap.putIfAbsent(custAccDto.getCustAccNum(), custAccDto);
                });
                listCustAccDto = caMap.values().stream().toList();
            } else if (EXEMPT_LEVEL_BA.equals(addExemptRequest.getExemptLevel())) {
                listCustAccDto = addExemptRequest.getCustAccNo();
                //if exempt level = CA,BA then insert only 1 record
                //find unique ca,ba

                Map<String, AddExemptCustAccDto> caMap = new HashMap<>();
                addExemptRequest.getCustAccNo().forEach(custAccDto -> {
                    String key = custAccDto.getCustAccNum() + ":" + custAccDto.getBillingAccNum();
                    caMap.putIfAbsent(key, custAccDto);
                });
                listCustAccDto = caMap.values().stream().toList();
            }else{
                listCustAccDto = addExemptRequest.getCustAccNo();
            }

            for (AddExemptCustAccDto custAccDto:listCustAccDto){

                var mode = addExemptRequest.getExemptMode().split(",");

                for (int i = 0; i < mode.length; i++) {
                    //validate
                    /*
                    If CStr(marrNModule(IngSave)) = "CCS" And CStr(marrNMode_id(IngSave)) = "DC" Then
                                If ffCheckBilling_System(CStr(marrGBill_acc_num(IngSave))) = "BOS" Then
                                    ลูกค้าเลขหมายระบบ BOS ไม่สามารถทำยกเว้น Suspend Credit Limit ได้
                     */
                    //validate
                    if("CCS".equalsIgnoreCase(addExemptRequest.getModule())
                            && "DC".equalsIgnoreCase(addExemptRequest.getExemptMode())){
                        String biilingSystem = commonService.getBillingSystem(custAccDto.getBillingAccNum());
                        if("BOS".equalsIgnoreCase(biilingSystem)){
                            throw new ExemptException(AppConstant.ADD_EXEMPT_ERROR_BOS_SUSPEND_CREDIT_LIMIT_NOT_ALLOW,"ลูกค้าเลขหมายระบบ BOS ไม่สามารถทำยกเว้น Suspend Credit Limit ได้");
                        }
                    }

                    //1. D_GetDCExemptH
                    Long noOfExempt = dccExemptHistoryRepo.countNumberOfExempt(custAccDto.getCustAccNum(), addExemptRequest.getModule(), mode[i]);
                    noOfExempt++;
                    Long exemptSeq = noOfExempt;

                    addExempt(addExemptRequest.getExemptLevel(), custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(),
                            custAccDto.getServiceNum(),
                            addExemptRequest.getModule(),
                            mode[i],
                            noOfExempt,
                            addExemptRequest.getEndDate(),
                            location,
                            addExemptRequest.getReason(),
                            addExemptRequest.getCateCode(),
                            addExemptRequest.getEffectiveDate(),
                            addBy,
                            sentInterfaceFlag,
                            null);

                    addExemptHistory(exemptSeq,
                            noOfExempt,
                            mode[i], custAccDto.getServiceNum(), custAccDto.getBillingAccNum(), custAccDto.getCustAccNum(), location, addExemptRequest.getReason(),
                            addExemptRequest.getCateCode(),
                            addExemptRequest.getEffectiveDate(),
                            addExemptRequest.getExemptLevel(),
                            addExemptRequest.getEndDate(),
                            addBy,
                            addExemptRequest.getModule(),
                            sentInterfaceFlag,
                            null,
                            EXEMPT_ACTION_TYPE_ADD
                            );

//                    If CStr(marrNModule(IngSave)) = "CCS" And CStr(marrNMode_id(IngSave)) = "DC" Then
//                    Call ffSaveDataBOS(CStr(marrGCust_acc_num(IngSave)), CStr(marrGBill_acc_num(IngSave)), CStr(marrGMobile_num(IngSave)), Format(CStr(marrNEffective(IngSave)), "YYYY/MM/DD"), Format(CStr(marrNEnd(IngSave)), "YYYY/MM/DD"), "0")
//                    End If

                    //cancel BOS by user requirement
//                    if("CCS".equalsIgnoreCase(addExemptRequest.getModule()) && "DC".equalsIgnoreCase(mode[i])){
//                        String checkExemptDCBOS = ffGetCheckExemptDCBOS();
//                        ffSaveDataBOS(custAccDto.getBillingAccNum(), custAccDto.getServiceNum(), checkExemptDCBOS, addExemptRequest.getModule(), mode[i],"0",
//                                addExemptRequest.getExemptLevel()
//                                ,DateUtils.getDateByFormatEnLocale(addExemptRequest.getEffectiveDate(), DateUtils.DEFAULT_DATE_PATTERN)
//                                ,DateUtils.getDateByFormatEnLocale(addExemptRequest.getEndDate(), DateUtils.DEFAULT_DATE_PATTERN)
//                                ,addBy,DateUtils.getCurrentDate()
//                                );
//
//                    }
                }
            }
        }else if(EXEMPT_BY_CATE.equals(addExemptRequest.getExemptBy())) {
            for(DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()){
            //addExemptRequest.getExemptCateDetails().forEach(exemptCateDetailDto -> {

                List<AddExemptCustAccDto> listCustAccDto = new ArrayList<>();
                if (EXEMPT_LEVEL_CA.equals(exemptCateDetailDto.getExemptLevel())) {
                    //if exempt level = CA,BA then insert only 1 record
                    //find unique ca
                    Map<String, AddExemptCustAccDto> caMap = new HashMap<>();
                    addExemptRequest.getCustAccNo().forEach(custAccDto -> {
                        caMap.putIfAbsent(custAccDto.getCustAccNum(), custAccDto);
                    });
                    listCustAccDto = caMap.values().stream().toList();
                } else if (EXEMPT_LEVEL_BA.equals(exemptCateDetailDto.getExemptLevel())) {
                    listCustAccDto = addExemptRequest.getCustAccNo();
                    //if exempt level = CA,BA then insert only 1 record
                    //find unique ca,ba

                    Map<String, AddExemptCustAccDto> caMap = new HashMap<>();
                    addExemptRequest.getCustAccNo().forEach(custAccDto -> {
                        String key = custAccDto.getCustAccNum() + ":" + custAccDto.getBillingAccNum();
                        caMap.putIfAbsent(key, custAccDto);
                    });
                    listCustAccDto = caMap.values().stream().toList();
                }else {
                    listCustAccDto = addExemptRequest.getCustAccNo();
                }


                for(AddExemptCustAccDto custAccDto:listCustAccDto){
//                listCustAccDto.forEach(custAccDto -> {

                    //1. D_GetDCExemptH
                    Long noOfExempt = dccExemptHistoryRepo.countNumberOfExempt(custAccDto.getCustAccNum()
                            , exemptCateDetailDto.getModuleCode()
                            , exemptCateDetailDto.getModeId());
                    noOfExempt++;
                    Long exemptSeq = noOfExempt;

                    addExempt(exemptCateDetailDto.getExemptLevel(),
                            custAccDto.getCustAccNum(),
                            custAccDto.getBillingAccNum(),
                            custAccDto.getServiceNum(),
                            exemptCateDetailDto.getModuleCode(),
                            exemptCateDetailDto.getModeId(),
                            noOfExempt,
                            addExemptRequest.getEndDate(),
                            location,
                            addExemptRequest.getReason(),
                            addExemptRequest.getCateCode(),
                            addExemptRequest.getEffectiveDate(),
                            addBy,
                            sentInterfaceFlag,
                            exemptCateDetailDto.getExpireDate());

                    addExemptHistory(exemptSeq,
                            noOfExempt,
                            exemptCateDetailDto.getModeId(),
                            custAccDto.getServiceNum(),
                            custAccDto.getBillingAccNum(),
                            custAccDto.getCustAccNum(),
                            location,
                            addExemptRequest.getReason(),
                            addExemptRequest.getCateCode(),
                            addExemptRequest.getEffectiveDate(),
                            exemptCateDetailDto.getExemptLevel(),
                            addExemptRequest.getEndDate(),
                            addBy,
                            exemptCateDetailDto.getModuleCode(),
                            sentInterfaceFlag,
                            exemptCateDetailDto.getExpireDate(),
                            EXEMPT_ACTION_TYPE_ADD
                    );

                    // cancel BOS by user requirement

//                    if("CCS".equalsIgnoreCase(addExemptRequest.getModule()) && "DC".equalsIgnoreCase(exemptCateDetailDto.getModeId())){
//                        String checkExemptDCBOS = ffGetCheckExemptDCBOS();
//                        ffSaveDataBOS(custAccDto.getBillingAccNum(), custAccDto.getServiceNum(), checkExemptDCBOS, addExemptRequest.getModule(), exemptCateDetailDto.getModeId(),"0",
//                                addExemptRequest.getExemptLevel()
//                                ,DateUtils.getDateByFormatEnLocale(addExemptRequest.getEffectiveDate(), DateUtils.DEFAULT_DATE_PATTERN)
//                                ,DateUtils.getDateByFormatEnLocale(addExemptRequest.getEndDate(), DateUtils.DEFAULT_DATE_PATTERN)
//                                ,addBy,DateUtils.getCurrentDate()
//                        );
//
//                    }

                }
            }
        }

        return AddExemptResponse.builder().responseCode(AppConstant.SUCCESS).build();
    }

    @Override
    public AddExemptResponse validateAddExempt(AddExemptRequest addExemptRequest) throws ExemptException {
        /*
          Validation
          Alert Confirm :
          1. if lower exempt level found alert lower level found
          2. exempt duration more than 3 day
          3 ภายใน 3 เดือน มีการทำ Exempt Mode " & marrNMode_id(IngSave) & " ไปแล้วจำนวน " & intCountExempt & " ครั้ง" & vbCrLf & "กรุณายืนยันการทำ Exempt"
         */
        AddExemptResponse response= AddExemptResponse.builder().responseCode(AppConstant.SUCCESS).build();
        // 1. if lower exempt level found alert lower level found
        if(EXEMPT_LEVEL_CA.equalsIgnoreCase(addExemptRequest.getExemptLevel())){
            //find exempt level BA and MO
            for (AddExemptCustAccDto custAccDto:addExemptRequest.getCustAccNo()){

                String modeIds = addExemptRequest.getExemptMode();
                if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())){

                    modeIds = addExemptRequest.getExemptCateDetails()
                            .stream()
                            .map(DccExemptCateDetailDto::getModeId)
                            .collect(Collectors.joining(","));

                }

                Long count = dccExemptRepo.countExemptByLevel(custAccDto.getCustAccNum(), modeIds, Arrays.stream(new String[]{EXEMPT_LEVEL_BA, EXEMPT_LEVEL_MO}).toList());
                if(count > 0){
                    response.setCustAccNum(custAccDto.getCustAccNum());
                    response.setExemptLevel(EXEMPT_LEVEL_CA);
                    response.setCountExempt(count);
                    response.setMode(addExemptRequest.getExemptMode());
                    response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_LOW_LEVEL_FOUND);
                    break;
                }

                if(EXEMPT_BY_MODE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {

                    // check 3.
                    Long countHis = dccExemptHistoryRepo.countExemptHistoryByCA(custAccDto.getCustAccNum(), EXEMPT_LEVEL_CA, addExemptRequest.getExemptMode());
                    if (countHis > 0) {
                        response.setCustAccNum(custAccDto.getCustAccNum());
                        response.setExemptLevel(EXEMPT_LEVEL_CA);
                        response.setCountExempt(countHis);
                        response.setMode(addExemptRequest.getExemptMode());
                        response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                        break;
                    }
                }else if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())){

                    for (DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()){
                        Long countHis = dccExemptHistoryRepo.countExemptHistoryByCA(custAccDto.getCustAccNum(), EXEMPT_LEVEL_CA, exemptCateDetailDto.getModeId());
                        if (countHis > 0) {
                            response.setCustAccNum(custAccDto.getCustAccNum());
                            response.setExemptLevel(EXEMPT_LEVEL_CA);
                            response.setCountExempt(countHis);
                            response.setMode(exemptCateDetailDto.getModeId());
                            response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                            break;
                        }
                    }
                }
            }

        }else if(EXEMPT_LEVEL_BA.equalsIgnoreCase(addExemptRequest.getExemptLevel())){
            //find exempt level MO
            for (AddExemptCustAccDto custAccDto:addExemptRequest.getCustAccNo()){
                String modeIds = addExemptRequest.getExemptMode();
                if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())){

                    modeIds = addExemptRequest.getExemptCateDetails()
                            .stream()
                            .map(DccExemptCateDetailDto::getModeId)
                            .collect(Collectors.joining(","));

                }
                Long count = dccExemptRepo.countExemptByLevel(custAccDto.getCustAccNum(), modeIds, Arrays.stream(new String[]{EXEMPT_LEVEL_MO}).toList());
                if(count > 0){
                    response.setCustAccNum(custAccDto.getCustAccNum());
                    response.setExemptLevel(EXEMPT_LEVEL_CA);
                    response.setCountExempt(count);
                    response.setMode(addExemptRequest.getExemptMode());
                    response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_LOW_LEVEL_FOUND);
                    break;
                }

                if(EXEMPT_BY_MODE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    // check 3.
                    Long countHis = dccExemptHistoryRepo.countExemptHistoryByBA(custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(), EXEMPT_LEVEL_BA, addExemptRequest.getExemptMode());
                    if (countHis > 0) {
                        response.setCustAccNum(custAccDto.getCustAccNum());
                        response.setExemptLevel(EXEMPT_LEVEL_CA);
                        response.setCountExempt(count);
                        response.setMode(addExemptRequest.getExemptMode());
                        response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                        break;
                    }
                }else if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())){
                    for (DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()) {
                        Long countHis = dccExemptHistoryRepo.countExemptHistoryByBA(custAccDto.getCustAccNum(),
                                custAccDto.getBillingAccNum(),
                                EXEMPT_LEVEL_BA,
                                exemptCateDetailDto.getModeId());
                        if (countHis > 0) {
                            response.setCustAccNum(custAccDto.getCustAccNum());
                            response.setExemptLevel(EXEMPT_LEVEL_CA);
                            response.setCountExempt(count);
                            response.setMode(exemptCateDetailDto.getModeId());
                            response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                            break;
                        }
                    }
                }
            }
        }else if(EXEMPT_LEVEL_MO.equalsIgnoreCase(addExemptRequest.getExemptLevel())){
            // check 3.
            for (AddExemptCustAccDto custAccDto : addExemptRequest.getCustAccNo()) {

                if(EXEMPT_BY_MODE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    Long countHis = dccExemptHistoryRepo.countExemptHistoryByMobile(custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(), custAccDto.getServiceNum(), EXEMPT_LEVEL_MO, addExemptRequest.getExemptMode());
                    if (countHis > 0) {
                        response.setCustAccNum(custAccDto.getCustAccNum());
                        response.setExemptLevel(EXEMPT_LEVEL_CA);
                        response.setCountExempt(countHis);
                        response.setMode(addExemptRequest.getExemptMode());
                        response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                        break;
                    }
                }else if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    for (DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()) {
                        // check 3.
                        Long countHis = dccExemptHistoryRepo.countExemptHistoryByMobile(custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(), custAccDto.getServiceNum(), EXEMPT_LEVEL_MO, exemptCateDetailDto.getModeId());
                        if (countHis > 0) {
                            response.setCustAccNum(custAccDto.getCustAccNum());
                            response.setExemptLevel(EXEMPT_LEVEL_CA);
                            response.setCountExempt(countHis);
                            response.setMode(exemptCateDetailDto.getModeId());
                            response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                            break;
                        }
                    }
                }
            }
        }
        // 2. exempt duration more than 3 day
        // just validate duration from front end
        return response;
    }

    @Override
    public DeleteExemptResponse deleteExempt(DeleteExemptRequest deleteExemptRequest,Integer location,String deleteBy)throws ExemptException{
        DeleteExemptResponse response = DeleteExemptResponse.builder().responseCode(AppConstant.SUCCESS).build();

        // Update send_bos_flag before delete dcc_exempt case 'D'
        //if ((DCC_MODULE_CODE == 'CCS') && (DCC_MODE_ID == 'DC'))  then update exempt

        List<ExemptDetailDto> successList = new ArrayList<>();
        List<ExemptDetailDto> errorList = new ArrayList<>();

        for(ExemptDetailDto exemptDto: deleteExemptRequest.getDeleteExemptDetail()) {

            //max_exempt_seq = D_GetDCExemptH ++
            Long exemptSeq = dccExemptHistoryRepo.countExemptHisForUpdate(exemptDto.getCustAccNum(), exemptDto.getModuleCode(), exemptDto.getModeId(), exemptDto.getNoOfExempt());
            exemptSeq++;

            //DCC_SQL_STRING = 'D'

            // D_InsDCExemptH
            addExemptHistory(exemptSeq, exemptDto.getNoOfExempt(), exemptDto.getModeId(),
                    exemptDto.getMobileNum(), exemptDto.getBillingAccNum(), exemptDto.getCustAccNum(),
                    location, deleteExemptRequest.getReason(), exemptDto.getCateCode(), exemptDto.getEffectiveDate(), deleteExemptRequest.getExemptLevel(),
                    exemptDto.getEndDate(), deleteBy, exemptDto.getModuleCode(), "N", null, EXEMPT_ACTION_TYPE_DELETE);


            //D_DelDCExempt
            int effRecord = dccExemptRepo.deleteExemptByCA(exemptDto.getCustAccNum(),
                    exemptDto.getBillingAccNum(),
                    exemptDto.getModuleCode(),
                    exemptDto.getModeId(),
                    deleteExemptRequest.getExemptLevel(),
                    exemptDto.getNoOfExempt());

            if (effRecord <= 0) {
                //throws exception for rollback
                //throw new ExemptException(AppConstant.DELETE_EXEMPT_ERROR_RECORD_NOT_FOUND, "Record Not Found");
                errorList.add(exemptDto);
            }else{
                successList.add(exemptDto);
            }


//        If arrModeIDDelete(lngModeId) = "BL" Or arrModeIDDelete(lngModeId) = "DL" Or arrModeIDDelete(lngModeId) = "DC" Then
//        Call ffGetNegoExemSff(marrCust_acc_num(IngSave), marrBill_acc_num(IngSave), marrMobile_num(IngSave), Format(CStr(marrEffective(IngSave)), "YYYY/MM/DD"), Format(marrEnd(IngSave), "YYYY/MM/DD"), arrModeIDDelete(lngModeId), marrLevel(IngSave))
//             PLUGIN.DCCFNG_EXEMPT_SFF_ASYNC('D_GetNGExemSFF',:ca,:ba,:mobile,:mode,to_date(:startdate,'YYYY/MM/DD'),to_date(:enddate,'YYYY/MM/DD'),:exempt_level);

            if ("BL".equalsIgnoreCase(exemptDto.getModeId())
                    || "DL".equalsIgnoreCase(exemptDto.getModeId())
                    || "DC".equalsIgnoreCase(exemptDto.getModeId())) {
                ffGetNegoExemSff(exemptDto.getCustAccNum()
                        , exemptDto.getBillingAccNum()
                        , exemptDto.getMobileNum()
                        , exemptDto.getModeId()
                        , exemptDto.getEffectiveDate()
                        , exemptDto.getEndDate(),
                        deleteExemptRequest.getExemptLevel());
            }

//        If marrModule(IngSave) = "CCS" And arrModeIDDelete(lngModeId) = "DC" Then
//        Call ffSaveDataBOS(CStr(marrCust_acc_num(IngSave)), CStr(marrBill_acc_num(IngSave)), CStr(marrMobile_num(IngSave)), Format(CStr(marrEffective(IngSave)), "YYYY/MM/DD"), gvClsUtil.FormatDTP(dtpEnd_dateL.value, "YYYY/MM/DD"), "1")
//        End If

            // disable bos by user requirement
//            String checkExemptDCBOS = ffGetCheckExemptDCBOS();
//
//            if ("CCS".equalsIgnoreCase(exemptDto.getModuleCode())
//                    && "DC".equalsIgnoreCase(exemptDto.getModeId())) {
//                ffSaveDataBOS(exemptDto.getBillingAccNum(),
//                        exemptDto.getMobileNum(), checkExemptDCBOS,
//                        exemptDto.getModuleCode(),
//                        exemptDto.getModeId(),
//                        "1", deleteExemptRequest.getExemptLevel(),
//                        DateUtils.getDateByFormatEnLocale(exemptDto.getEffectiveDate(), DateUtils.DEFAULT_DATE_PATTERN),
//                        DateUtils.getDateByFormatEnLocale(exemptDto.getEndDate(), DateUtils.DEFAULT_DATE_PATTERN),
//                        deleteBy,
//                        DateUtils.getCurrentDate()
//                );
//            }
        }

        response.setErrorList(errorList);
        response.setSuccessList(successList);
        return response;
    }

    private DccGlobalParameterRepo dccGlobalParameterRepo;

    private String ffGetCheckExemptDCBOS() {
//        D_GetDCGbPara
//        "DCC_SECTION_NAME",  "CONFIG")
//     "DCC_KEYWORD", "CHECK_EXEMPT_DC_BOS"
//     "DCC_SQL_STRING", "K"
        try {
            List<CommonDropdownListDto> listConfig = dccGlobalParameterRepo.getInfoByKeyWordAndSectionName("CHECK_EXEMPT_DC_BOS","CONFIG");
            if(listConfig !=null && listConfig.isEmpty()){
                return listConfig.get(0).getVal();
            }
        } catch (Exception e) {
            log.error("error when get ffGetCheckExemptDCBOS",e);
        }
        return null;
    }

    private void ffGetNegoExemSff(String custAccNo, String billingAccNo, String mobileNo, String mode, String startdate, String enddate, String exemptLevel) {
//                   PLUGIN.DCCFNG_EXEMPT_SFF_ASYNC('D_GetNGExemSFF',:ca,:ba,:mobile,:mode,to_date(:startdate,'YYYY/MM/DD'),to_date(:enddate,'YYYY/MM/DD'),:exempt_level);
        dccExemptRepo.ffGetNegoExemSff(custAccNo,billingAccNo,mobileNo,mode,startdate,enddate,exemptLevel);
    }

    @Override
    public UpdateExemptResponse updateExempt(UpdateExemptRequest updateExemptRequest,Integer location,String updateBy)throws ExemptException{
        UpdateExemptResponse response = UpdateExemptResponse.builder().responseCode(AppConstant.SUCCESS).build();
        List<ExemptDetailDto> successList = new ArrayList<>();
        List<ExemptDetailDto> errorList = new ArrayList<>();
        for (ExemptDetailDto exemptDt: updateExemptRequest.getUpdateExemptDetail()) {
            // insert exempt history
            Long exemptSeq = dccExemptHistoryRepo.countExemptHisForUpdate(exemptDt.getCustAccNum(), exemptDt.getModuleCode(), exemptDt.getModeId(), exemptDt.getNoOfExempt());
            exemptSeq++;

            addExemptHistory(exemptSeq, exemptDt.getNoOfExempt(), exemptDt.getModeId(),
                    exemptDt.getMobileNum(), exemptDt.getBillingAccNum(), exemptDt.getCustAccNum(),
                    location, updateExemptRequest.getReason(), null, null, updateExemptRequest.getExemptLevel(),
                    updateExemptRequest.getEndDate(), updateBy, exemptDt.getModuleCode(), "N", null, EXEMPT_ACTION_TYPE_UPDATE);


            // update exempt
        /*
        "update dcc_exempt set  end_dat = to_date(:v1,'YYYY/MM/DD'), "
					" update_reason   = :v2, update_location = :v3,"
					" last_update_by  = :v4, last_update_dtm = sysdate ,"
					" sent_interface_flag = :v5 ,"
					" SENT_BOS_FLAG = (select g.KEYWORD_VALUE from DCC_GLOBAL_PARAMETER g where g.SECTION_NAME = 'CONFIG_BOS_EXEMPT' and g.KEYWORD = 'SEND_BOS_FLAG') "
					" where cust_acc_num = :v6 and module_code = :v7 and  mode_id = :v8 "
					"  and exempt_level  =  :v9 and no_of_exempt = :v10
         */

            int effRecord = dccExemptRepo.updateExemptByCA(updateExemptRequest.getReason(),DateUtils.getDateByFormatEnLocale(updateExemptRequest.getEndDate(), DateUtils.DEFAULT_DATE_PATTERN),
                    location, updateBy, EXEMPT_SENT_INTERFACE_FLAG_N, exemptDt.getCustAccNum(), exemptDt.getModuleCode(),
                    exemptDt.getModeId(),
                    updateExemptRequest.getExemptLevel(),
                    exemptDt.getNoOfExempt());

            if (effRecord <= 0) {
                //throw new ExemptException(AppConstant.UPDATE_EXEMPT_ERROR_RECORD_NOT_FOUND, "Record Not Found");
                errorList.add(exemptDt);
            }else{

                successList.add(exemptDt);
            }
        }

        response.setErrorList(errorList);
        response.setSuccessList(successList);

        return response;
    }

    private void addExempt(String exemptLevel,
                           String cusAccNo,
                           String billAccNo,
                           String serviceNo,
                           String module,
                           String mode,
                           Long noOfExempt,
                           String endDate,
                           Integer location,
                           String addReason,
                           String cateCode,
                           String effectiveDate,
                           String user,
                           String sentInterfaceFlag,
                           String expireDate){
        //2. D_InsDCExempt
        DccExemptModelId dccExemptModelId = DccExemptModelId.builder()
                .exemptLevel(exemptLevel)
                .billingAccNum(billAccNo)
                .modeId(mode)
                .mobileNum(serviceNo)
                .custAccNum(cusAccNo)
                .build();

        DccExemptModel dccExempt = DccExemptModel.
                builder()
                .noOfExempt(noOfExempt)
                .id(dccExemptModelId)
                .endDate(DateUtils.getDateByFormatEnLocale(endDate, DateUtils.DEFAULT_DATE_PATTERN))
                .addLocation(location)//location of user
                .addReason(addReason)
                .cateCode(cateCode)
                .effectiveDate(DateUtils.getDateByFormatEnLocale(effectiveDate, DateUtils.DEFAULT_DATE_PATTERN))
                .expireDate(DateUtils.getDateByFormatEnLocale(expireDate, DateUtils.DEFAULT_DATE_PATTERN))
                .lastUpdateBy(user)
                .lastUpdateDtm(DateUtils.getCurrentDate())
                .moduleCode(module)
                .soNbr(null)
                .sentBosFlag(null)
                .sentIntefaceFlag(sentInterfaceFlag)
                .updateLocation(location)
                .updateReason(null)
                .build();

        dccExemptRepo.saveAndFlush(dccExempt);
    }

    private void addExemptHistory(Long exemptSeq,
                                  Long noOfExempt,
                                  String mode,
                                  String serviceNum,
                                  String billingAccNum,
                                  String custAccNum,
                                  Integer location,
                                  String addReason,
                                  String cateCode,
                                  String effectiveDate,
                                  String exemptLevel,
                                  String endDate,
                                  String user,
                                  String module,
                                  String sentInterfaceFlag,
                                  String expireDate,
                                  String actionType){
        //3. D_InsDCExemptH

        DccExemptHistoryId dccExemptHistoryId = DccExemptHistoryId.builder()
                .exemptSeq(exemptSeq)
                .noOfExempt(noOfExempt)
                .modeId(mode)
                .mobileNum(serviceNum)
                .actionType(actionType)
                .custAccNum(custAccNum)
                .billingAccNum(billingAccNum)
                .build();


        DccExemptHistory dccExemptHistory = DccExemptHistory.builder()
                .addLocation(location)
                .addReason(addReason)
                .cateCode(cateCode)
                .checkFlag(null)
                .effectiveDat(DateUtils.getDateByFormatEnLocale(effectiveDate, DateUtils.DEFAULT_DATE_PATTERN))
                .exemptLevel(exemptLevel)
                .endDat(DateUtils.getDateByFormatEnLocale(endDate, DateUtils.DEFAULT_DATE_PATTERN))
                .expireDat(DateUtils.getDateByFormatEnLocale(expireDate, DateUtils.DEFAULT_DATE_PATTERN))
                .id(dccExemptHistoryId)
                .lastUpdateBy(user)
                .lastUpdateDtm(DateUtils.getCurrentDate())
                .moduleCode(module)
                .sentInterfaceFlag(sentInterfaceFlag)
                .updateLocation(location)
                .build();

        dccExemptHistoryRepo.saveAndFlush(dccExemptHistory);
    }


    private void ffSaveDataBOS(String billingAccNum,String mobileNum,String mStrCheckExemptDCBOS,
                               String moduleCode,String modeId,String inOperType,
                               String exemptLevel,Date effectiveDate,Date endDate,String updateBy,Date updateDate ) throws ExemptException {
        //1. D_GetDCBillSys
        /*
            select b.BILLING_SYSTEM "
					" from BILLING_PROFILE b "
					" where b.OU_NUM = :billing_acc_num
         */
        String biilingSystem = commonService.getBillingSystem(billingAccNum);

        if("BOS".equalsIgnoreCase(biilingSystem)
                && "Y".equalsIgnoreCase(mStrCheckExemptDCBOS)) {

            //2. A_DCExemptBOS
          /*
        * If strBilling_System = "BOS" Then
        If mStrCheckExempt_DC_BOS <> "Y" Then */
            // sendcallbuf = (FBFR32 *) FADD32(sendcallbuf, DCC_SQL_STRING,  "N", 2);
        /*

       DCC_SO_NBR = select TO_CHAR(ADD_MONTHS(SYSDATE,6516),'YYMM')||'9'||LTRIM(TO_CHAR(dcc_exempt_gen_bos_seq.nextval,'099999')) exempt_bos_seq FROM DUAL
         */
            String tmpSoNbr = dmsem002SetTreatmentExemptDao.getDCBOSSeq();

            // D_GetDCBOSSeq
            if("2".equalsIgnoreCase(inOperType)) {
                // if  (in_oper_type == 2) DCC_OPER_TYPE
                //         DCC_BILLING_ACC_NUM,  in_billing_acc_num
                //          DCC_MODULE_CODE,  'CCS'
                //        DCC_MODE_ID,  'DC'
                //        DCC_SQL_STRING 'O'
                // DCC_ORIGINAL_SO_NBR = D_GetDCBOSSeq
        /*
        select e.SO_NBR from DCC_EXEMPT e "
				" where e.BILLING_ACC_NUM = :billing_acc_num "
				" and e.MODULE_CODE = :module_code "
				" and e.MODE_ID = :mode_id
         */
                List<String> originalSoNbr = dccExemptRepo.getOriginalSoNBR(billingAccNum,moduleCode,modeId);

            }
            // end if

            //CCS_SetExCLBOS
            // wait for tuxedo proc
//        DCC_RESULT_CODE
//        if  (out_bos_result_code == 1000000)
            if(setExCLBOS() == 1000000L) {
//            D_UpdDCEMBOS
        /*
        update DCC_EXEMPT e set e.SO_NBR = :so_nbr "
						", SENT_BOS_FLAG = (select g.KEYWORD_VALUE from DCC_GLOBAL_PARAMETER g where g.SECTION_NAME = 'CONFIG_BOS_EXEMPT' and g.KEYWORD = 'SEND_BOS_FLAG') "
					" where e.BILLING_ACC_NUM = :billing_acc_num "
					"and e.MOBILE_NUM = :mobile_num "
					"and e.MODULE_CODE = :module_code "
					"and e.MODE_ID = :mode_id
         */
                dccExemptRepo.updateSONBR(tmpSoNbr,billingAccNum,mobileNum,moduleCode,modeId);
            }else {
//        else
//            D_InsDCEMBOS
                DccBosExemptId dccBosExemptId = DccBosExemptId.builder()
                        .billing_acc_num(billingAccNum)
                        .exemptLevel(exemptLevel)
                        .effectiveDat(effectiveDate)
                        .endDat(endDate)
                        .lastUpdateBy(updateBy)
                        .lastUpdateDtm(updateDate)
                        .modeId(modeId)
                        .mobileNum(mobileNum)
                        .moduleCode(moduleCode)
                        .operType(inOperType).build();

                DccBosExempt dccBosExempt = DccBosExempt.builder().id(dccBosExemptId).build();
                dccExemptBosRepo.saveAndFlush(dccBosExempt);
        /*
        insert into DCC_BOS_EXEMPT "
					"(billing_acc_num, mobile_num, module_code, mode_id, exempt_level, "
					"effective_dat, end_dat, oper_type, last_update_by, last_update_dtm) "
					"values (:billing_acc_num, :mobile_num, :module_code, :mode_id, :exempt_level, "
							"to_date(:effective_dat,'yyyy-mm-dd'), to_date(:end_dat,'yyyy-mm-dd'), :oper_type,  user, sysdate)
         */
            }
        }
    }

    private Long setExCLBOS(){
        return 1000000L;
    }

}


