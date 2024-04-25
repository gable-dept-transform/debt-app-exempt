package th.co.ais.mimo.debt.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.dto.treatment.SearchTreatmentDto;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchRequest;
import th.co.ais.mimo.debt.service.DMSEM002SetTreatmentExemptService;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class DMSEM002SetTreatmentExemptServiceImpl implements DMSEM002SetTreatmentExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException {

        try {
            String sql = "";
            HashMap<String, Object> mapParam = new HashMap<>();

            if("CA".equals(request.getSearchType())) {
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
            }else if("BA".equals(request.getSearchType())){
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
                
            }else if("MO".equals(request.getSearchType())){
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


}


