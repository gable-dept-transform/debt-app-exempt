package th.co.ais.mimo.debt.exempt.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.dto.GetRefAssignDto;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMaster;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMasterId;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004GetBaByMobileNumDto;

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
	@Query(value = " delete from dcc_criteria_master  where criteria_id = :criteriaId and mode_id = :modeId " , nativeQuery = true)
    void deleteCriteriaInfo(@Param("modeId")String modeId, @Param("criteriaId") Long criteriaId);
	
	@Query(value = " SELECT mode_id AS modeId, "
			+ "       preassign_id AS preassignId, "
			+ "       criteria_id AS criteriaId, "
			+ "       criteria_type AS criteriaType, "
			+ "       preassign_dat AS preassignDat,"
			+ "       assign_id AS assignId, "
			+ "       assign_dat AS assignDat, "
			+ "       assign_status AS assignStatus, "
			+ "       unassign_dat AS unassignDat, "
			+ "       company_code AS companyCode, "
			+ "       collection_segment_list AS collectionSegmentList, "
			+ "       payment_type_list AS paymentTypeList,"
			+ "       bill_cycle_list AS billCycleList, "
			+ "       region_list AS regionList, "
			+ "       mobile_status_list AS mobileStatusList, "
			+ "       product_group_list AS productGroupList, "
			+ "       cate_subcate_list AS cateSubcateList, "
			+ "       ba_status_list AS baStatusList,"
			+ "       min_invoice_mny AS minInvoiceMny, "
			+ "       invoice_count AS invoiceCount, "
			+ "       debt_mny_from AS debtMnyFrom, "
			+ "       debt_mny_to AS debtMnyTo, "
			+ "       ar_mny_from AS arMnyFrom, "
			+ "       ar_mny_to AS arMnyTo, "
			+ "       debt_age_from AS debtAgeFrom,"
			+ "       debt_age_to AS debtAgeTo, "
			+ "       credit_term_flag AS creditTermFlag, "
			+ "       status_age_from AS statusAgeFrom, "
			+ "       status_age_to AS statusAgeTo, "
			+ "       service_age_from AS serviceAgeFrom, "
			+ "       service_age_to AS serviceAgeTo, "
			+ "       first_ar_dat_from AS firstArDatFrom, "
			+ "       first_ar_dat_to AS firstArDatTo, "
			+ "       ba_inactive_dat_from AS baInactiveDatFrom, "
			+ "       ba_inactive_dat_to AS baInactiveDatTo, "
			+ "       status_reason_list AS statusReasonList, "
			+ "       action_reason_list AS actionReasonList, "
			+ "       nego_flag AS negoFlag,"
			+ "       exempt_flag AS exemptFlag, "
			+ "       max_account AS maxAccount, "
			+ "       max_amount AS maxAmount, "
			+ "       assign_duration AS assignDuration, "
			+ "       assign_type AS assignType, "
			+ "       agent_type AS agentType, "
			+ "       campaign_code AS campaignCode,"
			+ "       due_day AS dueDay, "
			+ "       message_id AS messageId, "
			+ "       max_call AS maxCall, "
			+ "       max_redial AS maxRedial, "
			+ "       template_id AS templateId, "
			+ "       check_dat_from AS checkDatFrom, "
			+ "       check_dat_to AS checkDatTo, "
			+ "       invoice_back_dat AS invoiceBackDat, "
			+ "       invoice_interval AS invoiceInterval, "
			+ "       auto_assign_flag AS autoAssignFlag, "
			+ "       last_update_by AS lastUpdateBy, "
			+ "       last_update_dtm AS lastUpdateDtm, "
			+ "       order_level AS orderLevel, "
			+ "       collection_segment_all_flag AS collectionSegmentAllFlag, "
			+ "       mobile_status_all_flag AS mobileStatusAllFlag, "
			+ "       region_all_flag AS regionAllFlag,"
			+ "       product_group_all_flag AS productGroupAllFlag, "
			+ "       cate_subcate_all_flag AS cateSubcateAllFlag, "
			+ "       ba_status_all_flag AS baStatusAllFlag, "
			+ "       action_reason_all_flag AS actionReasonAllFlag,"
			+ "       status_reason_all_flag AS statusReasonAllFlag, "
			+ "       payment_type_all_flag AS paymentTypeAllFlag, "
			+ "       bill_cycle_all_flag AS billCycleAllFlag, "
			+ "       create_dtm AS createDtm, "
			+ "       run_db AS runDb, "
			+ "       '' AS criteriaDescription, "
			+ "       cancel_assign_flag AS cancelAssignFlag, "
			+ "       blacklist_type AS blacklistType, "
			+ "       blacklist_subtype AS blacklistSubtype, "
			+ "       letter_level AS letterLevel, "
			+ "       letter_address_type AS letterAddressType, "
			+ "       letter_dat AS letterDat, "
			+ "       letter_payment_due AS letterPaymentDue, "
			+ "       value_segment_list AS valueSegmentList, "
			+ "       order_type AS orderType, "
			+ "       reason_code_list AS reasonCodeList, "
			+ "       confirm_dat AS confirmDat, "
			+ "       process_status AS processStatus, "
			+ "       df_dat_from AS dfDatFrom, "
			+ "       df_dat_to AS dfDatTo, "
			+ "       ca_inactive_dat_from AS caInactiveDatFrom, "
			+ "       ca_inactive_dat_to AS caInactiveDatTo, "
			+ "       NULL AS callType, "
			+ "       assign_job AS assignJob, "
			+ "       province_list AS provinceList, "
			+ "       '' AS cnFlag, "
			+ "       company_type_list AS companyTypeList, "
			+ "       template_id_ctype AS templateIdCtype, "
			+ "       super_deal_flag AS superDealFlag,  "
			+ "       fix_company_type_flag AS fixCompanyTypeFlag,  "
			+ "       device_discount_from AS deviceDiscountFrom, "
			+ "       device_discount_to AS deviceDiscountTo,  "
			+ "       bill_system_list AS billSystemList,  "
			+ "       create_by AS createBy,  "
			+ "       gen_detail_rpt_flag AS genDetailRptFlag,  "
			+ "       cpe_brand_list AS cpeBrandList,  "
			+ "       cpe_flag AS cpeFlag,  "
			+ "       exempt_reason AS exemptReason,  "
			+ "       bundling_flag AS bundlingFlag,"
			+ "		(select reason_description from dcc_reason"
			+ "		where reason_type = 'EXEMPT_ADD' and reason_subtype like 'EXEMPT_ADD' AND REASON_CODE = ACTION_REASON_LIST) AS actionReasonListDesc ,"
			+ "			(select  distinct keyword_desc  from dcc_global_parameter where section_name = 'CRITERIA' and keyword = 'MODULE_CODE' AND KEYWORD_VALUE = assign_Type) AS assignTypeDesc  ,"
			+ "		(select distinct keyword_desc from dcc_global_parameter where section_name = 'CRITERIA' and keyword = 'EXEMPT_LEVEL' AND KEYWORD_VALUE = order_Level) AS orderLevelDesc "
			+ "  FROM dcc_criteria_history  "
			+ "  where mode_id = 'EM' and assign_id is not null  "
			+ "  and nvl(cancel_Assign_flag,'N') <> 'Y'  and  "
			+ "  (:assignId is null or assign_id = :assignId)  "
			+ "  order by assign_id desc " , nativeQuery = true)
	List<GetRefAssignDto> getRefAssignId(@Param("assignId") String assignId );
	
	@Modifying
	@Query(value = "INSERT INTO dcc_criteria_master ("
			+ "order_type, reason_code_list, value_segment_list, mode_id, criteria_id, criteria_type, "
			+ "criteria_description, company_code, collection_segment_list, payment_type_list, bill_cycle_list, "
			+ "region_list, mobile_status_list, product_group_list, cate_subcate_list, ba_status_list, min_invoice_mny, "
			+ "invoice_count, debt_mny_from, debt_mny_to, ar_mny_from, ar_mny_to, debt_age_from, debt_age_to, "
			+ "credit_term_flag, status_age_from, status_age_to, service_age_from, service_age_to, first_ar_dat_from, "
			+ "first_ar_dat_to, ba_inactive_dat_from, ba_inactive_dat_to, status_reason_list, action_reason_list, nego_flag, "
			+ "exempt_flag, max_account, max_amount, assign_duration, campaign_code, agent_type, assign_type, assign_job, "
			+ "due_day, call_type, message_id, max_call, max_redial, template_id, check_dat_from, check_dat_to, "
			+ "invoice_back_dat, invoice_interval, run_type, run_at, run_bill_type, run_bill_day, run_start_dat, run_end_dat, "
			+ "auto_assign_flag, last_update_by, last_update_dtm, priority_no, order_level, blacklist_type, blacklist_subtype, "
			+ "letter_payment_due, letter_dat, letter_address_type, letter_level, province_list, tp_debt_type, df_dat_from, "
			+ "df_dat_to, blacklist_dat_flag, blacklist_dat_from, blacklist_dat_to, thai_letter_flag, inc_envelope_flag, "
			+ "unlock_mode_flag, cn_flag, company_type_list, message_id_fbb, template_id_ctype, super_deal_flag, "
			+ "fix_company_type_flag, device_discount_from, device_discount_to, create_by, gen_detail_rpt_flag, bill_system_list, "
			+ "auto_sms_flag, cpe_brand_list, cpe_flag, ivr_robot_flag, cpe_penalty_flag, cpe_penalty_amount, bundling_flag, "
			+ "ca_amount_flag, ca_amount, ca_inactive_dat_from, ca_inactive_dat_to) "
			+ "VALUES (:orderType, :reasonCodeList, NULL, :modeId, :criteriaId, :criteriaType, "
			+ ":criteriaDescription, :companyCode, :collectionSegmentList, NULL, :billCycleList, "
			+ ":regionList, :mobileStatusList, NULL, :cateSubcateList, :baStatusList, 0, 0, 0, 0, "
			+ ":arMnyFrom, :arMnyTo, 0, 0, NULL, 0, 0, 0, 0, NULL, NULL, TO_DATE(:baInactiveDatFrom,'DD/MM/YYYY'), "
			+ "TO_DATE(:baInactiveDatTo,'DD/MM/YYYY'), NULL, :actionReasonList, 'S', NULL, 0, 0, :assignDuration, "
			+ "NULL, :agentType, :assignType, :assignJob, 0, :callType, NULL, 0, 0, NULL, TO_DATE(:checkDatFrom,'DD/MM/YYYY'), "
			+ "TO_DATE(:checkDatTo,'DD/MM/YYYY'), NULL, 0, 'O', :runAt, NULL, 0, TO_DATE(:runStartDat,'DD/MM/YYYY'), "
			+ "TO_DATE(:runEndDat,'DD/MM/YYYY'), :autoAssignFlag, :lastUpdateBy, sysdate, 0, :orderLevel, :blacklistType, "
			+ ":blacklistSubtype, 0, NULL, NULL, NULL, :provinceList, :tbDebtType, TO_DATE(:dfDatFrom,'DD/MM/YYYY'), "
			+ "TO_DATE(:dfDatTo,'DD/MM/YYYY'), NULL, NULL, NULL, 'Y', 'N', 'N', NULL, 'ALL', NULL, NULL, NULL, NULL, 0, 0, :createBy, "
			+ "NULL, NULL, 'N', NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, TO_DATE(:caInactiveDatFrom,'DD/MM/YYYY'), "
			+ "TO_DATE(:caInactiveDatTo,'DD/MM/YYYY'))", nativeQuery = true)
	void insertCriteriaMaster(@Param("orderType") String orderType, @Param("reasonCodeList") String reasonCodeList,
			@Param("modeId") String modeId, @Param("criteriaId") Long criteriaId,
			@Param("criteriaType") String criteriaType, @Param("criteriaDescription") String criteriaDescription,
			@Param("companyCode") String companyCode, @Param("collectionSegmentList") String collectionSegmentList,
			@Param("billCycleList") String billCycleList, @Param("regionList") String regionList,
			@Param("mobileStatusList") String mobileStatusList, @Param("cateSubcateList") String cateSubcateList,
			@Param("baStatusList") String baStatusList, @Param("arMnyFrom") BigDecimal arMnyFrom,
			@Param("arMnyTo") BigDecimal arMnyTo, @Param("baInactiveDatFrom") String baInactiveDatFrom,
			@Param("baInactiveDatTo") String baInactiveDatTo, @Param("actionReasonList") String actionReasonList,
			@Param("assignDuration") Long assignDuration, @Param("agentType") String agentType,
			@Param("assignType") String assignType, @Param("assignJob") String assignJob,
			@Param("callType") String callType, @Param("checkDatFrom") String checkDatFrom,
			@Param("checkDatTo") String checkDatTo, @Param("runAt") String runAt,
			@Param("runStartDat") String runStartDat, @Param("runEndDat") String runEndDat,
			@Param("autoAssignFlag") String autoAssignFlag, @Param("lastUpdateBy") String lastUpdateBy,
			@Param("orderLevel") String orderLevel, @Param("blacklistType") String blacklistType,
			@Param("blacklistSubtype") String blacklistSubtype, @Param("provinceList") String provinceList,
			@Param("tbDebtType") String tbDebtType, @Param("dfDatFrom") String dfDatFrom,
			@Param("dfDatTo") String dfDatTo, @Param("createBy") String createBy,
			@Param("caInactiveDatFrom") String caInactiveDatFrom, @Param("caInactiveDatTo") String caInactiveDatTo);
	
	@Modifying
	@Query(
		    value = "UPDATE dcc_criteria_master " +
		            "SET criteria_description = :criteriaDescription, " +
		            "    company_code = :companyCode, " +
		            "    collection_segment_list = :collectionSegmentList, " +
		            "    bill_cycle_list = :billCycleList, " +
		            "    region_list = :regionList, " +
		            "    mobile_status_list = :mobileStatusList, " +
		            "    cate_subcate_list = :cateSubcateList, " +
		            "    ba_status_list = :baStatusList, " +
		            "    ar_mny_from = :arMnyFrom, " +
		            "    ar_mny_to = :arMnyTo, " +
		            "    ba_inactive_dat_from = TO_DATE(:baInactiveDatFrom,'DD/MM/YYYY'), " +
		            "    ba_inactive_dat_to = TO_DATE(:baInactiveDatTo,'DD/MM/YYYY'), " +
		            "    action_reason_list = :actionReasonList, " +
		            "    assign_duration = :assignDuration, " +
		            "    agent_type = :agentType, " +
		            "    assign_type = :assignType, " +
		            "    assign_job = :assignJob, " +
		            "    check_dat_from = TO_DATE(:checkDatFrom,'DD/MM/YYYY'), " +
		            "    check_dat_to = TO_DATE(:checkDatTo,'DD/MM/YYYY') " +
		            "WHERE mode_id = :modeId " +
		            "  AND criteria_id = :criteriaId " +
		            "  AND criteria_type = :criteriaType",
		    nativeQuery = true
		)
		void updateCriteriaMaster(
		    @Param("criteriaDescription") String criteriaDescription,
		    @Param("companyCode") String companyCode,
		    @Param("collectionSegmentList") String collectionSegmentList,
		    @Param("billCycleList") String billCycleList,
		    @Param("regionList") String regionList,
		    @Param("mobileStatusList") String mobileStatusList,
		    @Param("cateSubcateList") String cateSubcateList,
		    @Param("baStatusList") String baStatusList,
		    @Param("arMnyFrom") BigDecimal arMnyFrom,
		    @Param("arMnyTo") BigDecimal arMnyTo,
		    @Param("baInactiveDatFrom") String baInactiveDatFrom,
		    @Param("baInactiveDatTo") String baInactiveDatTo,
		    @Param("actionReasonList") String actionReasonList,
		    @Param("assignDuration") Long assignDuration,
		    @Param("agentType") String agentType,
		    @Param("assignType") String assignType,
		    @Param("assignJob") String assignJob,
		    @Param("checkDatFrom") String checkDatFrom,
		    @Param("checkDatTo") String checkDatTo,
		    @Param("modeId") String modeId,
		    @Param("criteriaId") Long criteriaId,
		    @Param("criteriaType") String criteriaType
		);

	
	@Query(value = "select nvl(max(criteria_id),0) from dcc_criteria_master where mode_id = :modeId", nativeQuery = true)
	Long getMaxDccCriteriaId(@Param("modeId") String modeId);
	
	@Query(value = "select nvl(max(preassign_id), to_char(sysdate,'YYMM')||'0000') from dcc_criteria_history "
			+ "where mode_id = 'EM' and preassign_id like to_char(sysdate,'YYMM') || '%'", nativeQuery = true)
	Long getMaxPreAssignId();
	
	@Query(value = "SELECT"
			+ "	bill_accnt_num AS \"billAccNum\","
			+ "	service_num AS \"mobileNum\" "
			+ "FROM "
			+ "	("
			+ "	SELECT"
			+ "		bill_accnt_num,"
			+ "		service_num,"
			+ "		x_status_date,"
			+ "		max(a.x_status_date) OVER(PARTITION BY service_num) max_date "
			+ "	FROM"
			+ "		account_has_mobile a,"
			+ "		account ba "
			+ "	WHERE"
			+ "		service_num = :dccServiceNum "
			+ "		AND a.bill_accnt_num = ba.ou_num "
			+ "		AND ba.cust_stat_cd = 'Active' "
			+ "		AND (a.STATUS_CD = 'Active' "
			+ "			OR a.STATUS_CD LIKE 'Suspend%') "
			+ "	ORDER BY"
			+ "		a.x_status_date DESC, "
			+ "		a.last_upd DESC)"
			+ "WHERE "
			+ "	x_status_date = max_date "
			+ "	AND rownum <= 1", nativeQuery = true)
	DMSEM004GetBaByMobileNumDto getBaAccByMobileNum(@Param("dccServiceNum")String dccServiceNum);
	
}
