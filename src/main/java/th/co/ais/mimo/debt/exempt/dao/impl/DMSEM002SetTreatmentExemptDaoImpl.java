package th.co.ais.mimo.debt.exempt.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM002SetTreatmentExemptDao;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.SearchRequest;

import java.util.HashMap;
import java.util.List;

@Component
public class DMSEM002SetTreatmentExemptDaoImpl implements DMSEM002SetTreatmentExemptDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String SEARCH_TYPE_CA="CA";
    private final String SEARCH_TYPE_BA="BA";
    private final String SEARCH_TYPE_MO="MO";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException {

        try {
            String sql = "";
            HashMap<String, Object> mapParam = new HashMap<>();

            if(SEARCH_TYPE_CA.equals(request.getSearchType())) {
                sql = " select ca.ou_num cust_acc_num,  " +
                        "        ba.ou_num billing_acc_num, ba.name,    " +
                        "        ba.cust_stat_cd ba_status,  " +
                        "        mo.service_num,    " +
                        "        mo.status_cd mobile_status,   " +
                        "        mo.x_status_date, " +
                        "        ba.woc_flag  " +
                        "        ,decode(ba.cust_stat_cd,'Active',ba.limit_mny,null) limit_mny   " +
                        "        , ba.company_code  " +
                        "        , ba.customer_id  " +
                        "        , ba.x_id_type  " +
                        "        , ba.bill_style  " +
                        "        , ba.wht_req  " +
                        "        , ba.payment_method  " +
                        "        , ba.paymt_term  " +
                        "        , ba.bill_cycle  " +
                        "        , ba.x_paymt_credit_term as credit_term  " +
                        "        , ba.debt_mny  " +
                        "        , account_name   " +
                        "        , sa.ou_num sa_num    " +
                        "        , sa.cust_stat_cd sa_status   " +
                        "        , DCCU_DBUTIL.GET_COMPANY_TYPE_MOBILE(ba.ou_num,mo.service_num)   company_type  " +
                        "        , ca.name ca_name  " +
                        "        ,(Select  desc_text  " +
                        "        from    account_has_mobile_promo a, product p  " +
                        "        Where   a.bill_accnt_num = ba.ou_num  " +
                        "        and     p.row_id         = a.x_promotion_id  " +
                        "        and     a.status_cd      = 'Active'  " +
                        "        and     a.x_promo_class  = 'Main'  " +
                        "        and     sysdate between a.x_promo_start_dt and nvl(a.x_promo_end_dt,sysdate)  " +
                        "        And     rownum = 1) main_promo  " +
                        "        ,'' no_print_bill_flag  " +
                        "       , DCCU_DBUTIL.GET_SALES_REP(ca.ou_num) Sales_Rep  " +
                        "       , DCCU_DBUTIL.GET_SALES_REP_MST(ca.ou_num) Sales_Rep_MST  " +
                        "       , DCCU_DBUTIL.GET_SALES_REP_MOBILE('A', '', mo.service_num) sales_rep_mobile  " +
                        "       , ca.collection_segment  " +
                        "       from ( select a.master_ou_id master_ou_id, a.ou_num ou_num,     " +
                        "          a.name name, a.cust_stat_cd cust_stat_cd,   " +
                        "          decode(a.x_write_off_dt,null,'N','Y') woc_flag,  " +
                        "          (select limit_mny   " +
                        "          from csr_w_limit_master lm   " +
                        "          where lm.billing_account_num = a.ou_num) limit_mny   " +
                        "          , a.customer_id  " +
                        "          , a.x_id_type  " +
                        "          , bp.bill_source_cd company_code  " +
                        "          , bp.bill_pref_cd   bill_style  " +
                        "          , nvl(bp.x_wt_submit_req,'N') wht_req  " +
                        "          , bp.paymt_meth_cd   payment_method  " +
                        "          , bp.x_paymt_term   paymt_term  " +
                        "          , bp.x_bill_cycle   bill_cycle  " +
                        "          , bp.x_paymt_credit_term  " +
                        "          ,(select pab.total_balance_mny  " +
                        "                     from pm_account_balance pab  " +
                        "                    where pab.billing_acc_num = bp.ou_num) debt_mny  " +
                        "          , a.par_ou_id  " +
                        "          , bp.x_con_full_name account_name " +
                        "         from account a, billing_profile bp   " +
                        "         where master_ou_id = (select par_row_id  " +
                        "                from account  " +
                        "                where  ou_num = :paramValue ) " +
                        "         and a.accnt_type_cd='Billing'  " +
                        "         and a.ou_num =  bp.ou_num ) ba,  " +
                        "        account ca,account_has_mobile mo ,account sa     " +
                        "       where ba.master_ou_id = ca.par_row_id  " +
                        "       and   ba.ou_num = mo.bill_accnt_num     " +
                        "       and   ba.par_ou_id = sa.par_row_id(+)  " +
                        "       and sa.accnt_type_cd(+) = 'Service'   " +
                        "      order by ba.company_code, ba_status, ba.ou_num, mo.status_cd, mo.service_num ";
            }else if(SEARCH_TYPE_BA.equals(request.getSearchType())){
                sql = " select ca.ou_num cust_acc_num,  " +
                        "         ba.ou_num billing_acc_num, ba.name,   " +
                        "         ba.cust_stat_cd ba_status, mo.service_num ,    " +
                        "         mo.status_cd mobile_status    " +
                        "         , mo.x_status_date ,ba.woc_flag  " +
                        "         ,decode(ba.cust_stat_cd,'Active',ba.limit_mny,null) limit_mny   " +
                        "         , ba.company_code  " +
                        "         , ba.customer_id  " +
                        "         , ba.x_id_type  " +
                        "         , ba.bill_style  " +
                        "         , ba.wht_req  " +
                        "         , ba.payment_method  " +
                        "         , ba.paymt_term  " +
                        "         , ba.bill_cycle  " +
                        "         , ba.x_paymt_credit_term as credit_term  " +
                        "         , ba.debt_mny  " +
                        "         , account_name  " +
                        "         , sa.ou_num sa_num   " +
                        "         , sa.cust_stat_cd sa_status     " +
                        "         ,DCCU_DBUTIL.GET_COMPANY_TYPE_MOBILE(ba.ou_num,mo.service_num)   company_type  " +
                        "         , ca.name ca_name   " +
                        "         ,(Select  desc_text  " +
                        "         from    account_has_mobile_promo a, product p  " +
                        "         Where   a.bill_accnt_num = ba.ou_num  " +
                        "         and     p.row_id         = a.x_promotion_id  " +
                        "         and     a.status_cd      = 'Active'  " +
                        "         and     a.x_promo_class  = 'Main'  " +
                        "         and     sysdate between a.x_promo_start_dt and nvl(a.x_promo_end_dt,sysdate)  " +
                        "         And     rownum = 1) main_promo  " +
                        "         ,'' no_print_bill_flag  " +
                        "       , DCCU_DBUTIL.GET_SALES_REP(ca.ou_num) Sales_Rep  " +
                        "       , DCCU_DBUTIL.GET_SALES_REP_MST(ca.ou_num) Sales_Rep_MST   " +
                        "       , DCCU_DBUTIL.GET_SALES_REP_MOBILE('A', '', mo.service_num) sales_rep_mobile  " +
                        "       , ca.collection_segment  " +
                        "       from ( select a.master_ou_id master_ou_id, a.ou_num ou_num,  " +
                        "           a.name name, a.cust_stat_cd cust_stat_cd ,  " +
                        "           decode(a.x_write_off_dt,null,'N','Y') woc_flag,   " +
                        "           (select limit_mny   " +
                        "            from csr_w_limit_master lm   " +
                        "            where lm.billing_account_num = a.ou_num) limit_mny   " +
                        "           , a.customer_id  " +
                        "           , a.x_id_type  " +
                        "           , bp.bill_source_cd company_code  " +
                        "           , bp.bill_pref_cd   bill_style  " +
                        "           , nvl(bp.x_wt_submit_req,'N') wht_req  " +
                        "           , bp.paymt_meth_cd   payment_method  " +
                        "           , bp.x_paymt_term   paymt_term  " +
                        "           , bp.x_bill_cycle   bill_cycle  " +
                        "           , bp.x_paymt_credit_term  " +
                        "           ,(select pab.total_balance_mny  " +
                        "                      from pm_account_balance pab  " +
                        "                     where pab.billing_acc_num = bp.ou_num) debt_mny  " +
                        "           , a.par_ou_id     " +
                        "                 , bp.x_con_full_name account_name   " +
                        "         from account a, billing_profile bp    " +
                        "          where  a.ou_num = :paramValue    " +
                        "         and a.accnt_type_cd='Billing'  " +
                        "         And a.ou_num = bp.ou_num) ba, account sa,     " +
                        "        account ca,account_has_mobile mo   " +
                        "       where ba.master_ou_id = ca.par_row_id    " +
                        "       and   ba.ou_num = mo.bill_accnt_num    " +
                        "       and  ba.par_ou_id = sa.par_row_id(+)  " +
                        "       and sa.accnt_type_cd(+) = 'Service'  " +
                        "      order by ba.company_code, ba_status, ba.ou_num, mo.status_cd, mo.service_num ";

            }else if(SEARCH_TYPE_MO.equals(request.getSearchType())){
                sql = " select cust_acc_num,  " +
                        "       billing_acc_num, " +
                        "       name,  " +
                        "       ba_status,  " +
                        "       service_num,  " +
                        "       mobile_status   " +
                        "       from (select rownum           rn,  " +
                        "         ba.x_status_date,  " +
                        "         ca.ou_num        cust_acc_num,  " +
                        "         ba.ou_num        billing_acc_num,  " +
                        "         ba.name,  " +
                        "         ba.cust_stat_cd  ba_status,  " +
                        "         ba.mobile_num as service_num,  " +
                        "         ba.mobile_status  " +
                        "       from (select a.master_ou_id  master_ou_id,  " +
                        "           a.ou_num        ou_num,  " +
                        "           a.name          name,  " +
                        "           a.cust_stat_cd  cust_stat_cd,  " +
                        "           mo.service_num  mobile_num,  " +
                        "           mo.status_cd    mobile_status,  " +
                        "           mo.x_status_date,   " +
                        "           nvl(mo.source_type,'SBL') source_type   " +
                        "           from account a, account_has_mobile mo  " +
                        "           where mo.service_num = :paramValue " +
                        "           and mo.bill_accnt_num = a.ou_num  " +
                        "           and a.accnt_type_cd = 'Billing'   " +
                        "           order by cust_stat_cd asc,source_type desc,x_status_date desc ) ba,  " +
                        "         account ca  " +
                        "      where ba.master_ou_id = ca.par_row_id)  " +
                        "     where rn = 1 ";
                mapParam.put("paramValue",request.getParamValue());
                Query query = entityManager.createNativeQuery(sql, "searchTreatmentMobileDtoMapping");
                if (!mapParam.isEmpty()) {
                    mapParam.forEach(query::setParameter);
                }
                return query.getResultList();

            }
            mapParam.put("paramValue",request.getParamValue());
            Query query = entityManager.createNativeQuery(sql, "searchTreatmentDtoMapping");
            if (!mapParam.isEmpty()) {
                mapParam.forEach(query::setParameter);
            }
            return query.getResultList();
        } catch (PersistenceException | IllegalArgumentException e) {
            throw new ExemptException(AppConstant.FAIL, e.getMessage());
        }
    }

    @Override
    public List<ExemptDetailDto> searchExemptDetail(String searchType, String vaule) throws ExemptException {
        try{
            String sql = "";
            HashMap<String, Object> mapParam = new HashMap<>();
            if(SEARCH_TYPE_CA.equalsIgnoreCase(searchType)){
                mapParam.put("recv_cust_acc_num",vaule);
                //mapParam.put("recv_row_start",1);
                //mapParam.put("recv_row_end",10);
                sql = " select * from (select cust_acc_num, billing_acc_num, \n" +
                        " mobile_num, module_code, mode_id, exempt_level,\n" +
                        " (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, \n" +
                        " to_char(effective_dat,'DD/MM/YYYY') effective_date, to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        " to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        " cate_code, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        " add_location, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        " update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        " no_of_exempt, sent_interface_flag \n" +
                        " , row_number() over (order by exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt) as rownumber \n" +
                        " from   dcc_exempt  e \n" +
                        " where  cust_acc_num = :recv_cust_acc_num) \n" +
                        //" where rownumber >= :recv_row_start and rownumber <= :recv_row_end \n" +
                        " order by rownumber";
                Query query = entityManager.createNativeQuery(sql, "searchExemptDetailDtoMapping");
                if (!mapParam.isEmpty()) {
                    mapParam.forEach(query::setParameter);
                }
                return query.getResultList();
            }else if(SEARCH_TYPE_BA.equalsIgnoreCase(searchType)){
                mapParam.put("recv_billing_acc_num",vaule);
                //mapParam.put("recv_row_start",1);
                //mapParam.put("recv_row_end",10);
                sql = " SELECT a.*,row_number() over (order BY exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt) rownumber FROM ( \n" +
                        "select cust_acc_num, billing_acc_num, \n" +
                        "   mobile_num, module_code, mode_id, exempt_level,\n" +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, \n" +
                        "   to_char(effective_dat,'DD/MM/YYYY') effective_date , to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        "   to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        "   cate_code, \n" +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        "   add_location, \n" +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        "   update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        "   no_of_exempt, sent_interface_flag \n" +
                        "   from   dcc_exempt  e\n" +
                        "   where cust_acc_num = ( select ou_num from account \n" +
                        "  where  par_row_id =(select master_ou_id from account \n" +
                        " where  ou_num = :recv_billing_acc_num \n" +
                        "  and  accnt_type_cd='Billing' \n" +
                        "        and ou_num = e.billing_acc_num ) \n" +
                        "  and  accnt_type_cd='Customer' ) and exempt_level='CA' \n" +
                        "\n" +
                        "  UNION\n" +
                        "\n" +
                        "  select cust_acc_num, billing_acc_num, \n" +
                        " mobile_num, module_code, mode_id, exempt_level,\n" +
                        " (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing' ) billing_acc_name, \n" +
                        " to_char(effective_dat,'DD/MM/YYYY') effective_date, to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        " to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        " cate_code, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        " add_location, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        " update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        " no_of_exempt, sent_interface_flag \n" +
                        " from   dcc_exempt  e \n" +
                        " where billing_acc_num = :recv_billing_acc_num   \n" +
                        " and exempt_level in ('BA','MO')\n" +
                        " )a\n" +
                        " order by rownumber";
                Query query = entityManager.createNativeQuery(sql, "searchExemptDetailDtoMapping");

                if (!mapParam.isEmpty()) {
                    mapParam.forEach(query::setParameter);
                }
                return query.getResultList();
            }else if(SEARCH_TYPE_MO.equalsIgnoreCase(searchType)){
                mapParam.put("recv_service_num",vaule);
                sql = "--D_LstDCExempt m\n" +
                        "SELECT a.*,row_number() over (order BY exempt_level desc, module_code, mode_id, cust_acc_num, billing_acc_num, mobile_num, no_of_exempt) rownumber FROM ( \n" +
                        "select cust_acc_num, billing_acc_num, \n" +
                        "   mobile_num, module_code, mode_id, exempt_level,\n" +
                        "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, \n" +
                        "   to_char(effective_dat,'DD/MM/YYYY') effective_date , to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        "   to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        "   cate_code, \n" +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        "   add_location, \n" +
                        "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        "   update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        "   no_of_exempt, sent_interface_flag \n" +
                        "   from   dcc_exempt  e\n" +
                        "   where --cust_acc_num = '32000070195995'\n" +
                        "   \tcust_acc_num in ( select ou_num  from  account \n" +
                        " where  par_row_id in ( select ba.master_ou_id  \n" +
                        " from   account ba, account_has_mobile mo   \n" +
                        " where  mo.service_num = :recv_service_num \n" +
                        " and mo.bill_accnt_num=ba.ou_num \n" +
                        " and  ba.accnt_type_cd='Billing' \n" +
                        "         and mo.service_num = e.mobile_num) \n" +
                        " and  accnt_type_cd='Customer' ) \n" +
                        " and exempt_level ='CA' \n" +
                        " \n" +
                        " UNION\n" +
                        " \n" +
                        " select cust_acc_num, billing_acc_num, \n" +
                        " mobile_num, module_code, mode_id, exempt_level,\n" +
                        " (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, \n" +
                        " to_char(effective_dat,'DD/MM/YYYY') effective_date, to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        " to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        " cate_code, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        " add_location, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        " update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        " no_of_exempt, sent_interface_flag \n" +
                        " from   dcc_exempt  e \n" +
                        "  where billing_acc_num in ( select bill_accnt_num \n" +
                        " from account_has_mobile \n" +
                        "where service_num = :recv_service_num) \n" +
                        "and exempt_level ='BA' \n" +

                        " UNION \n" +

                        " select cust_acc_num, billing_acc_num, \n" +
                        " mobile_num, module_code, mode_id, exempt_level,\n" +
                        " (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, \n" +
                        " to_char(effective_dat,'DD/MM/YYYY') effective_date , to_char(end_dat,'DD/MM/YYYY') end_date, \n" +
                        " to_char(expire_dat,'DD/MM/YYYY') expire_date,\n" +
                        " cate_code, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) add_reason , \n" +
                        " add_location, \n" +
                        " (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, \n" +
                        " update_location, last_update_by, to_char(last_update_dtm,'DD/MM/YYYY HH24:Mi:SS') last_update_date, \n" +
                        " no_of_exempt, sent_interface_flag \n" +
                        " from   dcc_exempt  e\n" +
                        " where mobile_num = :recv_service_num --'8919893721' --\n" +
                        " and exempt_level ='MO'\n" +
                        " \n" +
                        ")a";
                Query query = entityManager.createNativeQuery(sql, "searchExemptDetailDtoMapping");

                if (!mapParam.isEmpty()) {
                    mapParam.forEach(query::setParameter);
                }
                return query.getResultList();
            }
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("error occured when search exempt detail ",e);
            throw new ExemptException(AppConstant.FAIL, e.getMessage());
        }

        return null;
    }

}
