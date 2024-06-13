package th.co.ais.mimo.debt.exempt.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import th.co.ais.mimo.debt.exempt.dao.DMSEM004CriteriaMasterDao;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMaster;
import th.co.ais.mimo.debt.exempt.entity.DccCriteriaMasterId;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004GetBaByMobileNumDto;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdReq;
import th.co.ais.mimo.debt.exempt.repo.DMSEM004CriteriaMasterRepo;
import th.co.ais.mimo.debt.exempt.utils.SqlUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class DMSEM004CriteriaMasterDaoImpl implements DMSEM004CriteriaMasterDao {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	DMSEM004CriteriaMasterRepo criteriaMasterRepo;


	@Override
	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception {
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
				sql.append(" invoice_count AS \"invoiceCount\",  ");
				sql.append(" debt_mny_from AS \"debtMnyFrom\",   ");
				sql.append(" debt_mny_to AS \"debtMnyTo\",   ");
				sql.append(" ar_mny_from AS \"arMnyFrom\",   ");
				sql.append(" ar_mny_to AS \"arMnyTo\",  ");
				sql.append(" debt_age_from AS \"debtAgeFrom\",   ");
				sql.append(" debt_age_to AS \"debtAgeTo\",   ");
				sql.append(" credit_term_flag AS \"creditTermFlag\",   ");
				sql.append(" status_age_from AS \"statusAgeFrom\",   ");
				sql.append(" status_age_to AS \"statusAgeTo\",   ");
				sql.append(" service_age_from AS \"serviceAgeFrom\",   ");
				sql.append(" service_age_to AS \"serviceAgeTo\",  ");
				sql.append(" first_ar_dat_from AS \"firstArDatFrom\",   ");
				sql.append(" first_ar_dat_to AS \"firstArDatTo\",  ");
				sql.append(" ba_inactive_dat_from AS \"baInactiveDatFrom\",   ");
				sql.append(" ba_inactive_dat_to AS \"baInactiveDatTo\",  ");
				sql.append(" status_reason_list AS \"statusReasonList\",   ");
				sql.append(" action_reason_list AS \"actionReasonList\",   ");
				sql.append(" nego_flag AS \"negoFlag\",   ");
				sql.append(" exempt_flag AS \"exemptFlag\",   ");
				sql.append(" max_account AS \"maxAccount\",   ");
				sql.append(" max_amount AS \"maxAmount\",  ");
				sql.append(" assign_duration AS \"assignDuration\",   ");
				sql.append(" campaign_code AS \"campaignCode\",  ");
				sql.append(" agent_type AS \"agentType\",   ");
				sql.append(" assign_type AS \"assignType\",    ");
				sql.append(" assign_job AS \"assignJob\",   ");
				sql.append(" due_day AS \"dueDay\",   ");
				sql.append(" call_type AS \"callType\",   ");
				sql.append(" message_id AS \"messageId\",   ");
				sql.append(" max_call AS \"maxCall\",   ");
				sql.append(" max_redial AS \"maxRedial\",  ");
				sql.append(" template_id AS \"templateId\",   ");
				sql.append(" check_dat_from AS \"checkDatFrom\",   ");
				sql.append(" check_dat_to AS \"checkDatTo\",  ");
				sql.append(" invoice_back_dat AS \"invoiceBackDat\",   ");
				sql.append(" invoice_interval AS \"invoiceInterval\",  ");
				sql.append(" run_type AS \"runType\",   ");
				sql.append(" run_at AS \"runAt\",   ");
				sql.append(" run_bill_type AS \"runBillType\",   ");
				sql.append(" run_bill_day AS \"runBillDay\",  ");
				sql.append(" run_start_dat AS \"runStartDat\",   ");
				sql.append(" run_end_dat AS \"runEndDat\",   ");
				sql.append(" auto_assign_flag AS \"autoAssignFlag\",   ");
				sql.append(" last_update_by AS \"lastUpdateBy\",  ");
				sql.append(" last_update_dtm AS \"lastUpdateDtm\",  ");
				sql.append(" order_level AS \"orderLevel\",   ");
				sql.append(" priority_no AS \"priorityNo\",  ");
				sql.append(" blacklist_type AS \"blacklistType\",   ");
				sql.append(" blacklist_subtype AS \"blacklistSubtype\",   ");
				sql.append(" letter_level AS \"letterLevel\",  ");
				sql.append(" letter_address_type AS \"letterAddressType\",   ");
				sql.append(" letter_dat AS \"letterDat\",   ");
				sql.append(" letter_payment_due AS \"letterPaymentDue\",   ");
				sql.append(" province_list AS \"provinceList\",   ");
				sql.append(" tp_debt_type AS \"tpDebtType\",  ");
				sql.append(" value_segment_list AS \"valueSegmentList\",   ");
				sql.append(" order_type AS \"orderType\",   ");
				sql.append(" reason_code_list AS \"reasonCodeList\",   ");
				sql.append(" df_dat_from AS \"dfDatFrom\",  ");
				sql.append(" df_dat_to AS \"dfDatTo\",   ");
				sql.append(" blacklist_dat_flag AS \"blacklistDatFlag\",  ");
				sql.append(" blacklist_dat_from AS \"blacklistDatFrom\",   ");
				sql.append(" blacklist_dat_to AS \"blacklistDatTo\",  ");
				sql.append(" ca_status AS \"caStatus\",   ");
				sql.append(" ca_inactive_dat_from AS \"caInactiveDatFrom\",   ");
				sql.append(" ca_inactive_dat_to AS \"caInactiveDatTo\",  ");
				sql.append(" thai_letter_flag AS \"thaiLetterFlag\",   ");
				sql.append(" inc_envelope_flag AS \"incEnvelopeFlag\",   ");
				sql.append(" template_id_ctype AS \"templateIdCtype\",   ");
				sql.append(" super_deal_flag AS \"superDealFlag\",   ");
				sql.append(" fix_company_type_flag AS \"fixCompanyTypeFlag\",   ");
				sql.append(" device_discount_from AS \"deviceDiscountFrom\",   ");
				sql.append(" device_discount_to AS \"deviceDiscountTo\",   ");
				sql.append(" create_by AS \"createBy\",   ");
				sql.append(" ca_debt_mny_from AS \"caDebtMnyFrom\",   ");
				sql.append(" ca_debt_mny_to AS \"caDebtMnyTo\",  ");
				sql.append(" gen_detail_rpt_flag AS \"genDetailRptFlag\",   ");
				sql.append(" bill_system_list AS \"billSystemList\",   ");
				sql.append(" auto_sms_flag AS \"autoSmsFlag\",   ");
				sql.append(" ca_device_discount_from AS \"caDeviceDiscountFrom\",   ");
				sql.append(" ca_device_discount_to AS \"caDeviceDiscountTo\",  ");
				sql.append(" cpe_brand_list AS \"cpeBrandList\",   ");
				sql.append(" cpe_flag AS \"cpeFlag\",   ");
				sql.append(" ivr_robot_flag AS \"ivrRobotFlag\",   ");
				sql.append(" cpe_penalty_flag AS \"cpePenaltyFlag\",   ");
				sql.append(" cpe_penalty_amount AS \"cpePenaltyAmount\",  ");
				sql.append(" bundling_flag AS \"bundlingFlag\",   ");
				sql.append(" ca_amount_flag AS \"caAmountFlag\",   ");
				sql.append(" ca_amount AS \"caAmount\",  ");
				sql.append(" (select  distinct keyword_desc  from dcc_global_parameter where section_name = 'CRITERIA' and keyword = 'MODULE_CODE' AND KEYWORD_VALUE = assign_Type) AS \"assignTypeDesc\"  ,");
				sql.append(" (select distinct keyword_desc from dcc_global_parameter where section_name = 'CRITERIA' and keyword = 'EXEMPT_LEVEL' AND KEYWORD_VALUE = order_Level) AS \"orderLevelDesc\"  ,");
				sql.append("(select reason_description from dcc_reason where reason_type = 'EXEMPT_ADD' and reason_subtype like 'EXEMPT_ADD' AND REASON_CODE = ACTION_REASON_LIST) AS \"actionReasonListDesc\" ");
				sql.append("  FROM dcc_criteria_master ");
				sql.append("  where 1=1 ");

				if (StringUtils.isNotEmpty(modeId)) {
					sql.append("   AND  mode_id = :modeId  ");
				}
				if (criteriaId != null) {
					sql.append("   AND criteria_id = :criteriaId  ");
				}
				if (StringUtils.isNotEmpty(description)) {
					sql.append("   AND CRITERIA_DESCRIPTION LIKE :description  ");
				}
				sql.append("  ORDER BY criteria_id DESC");

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

	@Override
	public void insertCriteriaMaster(InsertAssignIdReq req, Long newCriteriaId) throws Exception {
		try {
			//			criteriaMasterRepo.insertCriteriaMaster(req.getOrderType(), 
			//					req.getReasonCodeList(),
			//					req.getModeId(),
			//					newCriteriaId, 
			//					req.getCriteriaType(), 
			//					req.getCriteriaDescription(), 
			//					req.getCompanyCode(), 
			//					req.getCollectionSegmentList(),
			//					req.getBillCycleList(), 
			//					req.getRegionList(),
			//					req.getMobileStatusList(),
			//					req.getCateSubcateList(), 
			//					req.getBaStatusList(), 
			//					req.getArMnyFrom(), 
			//					req.getArMnyTo(), 
			//					req.getBaInactiveDatFrom(),
			//					req.getBaInactiveDatTo(), 
			//					req.getActionReasonList(),
			//					req.getAssignDuration(), 
			//					req.getAgentType(), 
			//					req.getAssignType(), 
			//					req.getAssignJob(), 
			//					null, // credit LIMIT(ยกเลิกแล้ว)
			//					req.getCheckDatFrom(),
			//					req.getCheckDatTo(), 
			//					req.getProcessDate(), 
			//					req.getEffectiveDate(), 
			//					req.getEffectiveEndDate(),
			//					req.getAutoAssignFlag(),
			//					req.getLastUpdateBy(),
			//					req.getOrderLevel(),
			//					req.getBlacklistType(),
			//					req.getBlacklistSubtype(),
			//					req.getProvinceList(), 
			//					null, // req.getTpDebtType()
			//					req.getDfDatFrom(), 
			//					req.getDfDatTo(), 
			//					req.getCreateBy(), 
			//					req.getCaInactiveDatFrom(),
			//					req.getCaInactiveDatTo());

			DccCriteriaMaster criteriaMaster = new DccCriteriaMaster();
			DccCriteriaMasterId criteriaMasterId = new DccCriteriaMasterId();

			criteriaMasterId.setModeId(req.getModeId());
			criteriaMasterId.setCriteriaId(newCriteriaId);
			criteriaMaster.setId(criteriaMasterId);

			criteriaMaster.setOrderType(req.getOrderType());
			criteriaMaster.setReasonCodeList(req.getReasonCodeList());

			criteriaMaster.setCriteriaType(req.getCriteriaType());
			criteriaMaster.setCriteriaDescription(req.getCriteriaDescription());
			criteriaMaster.setCompanyCode(req.getCompanyCode());
			criteriaMaster.setCollectionSegmentList(req.getCollectionSegmentList());
			criteriaMaster.setBillCycleList(req.getBillCycleList());
			criteriaMaster.setRegionList(req.getRegionList());
			criteriaMaster.setMobileStatusList(req.getMobileStatusList());
			criteriaMaster.setCateSubcateList(req.getCateSubcateList());
			criteriaMaster.setBaStatusList(req.getBaStatusList());
			criteriaMaster.setArMnyFrom(req.getArMnyFrom());
			criteriaMaster.setArMnyTo(req.getArMnyTo());
			criteriaMaster.setBaInactiveDatFrom(convertStringToDate(req.getBaInactiveDatFrom()));
			criteriaMaster.setBaInactiveDatTo(convertStringToDate(req.getBaInactiveDatTo()));
			criteriaMaster.setActionReasonList(req.getActionReasonList());
			criteriaMaster.setAssignDuration(req.getAssignDuration());
			criteriaMaster.setAgentType(req.getAgentType());
			criteriaMaster.setAssignType(req.getAssignType());
			criteriaMaster.setAssignJob(req.getAssignJob());
			criteriaMaster.setCallType(req.getCallType());
			criteriaMaster.setCheckDatFrom(convertStringToDate(req.getCheckDatFrom()));
			criteriaMaster.setCheckDatTo(convertStringToDate(req.getCheckDatTo()));
			criteriaMaster.setRunAt(req.getProcessDate());
			criteriaMaster.setRunStartDat(convertStringToDate(req.getEffectiveDate()));
			criteriaMaster.setRunEndDat(convertStringToDate(req.getEffectiveEndDate()));
			criteriaMaster.setAutoAssignFlag(req.getAutoAssignFlag());
			criteriaMaster.setLastUpdateBy(req.getLastUpdateBy());
			criteriaMaster.setOrderLevel(req.getOrderLevel());
			criteriaMaster.setBlacklistType(req.getBlacklistType());
			criteriaMaster.setBlacklistSubtype(req.getBlacklistSubtype());
			criteriaMaster.setProvinceList(req.getProvinceList());
			criteriaMaster.setDfDatFrom(convertStringToDate(req.getDfDatFrom()));
			criteriaMaster.setDfDatTo(convertStringToDate(req.getDfDatTo()));
			criteriaMaster.setCreateBy(req.getCreateBy());
			criteriaMaster.setCaInactiveDatFrom(convertStringToDate(req.getCaInactiveDatFrom()));
			criteriaMaster.setCaInactiveDatTo(convertStringToDate(req.getCaInactiveDatTo()));
			criteriaMaster.setLastUpdateDtm(new Date());
			criteriaMaster.setCompanyTypeList("ALL");
			criteriaMaster.setNegoFlag(req.getNegoFlag());
			criteriaMasterRepo.save(criteriaMaster);
		} catch (Exception e) {
			log.error("Exception insertCriteriaMaster : {}", e.getMessage(), e);
			throw e;
		}
	}

	private Date convertStringToDate(String dateStr) throws ParseException {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY");
		return formatter.parse(dateStr);
	}

	@Override
	public void updateCriteriaMaster(InsertAssignIdReq req) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql = updateCriteriaMasterQuerySql(sql, req.isSelectEfficetiveDate(), req.isSelectDuration());
			Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
			query = updateCriteriaMasterQueryParam(query, req.getCriteriaDescription(), req.getCompanyCode(), req.getCollectionSegmentList(), req.getBillCycleList(), req.getRegionList(), req.getMobileStatusList(), req.getCateSubcateList(), req.getBaStatusList(), req.getArMnyFrom(), req.getArMnyTo(), req.getBaInactiveDatFrom(), req.getBaInactiveDatTo(), req.getActionReasonList(), req.getAssignDuration(), req.getAgentType(), req.getAssignType(), req.getAssignJob(), req.getCheckDatFrom(), req.getCheckDatTo(), req.getModeId(), req.getCriteriaId(), req.getCriteriaType(), req.isSelectEfficetiveDate(), req.isSelectDuration(), req.getDfDatFrom(), req.getDfDatTo(), req.getProcessDate(), req.getCaInactiveDatFrom(), req.getCaInactiveDatTo(), req.getProvinceList(), req.getBlacklistType(),
					req.getBlacklistSubtype(), req.getOrderType(), req.getOrderLevel(), req.getAutoAssignFlag());
			query.executeUpdate();
		} catch (Exception e) {
			log.error("Exception insertCriteriaMaster : {}", e.getMessage(), e);
			throw e;
		}
	}

	private StringBuffer updateCriteriaMasterQuerySql(StringBuffer sql, boolean selectEfficetiveDate, boolean selectDuration) throws Exception {
		if (sql != null) {

			sql.append("UPDATE dcc_criteria_master SET ");
			sql.append("criteria_description = :criteriaDescription, ");
			sql.append("region_list = :regionList, ");
			sql.append("province_list = :provinceList, ");
			sql.append("BLACKLIST_TYPE = :blacklistType, ");
			sql.append("BLACKLIST_SUBTYPE = :blacklistSubtype, ");
			sql.append("agent_type = :agentType, ");

			sql.append("company_code = :companyCode, ");
			sql.append("collection_segment_list = :collectionSegmentList, ");
			sql.append("bill_cycle_list = :billCycleList, ");
			sql.append("mobile_status_list = :mobileStatusList, ");
			sql.append("cate_subcate_list = :cateSubcateList, ");
			sql.append("ba_status_list = :baStatusList, ");
			sql.append("ar_mny_from = :arMnyFrom, ");
			sql.append("ar_mny_to = :arMnyTo, ");

			sql.append("ba_inactive_dat_from = TO_DATE(:baInactiveDatFrom,'DD/MM/YYYY'), ");
			sql.append("ba_inactive_dat_to = TO_DATE(:baInactiveDatTo,'DD/MM/YYYY'), ");
			sql.append("check_dat_from = TO_DATE(:checkDatFrom,'DD/MM/YYYY'), ");
			sql.append("check_dat_to = TO_DATE(:checkDatTo,'DD/MM/YYYY'), ");
			sql.append("CA_INACTIVE_DAT_FROM = TO_DATE(:caInactiveDatFrom,'DD/MM/YYYY'), ");
			sql.append("CA_INACTIVE_DAT_TO = TO_DATE(:caInactiveDatTo,'DD/MM/YYYY'), ");

			sql.append("order_type = :orderType, ");
			sql.append("assign_type = :assignType, ");
			sql.append("order_level = :orderLevel, ");
			sql.append("assign_job = :assignJob, ");
			sql.append("action_reason_list = :actionReasonList, ");
			if (selectEfficetiveDate) {
				sql.append("df_dat_from = TO_DATE(:dfDatFrom,'DD/MM/YYYY'), ");
				sql.append("df_dat_to = TO_DATE(:dfDatTo,'DD/MM/YYYY'),");
			}
			if (selectDuration) {
				sql.append("assign_duration = :assignDuration, ");
			}
			sql.append("run_at = :runAt, ");
			sql.append("auto_assign_flag = :autoAssignFlag ");

			sql.append("WHERE mode_id = :modeId ");
			sql.append("AND criteria_id = :criteriaId ");
			sql.append("AND criteria_type = :criteriaType");
		}
		return sql;
	}

	private Query updateCriteriaMasterQueryParam(Query query, String criteriaDescription, String companyCode, String collectionSegmentList, String billCycleList, String regionList, String mobileStatusList, String cateSubcateList, String baStatusList, BigDecimal arMnyFrom, BigDecimal arMnyTo, String baInactiveDatFrom, String baInactiveDatTo, String actionReasonList, Long assignDuration, String agentType, String assignType, String assignJob, String checkDatFrom, String checkDatTo, String modeId, Long criteriaId, String criteriaType, boolean selectEfficetiveDate, boolean selectDuration, String dfDatFrom, String dfDatTo, String runAt, String caInactiveDatFrom, String caInactiveDatTo, String provinceList, String blacklistType, String blacklistSubtype, String orderType, String orderLevel,
			String autoAssignFlag) throws Exception {
		try {
			if (query != null) {
				query.setParameter("criteriaDescription", criteriaDescription);
				query.setParameter("regionList", regionList);
				query.setParameter("provinceList", provinceList);
				query.setParameter("blacklistType", blacklistType);
				query.setParameter("blacklistSubtype", blacklistSubtype);
				query.setParameter("agentType", agentType);

				query.setParameter("companyCode", companyCode);
				query.setParameter("collectionSegmentList", collectionSegmentList);
				query.setParameter("billCycleList", billCycleList);
				query.setParameter("mobileStatusList", mobileStatusList);
				query.setParameter("cateSubcateList", cateSubcateList);
				query.setParameter("baStatusList", baStatusList);
				query.setParameter("arMnyFrom", arMnyFrom);
				query.setParameter("arMnyTo", arMnyTo);

				query.setParameter("baInactiveDatFrom", baInactiveDatFrom);
				query.setParameter("baInactiveDatTo", baInactiveDatTo);
				query.setParameter("caInactiveDatFrom", caInactiveDatFrom);
				query.setParameter("caInactiveDatTo", caInactiveDatTo);
				query.setParameter("checkDatFrom", checkDatFrom);
				query.setParameter("checkDatTo", checkDatTo);

				query.setParameter("orderType", orderType);
				query.setParameter("assignType", assignType);
				query.setParameter("assignJob", assignJob);
				query.setParameter("orderLevel", orderLevel);
				query.setParameter("actionReasonList", actionReasonList);

				if (selectEfficetiveDate) {
					query.setParameter("dfDatFrom", dfDatFrom);
					query.setParameter("dfDatTo", dfDatTo);
				}
				if (selectDuration) {
					query.setParameter("assignDuration", assignDuration);
				}
				query.setParameter("runAt", runAt);
				query.setParameter("AUTO_ASSIGN_FLAG", autoAssignFlag);

				query.setParameter("modeId", modeId);
				query.setParameter("criteriaId", criteriaId);
				query.setParameter("criteriaType", criteriaType);

			}
		} catch (Exception e) {
			log.error("Exception updateCriteriaMasterQueryParam : {}", e.getMessage(), e);
			throw e;
		}
		return query;
	}

	@Override
	public DMSEM004GetBaByMobileNumDto validateGetBillAccNumByMobileNum(String mobileNum) throws Exception {
		DMSEM004GetBaByMobileNumDto response = null;
		try {
			response = criteriaMasterRepo.getBaAccByMobileNum(mobileNum);
		} catch (Exception e) {
			log.error("Exception insertCriteriaMaster : {}", e.getMessage(), e);
			throw e;
		}
		return response;
	}
}
