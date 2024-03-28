package th.co.ais.mimo.debt.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.service.QueryExemptService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class QueryExemptServiceImpl implements QueryExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public String setupModeQuery(QueryExemptRequest request){
        if(StringUtils.isEmpty(request.getCustAccNum()) && StringUtils.isEmpty(request.getBillingAccNum())
                && StringUtils.isEmpty(request.getMobileNum())
                && StringUtils.isEmpty(request.getEffectiveDateFrom()) && StringUtils.isEmpty(request.getEffectiveDateTo())
                && StringUtils.isEmpty(request.getEndDateFrom()) && StringUtils.isEmpty(request.getEndDateTo())
                && StringUtils.isEmpty(request.getExpireDateFrom()) && StringUtils.isEmpty(request.getExpireDateTo())){
            return "Default";
        }else if(!StringUtils.isEmpty(request.getCustAccNum()) && !StringUtils.isEmpty(request.getBillingAccNum()) && !StringUtils.isEmpty(request.getMobileNum())){
            return "M";
        }else if(!StringUtils.isEmpty(request.getMobileNum())){
            return "m";
        }else if(!StringUtils.isEmpty(request.getBillingAccNum())){
            return "B";
        }else if(!StringUtils.isEmpty(request.getCustAccNum())
                || (!StringUtils.isEmpty(request.getEffectiveDateFrom()) && !StringUtils.isEmpty(request.getEffectiveDateTo()))
                || (!StringUtils.isEmpty(request.getEndDateFrom()) && !StringUtils.isEmpty(request.getEndDateTo()))
                || (!StringUtils.isEmpty(request.getExpireDateFrom()) && !StringUtils.isEmpty(request.getExpireDateTo()))){
            return  "C";
        }
        return null;
    }
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
        request.setMode(this.setupModeQuery(request));
        log.info("search Mode :{}",request.getMode());

        return  request;
    }

    public List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException {
        log.info("queryExempt request : {}",request);
        try {
            String sql = "";
            HashMap<String ,Object> mapParam = new HashMap<>();
            this.setupDefaultCommonValue(request);

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
                        "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat , " +
                        "   to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') as expire_date, " +
                        "   cate_code,  " +
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
                        "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat ," +
                        "   to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                        "   to_char(expire_dat,'YYYY/MM/DD') as expire_date, cate_code,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason  ,  " +
                        "   add_location,  " +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                        "   update_location, last_update_by, " +
                        "   to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
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
                        "   ) aa) where rownumber >= :startRow and rownumber <= :endRow  " +
                        "   order by exempt_level desc, module_code,mode_id, cust_acc_num, billing_acc_num, mobile_num";
                    mapParam.put("custAccNum",request.getCustAccNum());
                    mapParam.put("billingAccNum",request.getBillingAccNum());
                    mapParam.put("mobileNum",request.getMobileNum());
                    mapParam.put("inDccMobileStatusList",request.getMobileStatus());
                    mapParam.put("startRow",request.getStartRow());
                    mapParam.put("endRow",request.getEndRow());
            }else if("B".equals(request.getMode())){
                        sql = "select * from (select aa.*, row_number() over (order by exempt_level desc, module_code,mode_id, cust_acc_num, billing_acc_num, mobile_status, mobile_num) as rownumber from (  " +
                                "  select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level,  " +
                                "  (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                                "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat,  " +
                                "   to_char(end_dat,'YYYY/MM/DD') as end_date,   " +
                                "   to_char(expire_dat,'YYYY/MM/DD') as expire_date,   " +
                                "   cate_code,   " +
                                "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,   " +
                                "   add_location,   " +
                                "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,   " +
                                "   update_location, last_update_by,   " +
                                "   to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,   " +
                                "   no_of_exempt, sent_interface_flag, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status   " +
                                "   from dcc_exempt e, account_has_mobile mb   " +
                                "   where billing_acc_num= :billingAccNum  " +
                                "   and e.billing_acc_num = mb.bill_accnt_num   " +
                                "   and e.mobile_num = mb.service_num   " +
                                "   and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)   " +
                                "    UNION  " +
                                "    select cust_acc_num, :billingAccNum as  billing_acc_num,  mobile_num, module_code, mode_id, exempt_level,  " +
                                "    (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                                "   to_char(effective_dat,'YYYY/MM/DD') as effective_dat,  " +
                                "   to_char(end_dat,'YYYY/MM/DD') as end_date,   " +
                                "   to_char(expire_dat,'YYYY/MM/DD') as expire_date,   " +
                                "   cate_code,   " +
                                "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,   " +
                                "   add_location,   " +
                                "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,   " +
                                "   update_location, last_update_by, " +
                                "   to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,   " +
                                "   no_of_exempt, sent_interface_flag, mb.status_cd mobile_status   " +
                                "   from dcc_exempt e, account_has_mobile mb   " +
                                "   where cust_acc_num= :custAccNum   " +
                                "   and exempt_level='CA'   " +
                                "   and e.billing_acc_num = mb.bill_accnt_num   " +
                                "   and e.mobile_num = mb.service_num   " +
                                "   and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)   " +
                                "     ) aa) where rownumber >= :startRow and rownumber <= :endRow  " +
                                "     order by exempt_level desc, module_code,mode_id, cust_acc_num, billing_acc_num, mobile_num";
                            mapParam.put("billingAccNum",request.getBillingAccNum());
                            mapParam.put("custAccNum",request.getCustAccNum());
                            mapParam.put("inDccMobileStatusList",request.getMobileStatus());
                            mapParam.put("startRow",request.getStartRow());
                            mapParam.put("endRow",request.getEndRow());
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
                        "   and exempt_level ='MO' " +
                        " order by exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt ";
                mapParam.put("mobileNum",request.getMobileNum());
            }else if("C".equals(request.getMode())){
                        sql = " select * from (select cust_acc_num, billing_acc_num,  " +
                                "     mobile_num, module_code, mode_id, exempt_level,  " +
                                "     (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                                "     to_char(effective_dat,'YYYY/MM/DD') AS effective_dat  ,  " +
                                "     to_char(end_dat,'YYYY/MM/DD')  AS end_date,  " +
                                "     to_char(expire_dat,'YYYY/MM/DD') AS expire_date,  " +
                                "     cate_code,  " +
                                "     (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason ,  " +
                                "     add_location,  " +
                                "     (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason,  " +
                                "     update_location, last_update_by,   " +
                                "     to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date,  " +
                                "     no_of_exempt, sent_interface_flag, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status,  " +
                                "     row_number() over (order by exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt) as rownumber  " +
                                "   from dcc_exempt e, account_has_mobile mb  ";
                               if(!StringUtils.isEmpty(request.getEffectiveDateFrom()) && !StringUtils.isEmpty(request.getEffectiveDateTo())) {
                                   sql = sql +"     where ( :effectiveDateFrom is null or effective_dat >= to_date( :effectiveDateFrom,'YYYY/MM/DD'))   " +
                                              "       and ( :effectiveDateTo is null or effective_dat <= to_date( :effectiveDateTo,'YYYY/MM/DD'))   ";
                                   mapParam.put("effectiveDateFrom",request.getEffectiveDateFrom());
                                   mapParam.put("effectiveDateTo",request.getEffectiveDateTo());
                               }else if(!StringUtils.isEmpty(request.getEndDateFrom()) && !StringUtils.isEmpty(request.getEndDateTo())) {
                                   sql = sql +"     where ( :endDateFrom is null or end_dat>=to_date( :endDateFrom,'YYYY/MM/DD'))   " +
                                              "       and ( :endDateTo is null or end_dat<=to_date( :endDateTo,'YYYY/MM/DD'))  ";
                                   mapParam.put("endDateFrom",request.getEndDateFrom());
                                   mapParam.put("endDateTo",request.getEndDateTo());
                               }else if(!StringUtils.isEmpty(request.getExpireDateFrom()) && !StringUtils.isEmpty(request.getExpireDateTo())) {
                                   sql = sql +"     where ( :expireDateFrom is null or expire_dat>=to_date( :expireDateFrom,'YYYY/MM/DD'))   " +
                                              "       and ( :expireDateTo is null or expire_dat<=to_date( :expireDateTo,'YYYY/MM/DD'))  ";
                                   mapParam.put("expireDateFrom",request.getExpireDateFrom());
                                   mapParam.put("expireDateTo",request.getExpireDateTo());
                               }else {
                                   sql = sql +"   where  cust_acc_num = :custAccNum  ";
                                   mapParam.put("custAccNum",request.getCustAccNum());
                               }
                                   sql = sql +"   and e.billing_acc_num = mb.bill_accnt_num   " +
                                              "           and e.mobile_num = mb.service_num   " +
                                              "           and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)   " +
                                              "           ) where rownumber >= :startRow and rownumber <= :endRow  " +
                                              "           order by rownumber ";
                                    mapParam.put("inDccMobileStatusList",request.getMobileStatus());
                                    mapParam.put("startRow",request.getStartRow());
                                    mapParam.put("endRow",request.getEndRow());
            }

            Query query = entityManager.createNativeQuery(sql, "dcExemptDtoMapping");
            if(!mapParam.isEmpty()){
                mapParam.forEach(query::setParameter);
            }
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

    @Override
    public List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException {
        try{
            String sql = "";
            HashMap<String ,Object> mapParam = new HashMap<>();
            this.setupDefaultCommonValue(request);

            if(!StringUtils.isEmpty(request.getMode())) {

                if ("M".equals(request.getMode())) {
                    sql = "select * from (select aa.*, row_number() over (order by exempt_level desc,  module_code, mode_id, cust_acc_num, billing_acc_num, mobile_status, mobile_num, no_of_exempt, exempt_seq) as rownumber from (  " +
                            "     select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level,  " +
                            "       (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,   " +
                            "       to_char(effective_dat,'YYYY/MM/DD') effective_dat , " +
                            "       to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "       to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,   " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,   " +
                            "       update_location, last_update_by, " +
                            "       to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,   " +
                            "       no_of_exempt , exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status   " +
                            "       from   dcc_exempt_history  e, account_has_mobile mb   " +
                            "       where billing_acc_num= :billingAccNum   " +
                            "       and   mobile_num=  :mobileNum  " +
                            "       and e.billing_acc_num = mb.bill_accnt_num   " +
                            "       and e.mobile_num = mb.service_num   " +
                            "       and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )   " +
                            "       and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)  " +
                            "   UNION   " +
                            "   select cust_acc_num, billing_acc_num,   " +
                            "       :mobileNum  as mobile_num,  " +
                            "       module_code, mode_id, exempt_level,  " +
                            "       (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,   " +
                            "       to_char(effective_dat,'YYYY/MM/DD') effective_dat ," +
                            "       to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "       to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,   " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,   " +
                            "       update_location, last_update_by, " +
                            "       to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,   " +
                            "       no_of_exempt, exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status   " +
                            "       from dcc_exempt_history e, account_has_mobile mb   " +
                            "       where billing_acc_num= :billingAccNum   " +
                            "       and   exempt_level='BA'   " +
                            "       and e.billing_acc_num = mb.bill_accnt_num   " +
                            "       and e.mobile_num = mb.service_num   " +
                            "       and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )   " +
                            "       and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:in_DCC_MOBILE_STAUTS_LIST,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)   " +
                            "  UNION  " +
                            "  select cust_acc_num,  " +
                            "       :billingAccNum as billing_acc_num,  " +
                            "       :mobileNum as  mobile_num, module_code, mode_id, exempt_level,  " +
                            "       (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,   " +
                            "       to_char(effective_dat,'YYYY/MM/DD') effective_dat , " +
                            "       to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "       to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,   " +
                            "       (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,   " +
                            "       update_location, last_update_by, " +
                            "       to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,   " +
                            "       no_of_exempt, exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status   " +
                            "       from dcc_exempt_history e, account_has_mobile mb   " +
                            "       where cust_acc_num= :custAccNum   " +
                            "       and exempt_level='CA'   " +
                            "       and e.billing_acc_num = mb.bill_accnt_num   " +
                            "       and e.mobile_num = mb.service_num   " +
                            "       and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )   " +
                            "       and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:in_DCC_MOBILE_STAUTS_LIST,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)   " +
                            "     ) aa   ";
                            if("Y".equals(request.getEffectiveDateLast())) {
                                sql = sql + "  where to_date(effective_dat,'YYYY/MM/DD') >=add_months(TRUNC(SYSDATE), -6)  ";
                            }
                                sql = sql + " ) where rownumber >= :startRow and rownumber <= :endRow   order by  rownumber";
                            mapParam.put("custAccNum",request.getCustAccNum());
                            mapParam.put("billingAccNum",request.getBillingAccNum());
                            mapParam.put("mobileNum",request.getMobileNum());
                            mapParam.put("startRow",request.getStartRow());
                            mapParam.put("endRow",request.getEndRow());
                            mapParam.put("inDccExemptActionList",request.getExemptAction());
                            mapParam.put("inDccMobileStatusList",request.getMobileStatus());

                } else if ("C".equals(request.getMode())) {
                    sql = "select * from (select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level, " +
                            "    (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                            "    to_char(effective_dat,'YYYY/MM/DD') ," +
                            "    to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "    to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "    (select reason_code || ' : ' || reason_description MODULE_CODE from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,  " +
                            "    (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,  " +
                            "    update_location, last_update_by, " +
                            "    to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,  " +
                            "    no_of_exempt , exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                            "    ,row_number() over (order by exempt_level desc,  module_code, mode_id, cust_acc_num, billing_acc_num, status_cd, mobile_num, no_of_exempt, exempt_seq) as rownumber  " +
                            "    from   dcc_exempt_history  e, account_has_mobile mb ";
                            
                            /*"    where (:v2 is null or effective_dat>=to_date(:v3,'YYYY/MM/DD'))  " +
                            "         and (:v4 is null or effective_dat<=to_date(:v5,'YYYY/MM/DD')) " +
                            
                            "    where (:v6 is null or end_dat>=to_date(:v7,'YYYY/MM/DD'))  " +
                            "     and (:v8 is null or end_dat<=to_date(:v9,'YYYY/MM/DD')) " +
                            
                            "   where (:v10 is null or expire_dat>=to_date(:v11,'YYYY/MM/DD'))  " +
                            "    and (:v12 is null or expire_dat<=to_date(:v13,'YYYY/MM/DD')) " +
                           
                            "    WHERE cust_acc_num = '32000070194200'  --V  " +
                           */
                            if(!StringUtils.isEmpty(request.getEffectiveDateFrom()) && !StringUtils.isEmpty(request.getEffectiveDateTo())) {
                                sql = sql +"     where ( :effectiveDateFrom is null or effective_dat >= to_date( :effectiveDateFrom,'YYYY/MM/DD'))   " +
                                        "       and ( :effectiveDateTo is null or effective_dat <= to_date( :effectiveDateTo,'YYYY/MM/DD'))   ";
                                mapParam.put("effectiveDateFrom",request.getEffectiveDateFrom());
                                mapParam.put("effectiveDateTo",request.getEffectiveDateTo());
                            }else if(!StringUtils.isEmpty(request.getEndDateFrom()) && !StringUtils.isEmpty(request.getEndDateTo())) {
                                sql = sql +"     where ( :endDateFrom is null or end_dat>=to_date( :endDateFrom,'YYYY/MM/DD'))   " +
                                        "       and ( :endDateTo is null or end_dat<=to_date( :endDateTo,'YYYY/MM/DD'))  ";
                                mapParam.put("endDateFrom",request.getEndDateFrom());
                                mapParam.put("endDateTo",request.getEndDateTo());
                            }else if(!StringUtils.isEmpty(request.getExpireDateFrom()) && !StringUtils.isEmpty(request.getExpireDateTo())) {
                                sql = sql +"     where ( :expireDateFrom is null or expire_dat>=to_date( :expireDateFrom,'YYYY/MM/DD'))   " +
                                        "       and ( :expireDateTo is null or expire_dat<=to_date( :expireDateTo,'YYYY/MM/DD'))  ";
                                mapParam.put("expireDateFrom",request.getExpireDateFrom());
                                mapParam.put("expireDateTo",request.getExpireDateTo());
                            }else {
                                sql = sql +"   where  cust_acc_num = :custAccNum  ";
                                mapParam.put("custAccNum",request.getCustAccNum());

                                if("Y".equals(request.getEffectiveDateLast())){
                                    sql = sql + " and e.effective_dat >= add_months(TRUNC(SYSDATE), -6) ";
                                }
                            }
                            sql = sql +"    AND e.billing_acc_num = mb.bill_accnt_num  " +
                            "       and e.mobile_num = mb.service_num  " +
                            "       and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )  " +
                            "       and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) " +
                            "    ) where rownumber >= :startRow and rownumber <= :endRow  " +
                            "    order by  rownumber";


                        mapParam.put("startRow",request.getStartRow());
                        mapParam.put("endRow",request.getEndRow());
                        mapParam.put("inDccExemptActionList",request.getExemptAction());
                        mapParam.put("inDccMobileStatusList",request.getMobileStatus());

                } else if ("B".equals(request.getMode())) {
                    sql = "select * from (select aa.*, row_number() over (order by exempt_level desc,  module_code, mode_id, cust_acc_num, billing_acc_num, mobile_status, mobile_num, no_of_exempt, exempt_seq) as rownumber from ( " +
                            "  select cust_acc_num, billing_acc_num, mobile_num, module_code, mode_id, exempt_level, " +
                            "    (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                            "    to_char(effective_dat,'YYYY/MM/DD') effective_dat , " +
                            "    to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "    to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "    (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,  " +
                            "    (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,  " +
                            "    update_location, last_update_by, " +
                            "    to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,  " +
                            "    no_of_exempt , exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                            "    from   dcc_exempt_history  e, account_has_mobile mb  " +
                            "    where billing_acc_num=  :billingAccNum  " +
                            "    and e.billing_acc_num = mb.bill_accnt_num  " +
                            "    and e.mobile_num = mb.service_num  " +
                            "    and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )  " +
                            "    and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)  " +
                            " UNION  " +
                            " select cust_acc_num,  " +
                            "    :billingAccNum as  billing_acc_num, " +
                            "    mobile_num, module_code, mode_id, exempt_level, " +
                            "    (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name,  " +
                            "    to_char(effective_dat,'YYYY/MM/DD') effective_dat , " +
                            "    to_char(end_dat,'YYYY/MM/DD') as end_date,  " +
                            "    to_char(expire_dat,'YYYY/MM/DD') as expire_date,  " +
                            "    (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason, add_location,  " +
                            "    (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason ,  " +
                            "    update_location, last_update_by, " +
                            "    to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as last_update_date,  " +
                            "    no_of_exempt, exempt_seq , cate_code, action_type, decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' ||  mb.x_suspend_type) mobile_status  " +
                            "    from dcc_exempt_history e, account_has_mobile mb " +
                            "    where cust_acc_num= :custAccNum " +
                            "    and exempt_level='CA'  " +
                            "    and e.billing_acc_num = mb.bill_accnt_num  " +
                            "    and e.mobile_num = mb.service_num  " +
                            "    and (:inDccExemptActionList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccExemptActionList,e.action_type) > 0 )  " +
                            "    and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) " +
                            "    ) aa  ";
                        if("Y".equals(request.getEffectiveDateLast())) {
                            sql = sql + "   where to_date(effective_dat,'YYYY/MM/DD') >= add_months(TRUNC(SYSDATE), -6) ";
                        }
                           sql = sql +  "   ) where rownumber >= :startRow  and rownumber <= :endRow  order by  rownumber";

                    mapParam.put("billingAccNum",request.getBillingAccNum());
                    mapParam.put("custAccNum",request.getCustAccNum()!=null?request.getCustAccNum():"");
                    mapParam.put("startRow",request.getStartRow());
                    mapParam.put("endRow",request.getEndRow());
                    mapParam.put("inDccExemptActionList",request.getExemptAction());
                    mapParam.put("inDccMobileStatusList",request.getMobileStatus());
                }else{
                    log.info("search Mode  not match!");
                    return null;
                }

                Query query = entityManager.createNativeQuery(sql, "dcExemptHistoryDtoMapping");
                if (!mapParam.isEmpty()) {
                    mapParam.forEach(query::setParameter);
                }
                return query.getResultList();
            }else{
                return null;
            }
        }catch (PersistenceException | IllegalArgumentException e){
            e.printStackTrace();
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }

    }

}


