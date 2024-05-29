package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMaster;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMasterId;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterDto;

@Repository
public interface DMSEM004CriteriaMasterRepo extends JpaRepository<DccCriteriaMaster, DccCriteriaMasterId> {

	@Query(value = "  SELECT criteria_id AS criteriaId,   "
			+ "       criteria_type AS criteriaType,    "
			+ "       criteria_description AS criteriaDescription,   "
			+ "       company_code AS companyCode,  "
			+ "       collection_segment_list AS collectionSegmentList,   "
			+ "       payment_type_list AS paymentTypeList,   "
			+ "       bill_cycle_list AS billCycleList,  "
			+ "       region_list AS regionList,   "
			+ "       mobile_status_list AS mobileStatusList,   "
			+ "       product_group_list AS productGroupList,   "
			+ "       cate_subcate_list AS cateSubcateList,  "
			+ "       ba_status_list AS baStatusList,   "
			+ "       min_invoice_mny AS minInvoiceMny,   "
			+ "       invoice_count AS invoiceCount,  "
			+ "       debt_mny_from AS debtMnyFrom,   "
			+ "       debt_mny_to AS debtMnyTo,   "
			+ "       ar_mny_from AS arMnyFrom,   "
			+ "       ar_mny_to AS arMnyTo,  "
			+ "       debt_age_from AS debtAgeFrom,   "
			+ "       debt_age_to AS debtAgeTo,   "
			+ "       credit_term_flag AS creditTermFlag,   "
			+ "       status_age_from AS statusAgeFrom,   "
			+ "       status_age_to AS statusAgeTo,   "
			+ "       service_age_from AS serviceAgeFrom,   "
			+ "       service_age_to AS serviceAgeTo,  "
			+ "       TO_CHAR(first_ar_dat_from, 'YYYY/MM/DD') AS firstArDatFrom,   "
			+ "       TO_CHAR(first_ar_dat_to, 'YYYY/MM/DD') AS firstArDatTo,  "
			+ "       TO_CHAR(ba_inactive_dat_from, 'YYYY/MM/DD') AS baInactiveDatFrom,   "
			+ "       TO_CHAR(ba_inactive_dat_to, 'YYYY/MM/DD') AS baInactiveDatTo,  "
			+ "       status_reason_list AS statusReasonList,   "
			+ "       action_reason_list AS actionReasonList,   "
			+ "       nego_flag AS negoFlag,   "
			+ "       exempt_flag AS exemptFlag,   "
			+ "       max_account AS maxAccount,   "
			+ "       max_amount AS maxAmount,  "
			+ "       assign_duration AS assignDuration,   "
			+ "       campaign_code AS campaignCode,  "
			+ "       agent_type AS agentType,   "
			+ "       assign_type AS assignType,    "
			+ "       assign_job AS assignJob,   "
			+ "       due_day AS dueDay,   "
			+ "       call_type AS callType,   "
			+ "       message_id AS messageId,   "
			+ "       max_call AS maxCall,   "
			+ "       max_redial AS maxRedial,  "
			+ "       template_id AS templateId,   "
			+ "       TO_CHAR(check_dat_from, 'YYYY/MM/DD') AS checkDatFrom,   "
			+ "       TO_CHAR(check_dat_to, 'YYYY/MM/DD') AS checkDatTo,  "
			+ "       TO_CHAR(invoice_back_dat, 'YYYY/MM/DD') AS invoiceBackDat,   "
			+ "       invoice_interval AS invoiceInterval,  "
			+ "       run_type AS runType,   "
			+ "       run_at AS runAt,   "
			+ "       run_bill_type AS runBillType,   "
			+ "       run_bill_day AS runBillDay,  "
			+ "       TO_CHAR(run_start_dat, 'YYYY/MM/DD') AS runStartDat,   "
			+ "       TO_CHAR(run_end_dat, 'YYYY/MM/DD') AS runEndDat,   "
			+ "       auto_assign_flag AS autoAssignFlag,   "
			+ "       last_update_by AS lastUpdateBy,  "
			+ "       TO_CHAR(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDtm,  "
			+ "       order_level AS orderLevel,   "
			+ "       priority_no AS priorityNo,  "
			+ "       blacklist_type AS blacklistType,   "
			+ "       blacklist_subtype AS blacklistSubtype,   "
			+ "       letter_level AS letterLevel,  "
			+ "       letter_address_type AS letterAddressType,   "
			+ "       TO_CHAR(letter_dat, 'YYYY/MM/DD') AS letterDat,   "
			+ "       letter_payment_due AS letterPaymentDue,   "
			+ "       province_list AS provinceList,   "
			+ "       tp_debt_type AS tpDebtType,  "
			+ "       value_segment_list AS valueSegmentList,   "
			+ "       order_type AS orderType,   "
			+ "       reason_code_list AS reasonCodeList,   "
			+ "       TO_CHAR(df_dat_from, 'YYYY/MM/DD') AS dfDatFrom,  "
			+ "       TO_CHAR(df_dat_to, 'YYYY/MM/DD') AS dfDatTo,   "
			+ "       blacklist_dat_flag AS blacklistDatFlag,  "
			+ "       TO_CHAR(blacklist_dat_from, 'YYYY/MM/DD') AS blacklistDatFrom,   "
			+ "       TO_CHAR(blacklist_dat_to, 'YYYY/MM/DD') AS blacklistDatTo,  "
			+ "       ca_status AS caStatus,   "
			+ "       TO_CHAR(ca_inactive_dat_from, 'YYYY/MM/DD') AS caInactiveDatFrom,   "
			+ "       TO_CHAR(ca_inactive_dat_to, 'YYYY/MM/DD') AS caInactiveDatTo,  "
			+ "       thai_letter_flag AS thaiLetterFlag,   "
			+ "       inc_envelope_flag AS incEnvelopeFlag,   "
			+ "       template_id_ctype AS templateIdCtype,   "
			+ "       super_deal_flag AS superDealFlag,   "
			+ "       fix_company_type_flag AS fixCompanyTypeFlag,   "
			+ "       device_discount_from AS deviceDiscountFrom,   "
			+ "       device_discount_to AS deviceDiscountTo,   "
			+ "       create_by AS createBy,   "
			+ "       ca_debt_mny_from AS caDebtMnyFrom,   "
			+ "       ca_debt_mny_to AS caDebtMnyTo,  "
			+ "       gen_detail_rpt_flag AS genDetailRptFlag,   "
			+ "       bill_system_list AS billSystemList,   "
			+ "       auto_sms_flag AS autoSmsFlag,   "
			+ "       ca_device_discount_from AS caDeviceDiscountFrom,   "
			+ "       ca_device_discount_to AS caDeviceDiscountTo,  "
			+ "       cpe_brand_list AS cpeBrandList,   "
			+ "       cpe_flag AS cpeFlag,   "
			+ "       ivr_robot_flag AS ivrRobotFlag,   "
			+ "       cpe_penalty_flag AS cpePenaltyFlag,   "
			+ "       cpe_penalty_amount AS cpePenaltyAmount,  "
			+ "       bundling_flag AS bundlingFlag,   "
			+ "       ca_amount_flag AS caAmountFlag,   "
			+ "       ca_amount AS caAmount   "
			+ " FROM dcc_criteria_master "
			+ " where mode_id = :modeId  "
			+ " AND  criteria_id = :criteriaId "
			+ " AND  CRITERIA_DESCRIPTION LIKE :description "
			+ " order by last_update_dtm desc ", nativeQuery = true)
	DMSEM004CriteriaMasterDto  SerachData(@Param("modeId") String modeId,
			@Param("criteriaId") Long criteriaId,
			@Param("description") String description );
	
