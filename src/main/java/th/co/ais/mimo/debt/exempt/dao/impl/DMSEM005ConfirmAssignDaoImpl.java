package th.co.ais.mimo.debt.exempt.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.DMSEM005ConfirmAssignDao;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005QueryAssignDto;
import th.co.ais.mimo.debt.exempt.dto.DMSEM005SearchDataDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005QueryAssignRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoResponse;
import th.co.ais.mimo.debt.exempt.utils.SqlUtils;

@Component
public class DMSEM005ConfirmAssignDaoImpl implements DMSEM005ConfirmAssignDao {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DMSEM005SearchDataDto> searchData(DMSEM005SearchDataRequest request) throws ExemptException {
		log.info("SearchDataConfirmAssign request : {}", request);
		List<DMSEM005SearchDataDto> result = null;

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append("his.mode_id AS \"modeId\", ").append("his.preassign_id AS \"preAssignId\", ")
					.append("his.criteria_id AS \"criteriaId\", ").append("his.criteria_type AS \"criteriaType\", ")
					.append("his.preassign_dat AS \"preAssignDate\", ").append("his.company_code AS \"companyCode\", ")
					.append("his.collection_segment_list AS \"collectionSegment\", ")
					.append("his.bill_cycle_list AS \"billCycleList\", ").append("his.region_list AS \"region\", ")
					.append("his.mobile_status_list AS \"mobileStatusList\", ")
					.append("his.cate_subcate_list AS \"cateSubCateList\", ")
					.append("his.ba_status_list AS \"baStatusList\", ").append("his.ar_mny_from AS \"arBalanceFrom\", ")
					.append("his.ar_mny_to AS \"arBalanceTo\", ")
					.append("his.ba_inactive_dat_from AS \"baStatusDateFrom\", ")
					.append("his.ba_inactive_dat_to AS \"baStatusDateTo\", ")
					.append("his.action_reason_list AS \"exemptReasonList\", ")
					.append("his.nego_flag AS \"creditLimitSet\", ").append("his.assign_duration AS \"duration\", ")
					.append("his.assign_type AS \"exemptModule\", ").append("his.agent_type AS \"zipCode\", ")
					.append("his.check_dat_from AS \"mobileStatusDateFrom\", ")
					.append("his.check_dat_to AS \"mobileStatusDateTo\", ")
					.append("his.auto_assign_flag AS \"autoAssignFlag\", ")
					.append("his.order_level AS \"exemptLevel\", ")
					.append("mas.criteria_description AS \"criteriaDesc\", ")
					.append("his.blacklist_type AS \"aumphur\", ").append("his.blacklist_subtype AS \"tumbol\", ")
					.append("his.letter_address_type AS \"creditLimitFlag\", ")
					.append("his.order_type AS \"exemptAction\", ")
					.append("his.reason_code_list AS \"creditLimitReason\", ")
					.append("his.process_status AS \"processStatus\", ")
					.append("his.df_dat_from AS \"effectiveDate\", ").append("his.df_dat_to AS \"endDate\", ")
					.append("his.ca_inactive_dat_from AS \"registerDateFrom\", ")
					.append("his.ca_inactive_dat_to AS \"registerDateTo\", ")
					.append("his.assign_job AS \"exemptMode\", ").append("his.province_list AS \"province\", ")
					.append("his.assign_id AS \"assignId\", ").append("his.assign_dat AS \"assignDate\", ")
					.append("his.assign_status AS \"assignStatus\", ")
					.append("his.last_update_by AS \"lastUpdateBy\", ")
					.append("his.last_update_dtm AS \"lastUpdateDate\", ").append("his.confirm_dat AS \"confirmDate\" ")
					.append("FROM dcc_criteria_history his, dcc_criteria_master mas ")
					.append("WHERE his.criteria_id = mas.criteria_id AND his.mode_id = mas.mode_id ")
					.append("AND his.mode_id =  :modeId ");
			if (StringUtils.isNotBlank(request.getPreAssignId())) {
				sql.append("AND his.PREASSIGN_ID = :preAssignId ");
			}
//			         .append("AND his.ASSIGN_ID = :assignId ")

			sql.append("AND his.assign_id is null ").append("AND nvl(his.cancel_Assign_flag,'N') <> 'Y' ")
					.append("ORDER by his.Preassign_id  DESC");

			Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
			query.setParameter("modeId", request.getMode());
//			query.setParameter("assignId", request.getAssignId());
			if (StringUtils.isNotBlank(request.getPreAssignId())) {
				query.setParameter("preAssignId", request.getPreAssignId());
			}

			result = SqlUtils.parseResult(query.getResultList(), DMSEM005SearchDataDto.class);

		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

