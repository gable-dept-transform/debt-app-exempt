package th.co.ais.mimo.debt.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.service.QueryExemptService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QueryExemptServiceImpl implements QueryExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public String setupModeQuery(QueryExemptRequest request){
        if(StringUtils.isEmpty(request.getCustAccNum()) && StringUtils.isEmpty(request.getBillingAccNum())
                && StringUtils.isEmpty(request.getMobileNum()) && StringUtils.isEmpty(request.getEffectiveDate()) && StringUtils.isEmpty(request.getEndDate())
                && StringUtils.isEmpty(request.getExpireDate())){
            return "Default";
        }else if(!StringUtils.isEmpty(request.getMobileNum())){
            return "m";
        }else if(!StringUtils.isEmpty(request.getBillingAccNum())){
            return "B";
        }else if(!StringUtils.isEmpty(request.getCustAccNum()) || !StringUtils.isEmpty(request.getEffectiveDate()) || !StringUtils.isEmpty(request.getEndDate())
                || !StringUtils.isEmpty(request.getExpireDate())){
            return  "C";
        }else if(!StringUtils.isEmpty(request.getCustAccNum()) && !StringUtils.isEmpty(request.getBillingAccNum()) && !StringUtils.isEmpty(request.getMobileNum())){
            return "M";
        }
        return null;
    }

    public List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException {
        log.info("queryExempt request : {}",request);
        try {
            String sql = "";

            request.setMode(this.setupModeQuery(request));

            if(StringUtils.isEmpty(request.getMode())){
                throw  new ExemptException(AppConstant.FAIL,"CRITERIA QUERY NOT FOUND");
            }

            if("Default".equals(request.getMode())) {
                sql = "select cust_acc_num, billing_acc_num, " +
                        "   mobile_num, module_code, mode_id, exempt_level," +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, " +
                        "   to_char(effective_dat,'YYYY/MM/DD') AS effective_dat , to_char(end_dat,'YYYY/MM/DD') AS end_date , " +
                        "   to_char(expire_dat,'YYYY/MM/DD') AS expire_date," +
                        "   cate_code, " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code = e.add_reason) add_reason , " +
                        "   add_location, " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, " +
                        "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date, " +
                        "   no_of_exempt, sent_interface_flag " +
                        "   from   dcc_exempt e";
            }else if("M".equals(request.getMode())){
                sql = "select * from (select aa.*, row_number() over (order by exempt_level desc, module_code,mode_id, cust_acc_num, billing_acc_num,mobile_status, mobile_num) as rownumber from ( " +
                        " select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level, " +
                        "   (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name, " +
                        "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat , to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') as expire_date, cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location,  " +
                        "  last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "   no_of_exempt, sent_interface_flag, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                        "   from dcc_exempt e ,account_has_mobile mb  " +
                        "   where billing_acc_num= :billingAccNum  " +
                        "   and   mobile_num= :mobileNum  " +
                        "   and   exempt_level='MO'  " +
                        "   and e.billing_acc_num = mb.bill_accnt_num  " +
                        "   and e.mobile_num = mb.service_num  " +
                        "   and ( :inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)  " +
                        " union  " +
                        " select cust_acc_num, billing_acc_num, :mobileNum  as mobile_num, module_code, mode_id, exempt_level, " +
                        "    (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, " +
                        "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat , to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') as expire_date, cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason  ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "   no_of_exempt, sent_interface_flag, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                        "   from dcc_exempt e ,account_has_mobile mb  " +
                        "   where billing_acc_num= :billingAccNum  " +
                        "   and   exempt_level='BA'  " +
                        "   and e.billing_acc_num = mb.bill_accnt_num  " +
                        "   and e.mobile_num = mb.service_num  " +
                        "   and ( :inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) " +
                        " union  " +
                        " select cust_acc_num,  :billingAccNum as billing_acc_num, :mobileNum as  mobile_num, module_code, mode_id, exempt_level, " +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, " +
                        "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat, to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') as expire_date, cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                        "   no_of_exempt, sent_interface_flag, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                        "   from dcc_exempt e ,account_has_mobile mb " +
                        "   where cust_acc_num= :custAccNum  " +
                        "   and exempt_level='CA'  " +
                        "   and e.billing_acc_num = mb.bill_accnt_num  " +
                        "   and e.mobile_num = mb.service_num  " +
                        "   and ( :inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST( :inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) " +
                        "   ) aa) where rownumber >=0 and rownumber <= 1000  " +
                        "   order by exempt_level desc, module_code,mode_id, cust_acc_num, billing_acc_num, mobile_num";

            }else if("B".equals(request.getMode())){


            }else if("m".equals(request.getMode())){
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
                        "   and exempt_level ='MO' ";

            }else if("C".equals(request.getMode())){

            }

            Query query = entityManager.createNativeQuery(sql, "dcExemptDtoMapping");
            if("M".equals(request.getMode())){
                query.setParameter("custAccNum",request.getCustAccNum());
                query.setParameter("billingAccNum",request.getBillingAccNum());
                query.setParameter("mobileNum",request.getMobileNum());
                query.setParameter("inDccMobileStatusList",request.getMobileStatus());
            }else if ("m".equals(request.getMode())){
                query.setParameter("mobileNum",request.getMobileNum());
            }
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

}


