package th.co.ais.mimo.debt.exempt.service.impl;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM003QueryExemptDao;
import th.co.ais.mimo.debt.exempt.dto.BillingAccDto;
import th.co.ais.mimo.debt.exempt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.exempt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.GetBillingRequest;
import th.co.ais.mimo.debt.exempt.model.QueryExemptRequest;
import th.co.ais.mimo.debt.exempt.repo.AccountHasMobileRepo;
import th.co.ais.mimo.debt.exempt.repo.DMSEM003NativeQueryService;
import th.co.ais.mimo.debt.exempt.service.DMSEM003QueryExemptService;

import java.util.List;

@Service
@Transactional
public class DMSEM003QueryExemptServiceImpl implements DMSEM003QueryExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DMSEM003QueryExemptDao dmsem003QueryExemptDao;

    private QueryExemptRequest setupDefaultCommonValue(QueryExemptRequest request){
        if(StringUtils.isEmpty(request.getMobileStatus())){
            request.setMobileStatus("ALL");
        }
        if(StringUtils.isEmpty(request.getExemptAction())){
            request.setExemptAction("ALL");
        }
        if(request.getStartRow()==null){
            request.setStartRow(1);
        }
        if(request.getEndRow()==0){
            request.setEndRow(1000);
        }
        return  request;
    }

    @Autowired
    AccountHasMobileRepo accountHasMobileRepo;
    @Autowired
    DMSEM003NativeQueryService dmsem003NativeQueryService;



    public List<DcExemptCurrentDtoMapping> queryExempt(QueryExemptRequest request) throws ExemptException {
        log.info("queryExempt request : {}",request);
        try {
            this.setupDefaultCommonValue(request);
            if("MNO".equals(request.getSelectType()) && !StringUtils.isEmpty(request.getBillingAccNum())){

                return this.dmsem003NativeQueryService.getExemptByMobileAndBilling(request.getMobileNum(),request.getCustAccNum(),request.getBillingAccNum(),request.getMobileStatus(),request.getStartRow(), request.getEndRow());
            }else if("BNO".equals(request.getSelectType())){

                return  this.dmsem003NativeQueryService.getExemptByBilling(request.getCustAccNum(),request.getBillingAccNum(), request.getMobileStatus(), request.getStartRow(), request.getEndRow());
            }else if("CNO".equals(request.getSelectType())) {
                return this.dmsem003NativeQueryService.getExemptByCustAccNum(request.getCustAccNum(), request.getMobileStatus(), request.getStartRow(), request.getEndRow());
            }else if("EFD".equals(request.getSelectType())) {

                return  this.dmsem003NativeQueryService.getExemptByEffectiveDate(request.getEffectiveDateFrom(), request.getEffectiveDateTo(), request.getMobileStatus(), request.getStartRow(), request.getEndRow());
            }else if("END".equals(request.getSelectType())) {

                return this.dmsem003NativeQueryService.getExemptByEndDate(request.getEndDateFrom(), request.getEndDateTo(), request.getMobileStatus(), request.getStartRow(), request.getEndRow());
            }else if("EPD".equals(request.getSelectType())) {

                return this.dmsem003NativeQueryService.getExemptByExpireDate(request.getExpireDateFrom(), request.getExpireDateTo(), request.getMobileStatus(), request.getStartRow(), request.getEndRow());
            }


           /* only  mobilenum
                sql = "select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level, " +
                        "     (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name,  " +
                        "      to_char(effective_dat,'YYYY/MM/DD') AS effective_dat ,  " +
                        "      to_char(end_dat,'YYYY/MM/DD') AS end_date ,  " +
                        "      to_char(expire_dat,'YYYY/MM/DD') AS expire_date, " +
                        "      cate_code,  " +
                        "     (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code = e.add_reason) add_reason ,  " +
                        "      add_location,  " +
                        "      (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "      update_location, last_update_by,  " +
                        "      to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "      no_of_exempt, sent_interface_flag  " +
                        "    from   dcc_exempt e"+
                        " where  cust_acc_num in ( select ou_num  from  account  " +
                        "   where  par_row_id in ( select ba.master_ou_id   " +
                        "   from   account ba, account_has_mobile mo    " +
                        "   where  mo.service_num = :mobileNum  " +
                        "   and mo.bill_accnt_num=ba.ou_num  " +
                        "   and  ba.accnt_type_cd='Billing'  " +
                        "       and mo.service_num = e.mobile_num)  " +
                        "   and  accnt_type_cd='Customer' ) and exempt_level ='CA' " +
                        "   union " +
                        "   select cust_acc_num, billing_acc_num,  " +
                        "   mobile_num, module_code, mode_id, exempt_level, " +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name,  " +
                        "   to_char(effective_dat,'YYYY/MM/DD') AS effective_dat  , to_char(end_dat,'YYYY/MM/DD') AS end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') AS expire_date, " +
                        "   cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "   no_of_exempt, sent_interface_flag  " +
                        "   from   dcc_exempt  e  " +
                        "    where billing_acc_num in ( select bill_accnt_num  " +
                        "   from account_has_mobile  " +
                        "  where service_num = :mobileNum)  " +
                        "  and exempt_level ='BA' " +
                        "  union " +
                        "  select cust_acc_num, billing_acc_num,  " +
                        "   mobile_num, module_code, mode_id, exempt_level, " +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name,  " +
                        "   to_char(effective_dat,'YYYY/MM/DD') AS effective_dat, to_char(end_dat,'YYYY/MM/DD') AS end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') AS expire_date, " +
                        "   cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "   no_of_exempt, sent_interface_flag  " +
                        "   from   dcc_exempt  e " +
                        "   where mobile_num = :mobileNum " +
                        "   and exempt_level ='MO' " +
                        " order by exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt ";
            */
            return null;
        }catch (PersistenceException | IllegalArgumentException e){
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

    @Override
    public List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException {
        return dmsem003QueryExemptDao.queryExemptHistory(request);

    }

    @Override
    public List<BillingAccDto> getBillingAccNum(GetBillingRequest request) throws ExemptException {
        return dmsem003QueryExemptDao.getBillingAccNum(request);

    }

}