	@Override
	public List<DMSEM005QueryAssignDto> queryAssign(DMSEM005QueryAssignRequest request) throws ExemptException {
		log.info("queryExemptAssign request : {}", request);
		List<DMSEM005QueryAssignDto> result = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append(
					"mode_id AS \"modeId\" ,assign_id AS \"assignId\" , assign_dat AS \"assignDate\", assign_status AS \"assignStatus\" ")
					.append("FROM dcc_criteria_history ");
			if (StringUtils.isNotBlank(request.getModeId())) {
				sql.append("WHERE mode_id = :modeId ");
			}
			if (StringUtils.isNoneBlank(request.getAssignId())) {
				sql.append("AND assign_id = :assignId ");
			} else {
				sql.append("AND assign_id is not null ");
			}
			sql.append("AND nvl(cancel_Assign_flag,'N') <> 'Y' ").append("ORDER BY assign_id desc ");

			Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
			if (StringUtils.isNotBlank(request.getModeId())) {
				query.setParameter("modeId", request.getModeId());
			}
			if (StringUtils.isNoneBlank(request.getAssignId())) {
				query.setParameter("assignId", request.getAssignId());
			}
			result = SqlUtils.parseResult(query.getResultList(), DMSEM005QueryAssignDto.class);

		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

//	@Override
//	public DMSEM005UpdateExemptInfoResponse updateExemptInfo(DMSEM005UpdateExemptInfoRequest request)
//			throws ExemptException {
//		log.info("updateExemptInfo request : {}", request);
//		DMSEM005UpdateExemptInfoResponse result = new DMSEM005UpdateExemptInfoResponse();
//		try {
//			StringBuffer sql1 = new StringBuffer();
//			StringBuffer sql2 = new StringBuffer();
//			StringBuffer sql3 = new StringBuffer();
//			StringBuffer sql4 = new StringBuffer();
//			sql1.append("SELECT MODE_ID , PREASSIGN_ID , PREASSIGN_DAT FROM DCC_CRITERIA_HISTORY ")
//					.append("WHERE MODE_ID = :modeId AND PREASSIGN_ID = :preAssignId");
//			Query query = entityManager.createNativeQuery(sql1.toString(), Tuple.class);
//			query.setParameter("modeId", request.getModeId());
//			query.setParameter("preAssignId", request.getPreAssignId());
//			if (!query.getResultList().isEmpty() && AppConstant.FLAG_Y.equals(request.getConfirmAssignFlag())) {
//				sql2.append("SELECT prejob_id FROM from dcc_calendar_transaction ")
//						.append("WHERE mode_id = :modeId and prejob_id = :preAssignId and job_type = 'ASSIGN' ");
//				Query query2 = entityManager.createNativeQuery(sql2.toString(), Tuple.class);
//				query2.setParameter("modeId", request.getModeId());
//				query2.setParameter("preAssignId", request.getPreAssignId());
//				if (query2.getResultList().isEmpty()) {
//					sql3.append("SELECT ").append("'ASSIGN' AS job_type, ").append("TRUNC(SYSDATE) AS run_dat, ")
//							.append("criteria_id, ").append("mode_id, ").append("prejob_id, ").append("job_id, ")
//							.append("job_description, ").append("NULL AS execute_dtm, ").append("'N' AS pause_flag, ")
//							.append("'N' AS run_result_flag, ").append("'NOTHING' AS run_result_desc, ")
//							.append(":userName AS last_update_by, ").append("SYSDATE AS last_update_dtm, ")
//							.append("set_seq, ").append("priority_no, ").append("'Y' AS auto_job_flag ")
//							.append("FROM dcc_calendar_transaction ").append("WHERE mode_id = :modeId ")
//							.append("AND prejob_id = :preAssignId;");
//					Query query3 = entityManager.createNativeQuery(sql3.toString(), Tuple.class);
//					query3.setParameter("userName", request.getUsername());
//					query3.setParameter("modeId", request.getModeId());
//					query3.setParameter("preAssignId", request.getPreAssignId());
//					query3.getResultList();
//				}
//
//				sql4.append("UPDATE dcc_criteria_history ").append("SET confirm_dat = SYSDATE, ")
//						.append("last_update_by = :userName, ").append("last_update_dtm = SYSDATE ")
//						.append("WHERE mode_id = :modeId ").append("AND preassign_id = :preAssignId;");
//				Query query4 = entityManager.createNativeQuery(sql4.toString(), Tuple.class);
//				query4.setParameter("userName", request.getUsername());
//				query4.setParameter("modeId", request.getModeId());
//				query4.setParameter("preAssignId", request.getPreAssignId());
//				query4.getResultList();
//
//			} else {
//				result.setErrorMsg("Exempt Criteria History Data not found");
//			}
//
//		} catch (PersistenceException | IllegalArgumentException e) {
//			throw new ExemptException(AppConstant.FAIL, e.getMessage());
//		} finally {
//			SqlUtils.close(entityManager);
//		}
//		return result;
//	}

	public DMSEM005UpdateExemptInfoResponse updateExemptInfo(DMSEM005UpdateExemptInfoRequest request)
			throws ExemptException {
		log.info("updateExemptInfo request : {}", request);
		DMSEM005UpdateExemptInfoResponse result = new DMSEM005UpdateExemptInfoResponse();
		try {
			if (criteriaHistoryExists(request)) {
				if (calendarTransactionIsEmpty(request)) {
					insertIntoCalendarTransaction(request, entityManager);
				}
				updateCriteriaHistory(request, entityManager);
			} else {
				result.setErrorMsg("Exempt Criteria History Data not found");
			}
		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

	private boolean criteriaHistoryExists(DMSEM005UpdateExemptInfoRequest request) {
		String sql = "SELECT MODE_ID, PREASSIGN_ID, PREASSIGN_DAT FROM DCC_CRITERIA_HISTORY WHERE MODE_ID = :modeId AND PREASSIGN_ID = :preAssignId";
		Query query = entityManager.createNativeQuery(sql, Tuple.class);
		query.setParameter("modeId", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());
		return !query.getResultList().isEmpty();
	}

	private boolean calendarTransactionIsEmpty(DMSEM005UpdateExemptInfoRequest request) {
		String sql = "SELECT prejob_id FROM dcc_calendar_transaction WHERE mode_id = :modeId AND prejob_id = :preAssignId AND job_type = 'ASSIGN'";
		Query query = entityManager.createNativeQuery(sql, Tuple.class);
		query.setParameter("modeId", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());
		return query.getResultList().isEmpty();
	}

	private void insertIntoCalendarTransaction(DMSEM005UpdateExemptInfoRequest request, EntityManager entityManager) {
		String sql = "INSERT INTO dcc_calendar_transaction (job_type, run_dat, criteria_id, mode_id, prejob_id, job_id, job_description, execute_dtm, pause_flag, run_result_flag, run_result_desc, last_update_by, last_update_dtm, set_seq, priority_no, auto_job_flag) "
				+ "SELECT 'ASSIGN', TRUNC(SYSDATE), criteria_id, mode_id, prejob_id, job_id, job_description, NULL, 'N', 'N', 'NOTHING', :userName, SYSDATE, set_seq, priority_no, 'Y' "
				+ "FROM dcc_calendar_transaction WHERE mode_id = :modeId AND prejob_id = :preAssignId";
		Query query = entityManager.createNativeQuery(sql, Tuple.class);
		query.setParameter("userName", request.getUsername());
		query.setParameter("modeId", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());
		query.executeUpdate();
	}

	private void updateCriteriaHistory(DMSEM005UpdateExemptInfoRequest request, EntityManager entityManager) {
		String sql = "UPDATE dcc_criteria_history SET confirm_dat = SYSDATE, last_update_by = :userName, last_update_dtm = SYSDATE "
				+ "WHERE mode_id = :modeId AND preassign_id = :preAssignId";
		Query query = entityManager.createNativeQuery(sql, Tuple.class);
		query.setParameter("userName", request.getUsername());
		query.setParameter("modeId", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());
		query.executeUpdate();
	}

	@Override
	public DMSEM005UpdateExemptInfoResponse deleteInformation(DMSEM005UpdateExemptInfoRequest request)
			throws ExemptException {
		log.info("deleteInformation request: {}", request);
		DMSEM005UpdateExemptInfoResponse result = new DMSEM005UpdateExemptInfoResponse();
		try {

			Date preAssignDate = getPreAssignDate(request);
			if (preAssignDate == null) {
				return setErrorAndReturn(result, "Exempt Criteria History Data not found");
			}

			insertIntoTransactionBacklog(request, preAssignDate);
			deleteFromTempTransaction(request.getModeId(), request.getPreAssignId());
			updateCriteriaHistory(request.getModeId(), request.getPreAssignId(), request.getUsername());

			Character autoAssignFlag = this.getAutoAssignFlag(request.getModeId(), request.getPreAssignId());
			if (AppConstant.FLAG_Y.equals(autoAssignFlag.toString())) {
				deleteFromCalendarTransaction(request.getModeId(), request.getPreAssignId());
			}
			
			result.setModeId(request.getModeId());
			result.setPreAssignId(request.getPreAssignId());
		} catch (PersistenceException | IllegalArgumentException e) {
//			log.error("Error occurred during database operation", e);
			result.setErrorMsg(e.getMessage());
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

	private DMSEM005UpdateExemptInfoResponse setErrorAndReturn(DMSEM005UpdateExemptInfoResponse response,
			String errorMsg) {
		response.setErrorMsg(errorMsg);
		return response;
	}

	private Date getPreAssignDate(DMSEM005UpdateExemptInfoRequest request) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PREASSIGN_DAT FROM DCC_CRITERIA_HISTORY ")
				.append("WHERE MODE_ID = :mode AND PREASSIGN_ID = :preAssignId");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("mode", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());

		List<Date> resultList = query.getResultList();
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	private void insertIntoTransactionBacklog(DMSEM005UpdateExemptInfoRequest request, Date preAssignDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO dcc_transaction_backlog (mode_id, preassign_id, preassign_dat, assign_id, ").append(
				"assign_dat, input_reason, company_code, billing_acc_num, mobile_num, cust_acc_num, region_code, ")
				.append("last_update_by, last_update_dtm) ")
				.append("SELECT mode_id, preassign_id, :preAssignDate, null, null, :inputReason, company_code, ")
				.append("billing_acc_num, mobile_num, cust_acc_num, region_code, :userName, sysdate ")
				.append("FROM dcc_temp_transaction ")
				.append("WHERE mode_id = :modeId AND preassign_id = :preAssignId AND region_code != 'ERR'");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("preAssignDate", preAssignDate);
		query.setParameter("inputReason", 6);
		query.setParameter("userName", request.getUsername());
		query.setParameter("modeId", request.getModeId());
		query.setParameter("preAssignId", request.getPreAssignId());

		query.executeUpdate();
	}

	private void deleteFromTempTransaction(String modeId, String preAssignId) {
		String deleteQuery = "DELETE FROM dcc_temp_transaction WHERE mode_id = :modeId AND preassign_id = :preAssignId";
		Query query = entityManager.createNativeQuery(deleteQuery);
		query.setParameter("modeId", modeId);
		query.setParameter("preAssignId", preAssignId);
		query.executeUpdate();
	}

	private void updateCriteriaHistory(String modeId, String preAssignId, String userName) {
		String updateQuery = "UPDATE dcc_criteria_history SET cancel_assign_flag = 'Y', last_update_by = :userName, last_update_dtm = SYSDATE "
				+ "WHERE mode_id = :modeId AND preassign_id = :preAssignId";
		Query query = entityManager.createNativeQuery(updateQuery);
		query.setParameter("userName", userName);
		query.setParameter("modeId", modeId);
		query.setParameter("preAssignId", preAssignId);
		query.executeUpdate();
	}

	private void deleteFromCalendarTransaction(String modeId, String preAssignId) {
		String deleteQuery = "DELETE FROM dcc_calendar_transaction WHERE mode_id = :modeId AND prejob_id = :preAssignId AND job_type = :jobType AND execute_dtm IS NULL";
		Query query = entityManager.createNativeQuery(deleteQuery);
		query.setParameter("modeId", modeId);
		query.setParameter("preAssignId", preAssignId);
		query.setParameter("jobType", "ASSIGN");
		query.executeUpdate();
	}

	private Character getAutoAssignFlag(String modeId, String preAssignId) {
		String sql = "SELECT AUTO_ASSIGN_FLAG FROM dcc_criteria_history WHERE mode_id = :modeId AND preassign_id = :preAssignId";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("modeId", modeId);
		query.setParameter("preAssignId", preAssignId);
		List<Character> resultList = query.getResultList();
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	@Override
	public DMSEM005GetExemptDescriptionResponse getExemptDescription(DMSEM005GetExemptDescriptionRequest request)
			throws ExemptException {
		DMSEM005GetExemptDescriptionResponse result = new DMSEM005GetExemptDescriptionResponse();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT keyword_desc FROM dcc_global_parameter WHERE ");
			sql.append("section_name = :sectionName AND ");
			sql.append("keyword = :keyword AND ");
			sql.append("keyword_value = :keywordValue");

			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter("sectionName", request.getSectionName());
			query.setParameter("keyword", request.getKeyword());
			query.setParameter("keywordValue", request.getKeywordValue());
			List<String> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result.setKeywordDescription(resultList.get(0));
			}
		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

	@Override
	public DMSEM005GetReasonDescriptionResponse getReasonDescription(DMSEM005GetReasonDescriptionRequest request)
			throws ExemptException {
		DMSEM005GetReasonDescriptionResponse result = new DMSEM005GetReasonDescriptionResponse();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT reason_description FROM dcc_reason WHERE ");
			sql.append("reason_code = :reasonCode AND ");
			sql.append("reason_type = :reasonType");

			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter("reasonCode", request.getReasonCode());
			query.setParameter("reasonType", request.getReasonType());
			List<String> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result.setReasonDescription(resultList.get(0));
			}
		} catch (PersistenceException | IllegalArgumentException e) {
			throw new ExemptException(AppConstant.FAIL, e.getMessage());
		} finally {
			SqlUtils.close(entityManager);
		}
		return result;
	}

}