	@Modifying
	@Query(value = " update dcc_criteria_master set run_end_dat = trunc(SYSDATE), "
    		+ "     last_update_by = :lastUpdateBy , last_update_dtm = sysdate, blacklist_dat_flag = :blacklistDatFlag , "
    		+ "     blacklist_dat_from = to_date(:blacklistDatFrom,'YYYY/MM/DD'), blacklist_dat_to = to_date(:blacklistDatTo,'YYYY/MM/DD') "
    		+ "     where mode_id = :modeId "
    		+ "		and criteria_id = :criteriaId "
    		+ "		and criteria_type = :criteriaType " , nativeQuery = true)
    void updateCriteriaInfo( @Param("lastUpdateBy") String lastUpdateBy, 
                       @Param("blacklistDatFlag") String blacklistDatFlag, 
                       @Param("blacklistDatFrom") String blacklistDatFrom, 
                       @Param("blacklistDatTo") String blacklistDatTo, 
                       @Param("modeId") String modeId, 
                       @Param("criteriaId") Long criteriaId, 
                       @Param("criteriaType") String criteriaType);
	
	@Modifying
	@Query(value = " delete from dcc_criteria_master  where criteriaId = :criteriaId and mode_id = :modeId " , nativeQuery = true)
    void deleteCriteriaInfo(@Param("modeId")String modeId, @Param("criteriaId") Long criteriaId);
}
