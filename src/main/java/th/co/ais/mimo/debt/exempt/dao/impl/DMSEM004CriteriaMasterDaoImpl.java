package th.co.ais.mimo.debt.exempt.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import th.co.ais.mimo.debt.exempt.dao.DMSEM004CriteriaMasterDao;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterDto;
import th.co.ais.mimo.debt.exempt.utils.SqlUtils;


@Repository
public class DMSEM004CriteriaMasterDaoImpl implements DMSEM004CriteriaMasterDao{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description)
			throws Exception {
		List<DMSEM004CriteriaMasterBean> result = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql = searchDataQuerySql(sql, modeId, criteriaId, description);             
            Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
            query = searchDataQueryParam(query, modeId, criteriaId, description);
            result = SqlUtils.parseResult(query.getResultList(), DMSEM004CriteriaMasterBean.class);
        } catch (Exception e) {
            log.error("Exception searchScoreHistory : {}", e.getMessage(), e);
            throw e;
        } finally {
            SqlUtils.close(entityManager);
        }
        return result;
	}
	
	private StringBuffer searchDataQuerySql(StringBuffer sql, String modeId, Long criteriaId, String description) throws Exception {
        try {
            if (sql != null) {
                 
            	 sql.append(" SELECT criteria_id AS \"criteriaId\",   ");
            	 sql.append(" criteria_type AS \"criteriaType\",    ");
            	 sql.append(" criteria_description AS \"criteriaDescription\",   ");
            	 sql.append(" company_code AS \"companyCode\",  ");
            	 sql.append(" collection_segment_list AS \"collectionSegmentList\",   ");
            	 sql.append(" payment_type_list AS \"paymentTypeList\",   ");
            	 sql.append(" bill_cycle_list AS \"billCycleList\",  ");
            	 sql.append(" region_list AS \"regionList\",   ");
            	 sql.append(" mobile_status_list AS \"mobileStatusList\",   ");
            	 sql.append(" product_group_list AS \"productGroupList\",   ");
            	 sql.append(" cate_subcate_list AS \"cateSubcateList\",  ");
            	 sql.append(" ba_status_list AS \"baStatusList\",   ");
            	 sql.append(" min_invoice_mny AS \"minInvoiceMny\",   ");
            	 sql.append(" invoice_count AS \"invoiceCount\",  " );
            	 sql.append(" debt_mny_from AS \"debtMnyFrom\",   " );
            	 sql.append(" debt_mny_to AS \"debtMnyTo\",   " );
            	 sql.append(" ar_mny_from AS \"arMnyFrom\",   " );
            	 sql.append(" ar_mny_to AS \"arMnyTo\",  " );
            	 sql.append(" debt_age_from AS \"debtAgeFrom\",   " );
            	 sql.append(" debt_age_to AS \"debtAgeTo\",   " );
            	 sql.append(" credit_term_flag AS \"creditTermFlag\",   " );
            	 sql.append(" status_age_from AS \"statusAgeFrom\",   " );
            	 sql.append(" status_age_to AS \"statusAgeTo\",   " );
            	 sql.append(" service_age_from AS \"serviceAgeFrom\",   " );
            	 sql.append(" service_age_to AS \"serviceAgeTo\",  " );
            	 sql.append(" TO_CHAR(first_ar_dat_from, 'YYYY/MM/DD') AS \"firstArDatFrom\",   " );
            	 sql.append(" TO_CHAR(first_ar_dat_to, 'YYYY/MM/DD') AS \"firstArDatTo\",  " );
            	 sql.append(" TO_CHAR(ba_inactive_dat_from, 'YYYY/MM/DD') AS \"baInactiveDatFrom\",   " );
            	 sql.append(" TO_CHAR(ba_inactive_dat_to, 'YYYY/MM/DD') AS \"baInactiveDatTo\",  " );
            	 sql.append(" status_reason_list AS \"statusReasonList\",   " );
            	 sql.append(" action_reason_list AS \"actionReasonList\",   " );
            	 sql.append(" nego_flag AS \"negoFlag\",   " );
            	 sql.append(" exempt_flag AS \"exemptFlag\",   " );
            	 sql.append(" max_account AS \"maxAccount\",   " );
            	 sql.append(" max_amount AS \"maxAmount\",  " );
            	 sql.append(" assign_duration AS \"assignDuration\",   " );
            	 sql.append(" campaign_code AS \"campaignCode\",  " );
            	 sql.append(" agent_type AS \"agentType\",   " );
            	 sql.append(" assign_type AS \"assignType\",    " );
            	 sql.append(" assign_job AS \"assignJob\",   " );
            	 sql.append(" due_day AS \"dueDay\",   " );
            	 sql.append(" call_type AS \"callType\",   " );
            	 sql.append(" message_id AS \"messageId\",   " );
            	 sql.append(" max_call AS \"maxCall\",   " );
            	 sql.append(" max_redial AS \"maxRedial\",  " );
            	 sql.append(" template_id AS \"templateId\",   " );
            	 sql.append(" TO_CHAR(check_dat_from, 'YYYY/MM/DD') AS \"checkDatFrom\",   " );
            	 sql.append(" TO_CHAR(check_dat_to, 'YYYY/MM/DD') AS \"checkDatTo\",  " );
            	 sql.append(" TO_CHAR(invoice_back_dat, 'YYYY/MM/DD') AS \"invoiceBackDat\",   " );
            	 sql.append(" invoice_interval AS \"invoiceInterval\",  " );
            	 sql.append(" run_type AS \"runType\",   " );
            	 sql.append(" run_at AS \"runAt\",   " );
            	 sql.append(" run_bill_type AS \"runBillType\",   " );
            	 sql.append(" run_bill_day AS \"runBillDay\",  " );
            	 sql.append(" TO_CHAR(run_start_dat, 'YYYY/MM/DD') AS \"runStartDat\",   " );
            	 sql.append(" TO_CHAR(run_end_dat, 'YYYY/MM/DD') AS \"runEndDat\",   " );
            	 sql.append(" auto_assign_flag AS \"autoAssignFlag\",   " );
            	 sql.append(" last_update_by AS \"lastUpdateBy\",  " );
            	 sql.append(" TO_CHAR(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS \"lastUpdateDtm\",  " );
            	 sql.append(" order_level AS \"orderLevel\",   " );
            	 sql.append(" priority_no AS \"priorityNo\",  " );
            	 sql.append(" blacklist_type AS \"blacklistType\",   " );
            	 sql.append(" blacklist_subtype AS \"blacklistSubtype\",   " );
            	 sql.append(" letter_level AS \"letterLevel\",  " );
            	 sql.append(" letter_address_type AS \"letterAddressType\",   " );
            	 sql.append(" TO_CHAR(letter_dat, 'YYYY/MM/DD') AS \"letterDat\",   " );
            	 sql.append(" letter_payment_due AS \"letterPaymentDue\",   " );
            	 sql.append(" province_list AS \"provinceList\",   " );
            	 sql.append(" tp_debt_type AS \"tpDebtType\",  " );
            	 sql.append(" value_segment_list AS \"valueSegmentList\",   " );
            	 sql.append(" order_type AS \"orderType\",   " );
            	 sql.append(" reason_code_list AS \"reasonCodeList\",   " );
            	 sql.append(" TO_CHAR(df_dat_from, 'YYYY/MM/DD') AS \"dfDatFrom\",  " );
            	 sql.append(" TO_CHAR(df_dat_to, 'YYYY/MM/DD') AS \"dfDatTo\",   " );
            	 sql.append(" blacklist_dat_flag AS \"blacklistDatFlag\",  " );
            	 sql.append(" TO_CHAR(blacklist_dat_from, 'YYYY/MM/DD') AS \"blacklistDatFrom\",   " );
            	 sql.append(" TO_CHAR(blacklist_dat_to, 'YYYY/MM/DD') AS \"blacklistDatTo\",  " );
            	 sql.append(" ca_status AS \"caStatus\",   " );
            	 sql.append(" TO_CHAR(ca_inactive_dat_from, 'YYYY/MM/DD') AS \"caInactiveDatFrom\",   " );
            	 sql.append(" TO_CHAR(ca_inactive_dat_to, 'YYYY/MM/DD') AS \"caInactiveDatTo\",  " );
            	 sql.append(" thai_letter_flag AS \"thaiLetterFlag\",   " );
            	 sql.append(" inc_envelope_flag AS \"incEnvelopeFlag\",   " );
            	 sql.append(" template_id_ctype AS \"templateIdCtype\",   " );
            	 sql.append(" super_deal_flag AS \"superDealFlag\",   " );
            	 sql.append(" fix_company_type_flag AS \"fixCompanyTypeFlag\",   " );
            	 sql.append(" device_discount_from AS \"deviceDiscountFrom\",   " );
            	 sql.append(" device_discount_to AS \"deviceDiscountTo\",   " );
            	 sql.append(" create_by AS \"createBy\",   " );
            	 sql.append(" ca_debt_mny_from AS \"caDebtMnyFrom\",   " );
            	 sql.append(" ca_debt_mny_to AS \"caDebtMnyTo\",  " );
            	 sql.append(" gen_detail_rpt_flag AS \"genDetailRptFlag\",   " );
            	 sql.append(" bill_system_list AS \"billSystemList\",   " );
            	 sql.append(" auto_sms_flag AS \"autoSmsFlag\",   " );
            	 sql.append(" ca_device_discount_from AS \"caDeviceDiscountFrom\",   " );
            	 sql.append(" ca_device_discount_to AS \"caDeviceDiscountTo\",  " );
            	 sql.append(" cpe_brand_list AS \"cpeBrandList\",   " );
            	 sql.append(" cpe_flag AS \"cpeFlag\",   " );
            	 sql.append(" ivr_robot_flag AS \"ivrRobotFlag\",   " );
            	 sql.append(" cpe_penalty_flag AS \"cpePenaltyFlag\",   " );
            	 sql.append(" cpe_penalty_amount AS \"cpePenaltyAmount\",  " );
            	 sql.append(" bundling_flag AS \"bundlingFlag\",   " );
            	 sql.append(" ca_amount_flag AS \"caAmountFlag\",   " );
            	 sql.append(" ca_amount AS \"caAmount\"   " );
            	 sql.append("  FROM dcc_criteria_master " );
            	 sql.append("  where 1=1 " );
            	 
            	 if (StringUtils.isNotEmpty(modeId)) {
                     sql.append("   AND  mode_id = :modeId  ");
                 }
            	  if (criteriaId != null) {
                      sql.append("   AND criteria_id = :criteriaId  ");
                  }
                  if (StringUtils.isNotEmpty(description)) {
                      sql.append("   AND CRITERIA_DESCRIPTION LIKE :description  ");
                  }
                  
                
            }
        } catch (Exception e) {
            log.error("Exception searchDataQuerySql : {}", e.getMessage(), e);
            throw e;
        }
        return sql;
    }
	
	private Query searchDataQueryParam(Query query, String modeId, Long criteriaId, String description) throws Exception {
        try {
            if (query != null) {
	           
	            if (StringUtils.isNotBlank(modeId)) {
	                query.setParameter("modeId", modeId);
	            }  
	            if (criteriaId != null) {
	                query.setParameter("criteriaId", criteriaId);
	            }  
	            if (StringUtils.isNotBlank(description)) {
	                query.setParameter("description", description);
	            }  
            }
        } catch (Exception e) {
            log.error("Exception searchDataQueryParam : {}", e.getMessage(), e);
            throw e;
        }
        return query;
    }

}
