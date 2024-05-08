package th.co.ais.mimo.debt.exempt.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM002SetTreatmentExemptDao;
import th.co.ais.mimo.debt.exempt.dto.AddExemptCustAccDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.entity.DccExemptHistory;
import th.co.ais.mimo.debt.exempt.entity.DccExemptHistoryId;
import th.co.ais.mimo.debt.exempt.entity.DccExemptModel;
import th.co.ais.mimo.debt.exempt.entity.DccExemptModelId;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.*;
import th.co.ais.mimo.debt.exempt.repo.DccExemptHistoryRepo;
import th.co.ais.mimo.debt.exempt.repo.DccExemptRepo;
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
    private static final String EXEMPT_ACTION_TYPE_DELETE = "ADD";

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

        //validate
        /*
        If CStr(marrNModule(IngSave)) = "CCS" And CStr(marrNMode_id(IngSave)) = "DC" Then
                    If ffCheckBilling_System(CStr(marrGBill_acc_num(IngSave))) = "BOS" Then
                        ลูกค้าเลขหมายระบบ BOS ไม่สามารถทำยกเว้น Suspend Credit Limit ได้
         */

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

            listCustAccDto.forEach(custAccDto -> {

                var mode = addExemptRequest.getExemptMode().split(",");

                for (int i = 0; i < mode.length; i++) {
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
                }
            });
        }else if(EXEMPT_BY_CATE.equals(addExemptRequest.getExemptBy())) {
            addExemptRequest.getExemptCateDetails().forEach(exemptCateDetailDto -> {

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



                listCustAccDto.forEach(custAccDto -> {

                    //1. D_GetDCExemptH
                    Long noOfExempt = dccExemptHistoryRepo.countNumberOfExempt(custAccDto.getCustAccNum(), addExemptRequest.getModule(), exemptCateDetailDto.getModeId());
                    Long exemptSeq = noOfExempt + 1;

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
                            addExemptRequest.getExemptLevel(),
                            addExemptRequest.getEndDate(),
                            addBy,
                            exemptCateDetailDto.getModuleCode(),
                            sentInterfaceFlag,
                            exemptCateDetailDto.getExpireDate(),
                            EXEMPT_ACTION_TYPE_ADD
                    );

                });

            });
        }

        return AddExemptResponse.builder().build();
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
                Long count = dccExemptRepo.countExemptByLevel(custAccDto.getCustAccNum(), Arrays.stream(new String[]{EXEMPT_LEVEL_BA, EXEMPT_LEVEL_MO}).toList());
                if(count > 0){
                    response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_LOW_LEVEL_FOUND);
                    break;
                }
                if(EXEMPT_BY_MODE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    // check 3.
                    Long countHis = dccExemptHistoryRepo.countExemptHistoryByCA(custAccDto.getCustAccNum(), EXEMPT_LEVEL_CA, addExemptRequest.getExemptMode());
                    if (countHis > 0) {
                        response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                        break;
                    }
                }else if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())){
                    for (DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()){
                        Long countHis = dccExemptHistoryRepo.countExemptHistoryByCA(custAccDto.getCustAccNum(), EXEMPT_LEVEL_CA, exemptCateDetailDto.getModeId());
                        if (countHis > 0) {
                            response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                            break;
                        }
                    }
                }
            }

        }else if(EXEMPT_LEVEL_BA.equalsIgnoreCase(addExemptRequest.getExemptLevel())){
            //find exempt level MO
            for (AddExemptCustAccDto custAccDto:addExemptRequest.getCustAccNo()){
                Long count = dccExemptRepo.countExemptByLevel(custAccDto.getCustAccNum(), Arrays.stream(new String[]{EXEMPT_LEVEL_MO}).toList());
                if(count > 0){
                    response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_LOW_LEVEL_FOUND);
                    break;
                }

                if(EXEMPT_BY_MODE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    // check 3.
                    Long countHis = dccExemptHistoryRepo.countExemptHistoryByBA(custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(), EXEMPT_LEVEL_BA, addExemptRequest.getExemptMode());
                    if (countHis > 0) {
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
                        response.setResponseCode(AppConstant.ADD_EXEMPT_WARNING_EXEMPT_FOUND);
                        break;
                    }
                }else if(EXEMPT_BY_CATE.equalsIgnoreCase(addExemptRequest.getExemptBy())) {
                    for (DccExemptCateDetailDto exemptCateDetailDto:addExemptRequest.getExemptCateDetails()) {
                        // check 3.
                        Long countHis = dccExemptHistoryRepo.countExemptHistoryByMobile(custAccDto.getCustAccNum(), custAccDto.getBillingAccNum(), custAccDto.getServiceNum(), EXEMPT_LEVEL_MO, exemptCateDetailDto.getModeId());
                        if (countHis > 0) {
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


        //max_exempt_seq = D_GetDCExemptH ++
        Long exemptSeq = dccExemptHistoryRepo.countExemptHisForUpdate(deleteExemptRequest.getCustAccNo(), deleteExemptRequest.getModule(), deleteExemptRequest.getMode(), deleteExemptRequest.getNoOfExempt());
        exemptSeq++;

        //DCC_SQL_STRING = 'D'

        // D_InsDCExemptH
        addExemptHistory(exemptSeq,deleteExemptRequest.getNoOfExempt(),deleteExemptRequest.getMode(),
                deleteExemptRequest.getMobileNo(), deleteExemptRequest.getBillingAccNo(), deleteExemptRequest.getCustAccNo(),
                location, deleteExemptRequest.getReason(),null,null, deleteExemptRequest.getExemptLevel(),
                deleteExemptRequest.getEndDate(), deleteBy, deleteExemptRequest.getModule(), "N",null,EXEMPT_ACTION_TYPE_DELETE);


        //D_DelDCExempt
        int effRecord = dccExemptRepo.deleteExemptByCA(deleteExemptRequest.getCustAccNo(),
                deleteExemptRequest.getBillingAccNo(),
                deleteExemptRequest.getModule(),
                deleteExemptRequest.getMode(),
                deleteExemptRequest.getExemptLevel(),
                deleteExemptRequest.getNoOfExempt());

        if(effRecord<=0){
            //throws exception for rollback
            throw new ExemptException(AppConstant.DELETE_EXEMPT_ERROR_RECORD_NOT_FOUND,"Record Not Found");
        }


        return response;
    }

    @Override
    public UpdateExemptResponse updateExempt(UpdateExemptRequest updateExemptRequest,Integer location,String updateBy)throws ExemptException{
        UpdateExemptResponse response = UpdateExemptResponse.builder().responseCode(AppConstant.SUCCESS).build();

        // insert exempt history
        Long exemptSeq = dccExemptHistoryRepo.countExemptHisForUpdate(updateExemptRequest.getCustAccNo(), updateExemptRequest.getModule(), updateExemptRequest.getMode(), updateExemptRequest.getNoOfExempt());
        exemptSeq++;

        addExemptHistory(exemptSeq,updateExemptRequest.getNoOfExempt(),updateExemptRequest.getMode(),
                updateExemptRequest.getMobileNo(), updateExemptRequest.getBillingAccNo(), updateExemptRequest.getCustAccNo(),
                location, updateExemptRequest.getReason(),null,null, updateExemptRequest.getExemptLevel(),
                updateExemptRequest.getEndDate(), updateBy, updateExemptRequest.getModule(), "N",null,EXEMPT_ACTION_TYPE_UPDATE);

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

        int effRecord = dccExemptRepo.updateExemptByCA(updateExemptRequest.getReason(),
                location,updateBy,EXEMPT_SENT_INTERFACE_FLAG_N,updateExemptRequest.getCustAccNo(), updateExemptRequest.getModule(),
                updateExemptRequest.getMode(),
                updateExemptRequest.getExemptLevel(),
                updateExemptRequest.getNoOfExempt());

        if(effRecord<=0){
            throw new ExemptException(AppConstant.UPDATE_EXEMPT_ERROR_RECORD_NOT_FOUND,"Record Not Found");
        }

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

}


