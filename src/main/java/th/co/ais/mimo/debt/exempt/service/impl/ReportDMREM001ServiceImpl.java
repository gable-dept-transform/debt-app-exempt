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
            sql.append("report_id as \"reportId\", ");
            sql.append("report_seq as \"reportSeq\", ");
            sql.append("criteria_dtm as \"criteriaDate\", ");
            sql.append("criteria_by as \"criteriaBy\", ");
            sql.append("criteria_location as \"criteriaLocation\", ");
            sql.append("process_dat as \"processDate\", ");
            sql.append("report_status as \"reportStatus\", ");
            sql.append("exempt_status as \"exemptStatus\", ");
            sql.append("exempt_format as \"exemptFormat\", ");
            sql.append("group_by_list as \"groupByForSummary\", ");
            sql.append("company_code as \"companyCode\", ");
            sql.append("mode_id_list as \"modeIdList\", ");
            sql.append("exempt_level_list as \"exemptLevelList\", ");
            sql.append("effective_dat_from as \"effectiveDateFrom\", ");
            sql.append("effective_dat_to as \"effectiveDateTo\", ");
            sql.append("end_dat_from as \"endDateFrom\", ");
            sql.append("end_dat_to as \"endDateTo\", ");
            sql.append("expire_dat_from as \"expireDateFrom\", ");
            sql.append("expire_dat_to as \"expireDateTo\", ");
            sql.append("cate_code as \"exemptCategory\", ");
            sql.append("payment_type_list as \"exemptAction\", ");
            sql.append("mobile_status_list as \"mobileStatusList\", ");
            sql.append("location_code_list as \"locationCodeList\", ");
            sql.append("debt_mny_from as \"debtMnyFrom\", ");
            sql.append("debt_mny_to as \"debtMnyTo\", ");
            sql.append("add_reason_list as \"addReason\", ");
            sql.append("cate_subcate_list as \"catSubCatList\", ");
            sql.append("month_period as \"monthPeriod\", ");
            sql.append("day_over as \"dayOver\", ");
            sql.append("duration_over as \"durationOver\", ");
            sql.append("location_from as \"locationFrom\", ");
            sql.append("location_to as \"locationTo\" ");
            sql.append("FROM dcc_report_em_criteria ");
            sql.append("WHERE 1=1 ");

            if (AppConstant.FLAG_Y.equals(request.getFvBlnACSLocation())) {
                sql.append(" AND criteria_location IN (:userLoctionCodeList) ");
            }

            if (StringUtils.isNotBlank(request.getReportId())) {
                sql.append("AND REPORT_ID = :reportId ");
            }

            if (StringUtils.isNotBlank(request.getReportStatus())) {
                sql.append(" AND REPORT_STATUS = :reportStatus ");
            }

            if (StringUtils.isNotBlank(request.getReportSeq())) {
                sql.append(" AND REPORT_SEQ = :reportSeq ");
            }

            if (StringUtils.isNotBlank(request.getCriteriaBy())) {
                sql.append(" AND CRITERIA_BY = :criteriaBy ");
            }

            if (request.getCriteriaDateFrom() != null && request.getCriteriaDateTo() != null) {
                sql.append(" AND CRITERIA_DTM >= TRUNC(:criteriaDateFrom) ");
                sql.append(" AND CRITERIA_DTM <= TRUNC(:criteriaDateTo) + INTERVAL '1' DAY - INTERVAL '1' SECOND ");
            }

            if (request.getProcessDateFrom() != null && request.getProcessDateTo() != null) {
                sql.append(" AND PROCESS_DAT >= TRUNC(:processDateFrom) ");
                sql.append(" AND PROCESS_DAT <= TRUNC(:processDateTo) + INTERVAL '1' DAY - INTERVAL '1' SECOND ");
            }

            sql.append(" ORDER BY report_seq DESC ");

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

            if (AppConstant.FLAG_Y.equals(request.getFvBlnACSLocation())) {
                query.setParameter("userLoctionCodeList", request.getAcsLocationList().get(0));
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
                .processDate(request.getProcessDate().getTime())
                .effectiveDateFrom(request.getEffectiveDateFrom().getTime())
                .effectiveDateTo(request.getEffectiveDateTo().getTime())
                .endDateFrom(request.getEndDateFrom().getTime())
                .endDateTo(request.getEndDateTo().getTime())
                .expireDateFrom(request.getExpireDateFrom().getTime())
                .expireDateTo(request.getExpireDateTo().getTime())
                .locationFrom(0)
                .locationTo(0)
                .debtAgeFrom(new BigDecimal(0))
                .debtAgeTo(new BigDecimal(0))
                .caDebtMnyFrom(new BigDecimal(0))
                .caDebtMnyTo(new BigDecimal(0))
                .baDebtMnyFrom(new BigDecimal(0))
                .baDebtMnyTo(new BigDecimal(0))
                .amountFrom(request.getAmountFrom())
                .amountTo(request.getAmountTo())
                .cateSubCateList(request.getCatSubCateList())
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
