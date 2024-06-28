package th.co.ais.mimo.debt.exempt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.Setter;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.ReportDMREM001Dao;
import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteria;
import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteriaId;
import th.co.ais.mimo.debt.exempt.model.DataDMREM001;
import th.co.ais.mimo.debt.exempt.model.SaveORUpdateDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SearchReportDataDMREM001Request;
import th.co.ais.mimo.debt.exempt.repo.DccReportExemptCriteriaRepository;
import th.co.ais.mimo.debt.exempt.utils.SqlUtils;

@Repository
public class ReportDMREM001ServiceImpl implements ReportDMREM001Dao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Setter
    private DccReportExemptCriteriaRepository dccReportExemptCriteriaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DataDMREM001> searchReportDMREM001(SearchReportDataDMREM001Request request) throws Exception {
        List<DataDMREM001> result = new ArrayList<DataDMREM001>();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("r.report_id as \"reportId\", ");
            sql.append("r.report_seq as \"reportSeq\", ");
            sql.append("r.criteria_dtm as \"criteriaDate\", ");
            sql.append("r.criteria_by as \"criteriaBy\", ");
            sql.append("r.criteria_location as \"criteriaLocation\", ");
            sql.append("r.process_dat as \"processDate\", ");
            sql.append("r.report_status as \"reportStatus\", ");
            sql.append("g.keyword_desc as \"reportStatusDesc\", ");
            sql.append("r.exempt_status as \"exemptStatus\", ");
            sql.append("r.exempt_format as \"exemptFormat\", ");
            sql.append("r.group_by_list as \"groupByForSummary\", ");
            sql.append("r.company_code as \"companyCode\", ");
            sql.append("r.mode_id_list as \"modeIdList\", ");
            sql.append("r.exempt_level_list as \"exemptLevelList\", ");
            sql.append("r.effective_dat_from as \"effectiveDateFrom\", ");
            sql.append("r.effective_dat_to as \"effectiveDateTo\", ");
            sql.append("r.end_dat_from as \"endDateFrom\", ");
            sql.append("r.end_dat_to as \"endDateTo\", ");
            sql.append("r.expire_dat_from as \"expireDateFrom\", ");
            sql.append("r.expire_dat_to as \"expireDateTo\", ");
            sql.append("r.cate_code as \"exemptCategory\", ");
            sql.append("r.payment_type_list as \"exemptAction\", ");
            sql.append("r.mobile_status_list as \"mobileStatusList\", ");
            sql.append("r.location_code_list as \"locationCodeList\", ");
            sql.append("r.debt_mny_from as \"debtMnyFrom\", ");
            sql.append("r.debt_mny_to as \"debtMnyTo\", ");
            sql.append("r.add_reason_list as \"addReason\", ");
            sql.append("r.cate_subcate_list as \"catSubCatList\", ");
            sql.append("r.month_period as \"monthPeriod\", ");
            sql.append("r.day_over as \"dayOver\", ");
            sql.append("r.duration_over as \"durationOver\", ");
            sql.append("r.location_from as \"locationFrom\", ");
            sql.append("r.location_to as \"locationTo\" ");
            sql.append("FROM dcc_report_em_criteria r ");
            sql.append("LEFT JOIN DCC_GLOBAL_PARAMETER g ON r.report_status = g.KEYWORD ");
            sql.append("WHERE 1=1 AND g.SECTION_NAME = 'REPORT_STATUS' ");

            if (StringUtils.isNotBlank(request.getReportId())) {
                sql.append("AND r.REPORT_ID = :reportId ");
            }

            if (StringUtils.isNotBlank(request.getReportStatus())) {
                sql.append(" AND g.KEYWORD_VALUE = :reportStatus ");
            }

            if (StringUtils.isNotBlank(request.getReportSeq())) {
                sql.append(" AND r.REPORT_SEQ = :reportSeq ");
            }

            if (StringUtils.isNotBlank(request.getCriteriaBy())) {
                sql.append(" AND r.CRITERIA_BY = :criteriaBy ");
            }

            if (request.getCriteriaDateFrom() != null && request.getCriteriaDateTo() != null) {
                sql.append(" AND r.CRITERIA_DTM >= TRUNC(:criteriaDateFrom) ");
                sql.append(" AND r.CRITERIA_DTM <= TRUNC(:criteriaDateTo) + INTERVAL '1' DAY - INTERVAL '1' SECOND ");
            }

            if (request.getProcessDateFrom() != null && request.getProcessDateTo() != null) {
                sql.append(" AND r.PROCESS_DAT >= TRUNC(:processDateFrom) ");
                sql.append(" AND r.PROCESS_DAT <= TRUNC(:processDateTo) + INTERVAL '1' DAY - INTERVAL '1' SECOND ");
            }

            sql.append(" ORDER BY r.report_seq DESC ");

            Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

            if (StringUtils.isNotBlank(request.getReportId())) {
                query.setParameter("reportId", request.getReportId());
            }

            if (StringUtils.isNotBlank(request.getReportSeq())) {
                query.setParameter("reportSeq", request.getReportSeq());
            }

            if (StringUtils.isNotBlank(request.getReportStatus())) {
                query.setParameter("reportStatus", request.getReportStatus());
            }

            if (StringUtils.isNotBlank(request.getCriteriaBy())) {
                query.setParameter("criteriaBy", request.getCriteriaBy());
            }

            if (request.getCriteriaDateFrom() != null && request.getCriteriaDateTo() != null) {
                query.setParameter("criteriaDateFrom", request.getCriteriaDateFrom());
                query.setParameter("criteriaDateTo", request.getCriteriaDateTo());
            }
            if (request.getProcessDateFrom() != null && request.getProcessDateTo() != null) {
                query.setParameter("processDateFrom", request.getProcessDateFrom());
                query.setParameter("processDateTo", request.getProcessDateTo());
            }

            result = SqlUtils.parseResult(query.getResultList(), DataDMREM001.class);

        } catch (Exception e) {
            log.error("Exception searchReport : {}", e.getMessage(), e);
            throw e;
        } finally {
            SqlUtils.close(entityManager);
        }
        return result;
    }

    @Override
    public DccReportExemptCriteria saveDccReportExemptCriteria(SaveORUpdateDMREM001Request request)
            throws Exception {

        DccReportExemptCriteriaId id = new DccReportExemptCriteriaId().builder().reportId(request.getReportId())
                .reportSeq(request.getReportSeq()).build();

        DccReportExemptCriteria dccReportExemptCriteria = new DccReportExemptCriteria()
                .builder()
                .id(id)
                .reportStatus(request.getReportStatus())
                .companyCodeList(request.getCompanyCodeList())
                .exemptStatus(request.getExemptStatus())
                .exemptFormat(request.getExemptFormatList())
                .groupForSummary(request.getGroupForSummary())
                .exemptModeList(request.getExemptModeList())
                .exemptLevelList(request.getExemptLevelList())
                .exmeptCategory(request.getExemptCategoryList())
                .reasonList(request.getReasonCodeList())
                .exemptActionList(request.getExemptActionList())
                .mobileStatusList(request.getMobileStatusList())
                .locationList(request.getLocationList())
                .processDate(request.getProcessDate() != null ? request.getProcessDate().getTime() : null)
                .effectiveDateFrom(
                        request.getEffectiveDateFrom() != null ? request.getEffectiveDateFrom().getTime() : null)
                .effectiveDateTo(request.getEffectiveDateTo() != null ? request.getEffectiveDateTo().getTime() : null)
                .endDateFrom(request.getEndDateFrom() != null ? request.getEndDateFrom().getTime() : null)
                .endDateTo(request.getEndDateTo() != null ? request.getEndDateTo().getTime() : null)
                .expireDateFrom(request.getExpireDateFrom() != null ? request.getExpireDateFrom().getTime() : null)
                .expireDateTo(request.getExpireDateTo() != null ? request.getExpireDateTo().getTime() : null)
                .locationFrom(0)
                .locationTo(0)
                .debtAgeFrom(request.getAmountFrom())
                .debtAgeTo(request.getAmountTo())
                .caDebtMnyFrom(new BigDecimal(0))
                .caDebtMnyTo(new BigDecimal(0))
                .baDebtMnyFrom(new BigDecimal(0))
                .baDebtMnyTo(new BigDecimal(0))
                .amountFrom(request.getAmountFrom())
                .amountTo(request.getAmountTo())
                .cateSubCateList(
                        AppConstant.FLAG_Y.equals(request.getSelectAllCate()) ? "ALL" : request.getCatSubCateList())
                .monthPeriod(request.getMonthPeriod())
                .durationOver(request.getDurationOver())
                .criteriaBy(request.getUsername())
                .criteriaDtm(new Date())
                .criteriaLocation(request.getLocation())
                .lastUpdateBy(request.getUsername())
                .lastUpdateDtm(new Date())
                .build();
        return dccReportExemptCriteriaRepository.save(dccReportExemptCriteria);

    }

    @Override
    public List<DccReportExemptCriteria> queryDccReportExemptCriteriaByReportIdandReportSeq(
            SaveORUpdateDMREM001Request request) throws Exception {
        // TODO Auto-generated method stub
        List<DccReportExemptCriteria> result = new ArrayList<DccReportExemptCriteria>();
        DccReportExemptCriteria dccReportExemptCriteria = DccReportExemptCriteria.builder()
                .id(DccReportExemptCriteriaId.builder().reportId(request.getReportId())
                        .reportSeq(request.getReportSeq()).build())
                .build();

        Example<DccReportExemptCriteria> example = Example.of(dccReportExemptCriteria);

        result = dccReportExemptCriteriaRepository.findAll(example);

        return result;
    }

    // @Override
    // public List<DataDMREM001>
    // getCriteriaByReportIdandSeq(SearchReportDataDMREM001Request request) throws
    // Exception {
    // List<DataDMREM001> result = dccReportExemptCriteriaRepository
    // .findAll(Example.of(request.builder().reportId("1").reportSeq("1").build()));
    // return result;
    // }

}
