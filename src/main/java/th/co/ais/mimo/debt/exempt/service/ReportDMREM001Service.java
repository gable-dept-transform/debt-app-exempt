package th.co.ais.mimo.debt.exempt.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.ReportDMREM001Dao;
import th.co.ais.mimo.debt.exempt.dto.GenReportSeqDto;
import th.co.ais.mimo.debt.exempt.entity.DccCalendarTransaction;
import th.co.ais.mimo.debt.exempt.entity.DccCalendarTransactionId;
import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteria;
import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteriaId;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DataDMREM001;
import th.co.ais.mimo.debt.exempt.model.DeleteReportDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SaveORUpdateDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SearchReportDataDMREM001Request;
import th.co.ais.mimo.debt.exempt.repo.DccCalendarTransactionRepository;
import th.co.ais.mimo.debt.exempt.repo.DccDMREM001TmpRepository;
import th.co.ais.mimo.debt.exempt.repo.DccPrivacyLogRepository;
import th.co.ais.mimo.debt.exempt.repo.DccReportExemptCriteriaRepository;
import th.co.ais.mimo.debt.exempt.service.impl.CommonService;

@Service
@Transactional
public class ReportDMREM001Service {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportDMREM001Dao reportDMREM001Dao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private DccCalendarTransactionRepository dccCalendarTransactionRepository;

    @Autowired
    private DccPrivacyLogRepository dccPrivacyLogRepository;

    @Autowired
    private DccReportExemptCriteriaRepository dccReportExemptCriteriaRepository;

    @Autowired
    private DccDMREM001TmpRepository dccDMREM001TmpRepository;

    public List<DataDMREM001> searchReportData(SearchReportDataDMREM001Request request) throws Exception {
        String errorMsg;
        try {
            // Search report Data
            List<DataDMREM001> resultList;
            errorMsg = validateSearchParameter(request);
            if (StringUtils.isEmpty(errorMsg)) {
                log.info("Call searchReportData");
                resultList = reportDMREM001Dao.searchReportDMREM001(request);
            } else {
                throw new ExemptException("200", errorMsg);
            }
            // Convert date format before search

            return resultList;
        } catch (Exception e) {
            log.error("Exception searchReportData : {}", e.getMessage(), e);
            throw e;
        }
    }

    public DataDMREM001 saveOrUpdateInfo(SaveORUpdateDMREM001Request request) throws Exception {
        String errorMsg = null;
        try {
            System.out.println("Call saveOrUpdateInfo");
            log.info("Call saveOrUpdateInfo");
            // Search report Data
            DataDMREM001 resultModel = new DataDMREM001();
            errorMsg = validateSaveOrUpdateInfo(request);

            if (StringUtils.isEmpty(errorMsg)) {

                if (request.getAmountFrom() == null) {
                    request.setAmountFrom(new BigDecimal(0));
                }

                if (request.getAmountTo() == null) {
                    request.setAmountTo(new BigDecimal(0));
                }

                if (AppConstant.FLAG_Y.equals(request.getSelectAllCate())) {
                    request.setCatSubCateList(AppConstant.ALL);
                } else if (AppConstant.FLAG_N.equals(request.getSelectAllCate())) {
                    if (StringUtils.isEmpty(request.getCatSubCateList())) {
                        request.setCatSubCateList(AppConstant.ALL);
                    }
                }

                DccReportExemptCriteria dccReportExemptCriteria = new DccReportExemptCriteria();

                if (AppConstant.FLAG_A.equals(request.getOperationMode())) {
                    GenReportSeqDto genReportSeqDto = commonService.getReportSeq(request.getReportId());
                    request.setReportSeq(genReportSeqDto.getReportMaxSeq());
                    request.setReportStatus("WT");
                    dccReportExemptCriteria = reportDMREM001Dao.saveDccReportExemptCriteria(request);

                    commonService.updateReportSeq(request.getReportId(), request.getReportSeq(), request.getUsername());

                    insertReportCalendarTransaction(
                            "EM",
                            0L,
                            dccReportExemptCriteria.getProcessDate(),
                            dccReportExemptCriteria.getId().getReportId(),
                            Long.valueOf(dccReportExemptCriteria.getId().getReportSeq()),
                            dccReportExemptCriteria.getId().getReportSeq(),
                            dccReportExemptCriteria.getId().getReportSeq(),
                            "N",
                            "Nothing",
                            "",
                            0,
                            dccReportExemptCriteria.getLastUpdateBy());

                } else if (AppConstant.FLAG_U.equals(request.getOperationMode())) {
                    dccReportExemptCriteria = reportDMREM001Dao.saveDccReportExemptCriteria(request);

                    commonService.updateReportSeq(request.getReportId(), request.getReportSeq(), request.getUsername());

                    dccCalendarTransactionRepository.updateCalendarTransaction(dccReportExemptCriteria.getProcessDate(),
                            "N", dccReportExemptCriteria.getLastUpdateBy(), 0,
                            "EM", 0L, Long.valueOf(dccReportExemptCriteria.getId().getReportSeq()),
                            dccReportExemptCriteria.getId().getReportId());

                    // Insert DCC Privacy Log
                    // DccPrivacyLog dccPrivacyLogRequest = new DccPrivacyLog();
                    // dccPrivacyLogRequest.setUserName(request.getUsername());
                    // dccPrivacyLogRequest.setLocationCode(request.getLocation());
                    // dccPrivacyLogRequest.setReferenceType(AppConstant.REFTYPE_EXEMPT_ACTION);
                    // insertDccPrivacyLog(dccPrivacyLogRequest);
                }
                resultModel = DataDMREM001.builder()
                        .reportId(dccReportExemptCriteria.getId().getReportId())
                        .reportSeq(dccReportExemptCriteria.getId().getReportSeq())
                        .criteriaDate(dccReportExemptCriteria.getCriteriaDtm())
                        .criteriaBy(dccReportExemptCriteria.getCriteriaBy())
                        .criteriaLocation(dccReportExemptCriteria.getCriteriaLocation())
                        .processDate(dccReportExemptCriteria.getProcessDate())
                        .reportStatus(dccReportExemptCriteria.getReportStatus())
                        .exemptStatus(dccReportExemptCriteria.getExemptStatus())
                        .companyCode(dccReportExemptCriteria.getCompanyCodeList())
                        .modeIdList(dccReportExemptCriteria.getExemptModeList())
                        .exemptFormat(dccReportExemptCriteria.getExemptFormat())
                        .exemptLevelList(dccReportExemptCriteria.getExemptLevelList())
                        .effectiveDateFrom(dccReportExemptCriteria.getEffectiveDateFrom())
                        .effectiveDateTo(dccReportExemptCriteria.getEffectiveDateTo())
                        .endDateFrom(dccReportExemptCriteria.getEndDateFrom())
                        .endDateTo(dccReportExemptCriteria.getEndDateTo())
                        .expireDateFrom(dccReportExemptCriteria.getExpireDateFrom())
                        .expireDateTo(dccReportExemptCriteria.getExpireDateTo())
                        .exemptCategory(dccReportExemptCriteria.getExmeptCategory())
                        .exemptAction(dccReportExemptCriteria.getExemptActionList())
                        .mobileStatusList(dccReportExemptCriteria.getMobileStatusList())
                        .locationCodeList(dccReportExemptCriteria.getLocationList())
                        .debtMnyFrom(dccReportExemptCriteria.getCaDebtMnyFrom())
                        .debtMnyTo(dccReportExemptCriteria.getCaDebtMnyTo())
                        .addReason(dccReportExemptCriteria.getReasonList())
                        .catSubCatList(dccReportExemptCriteria.getCateSubCateList())
                        .monthPeriod(dccReportExemptCriteria.getMonthPeriod())
                        .dayOver(dccReportExemptCriteria.getDayOver())
                        .durationOver(dccReportExemptCriteria.getDurationOver())
                        .locationFrom(dccReportExemptCriteria.getLocationFrom())
                        .locationTo(dccReportExemptCriteria.getLocationTo())
                        .build();

            } else {
                throw new ExemptException("200", errorMsg);
            }
            // Convert date format before search

            return resultModel;
        } catch (ExemptException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception saveOrUpdateInfo : {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteReportInformation(DeleteReportDMREM001Request request) throws Exception {
        String errorMsg = null;
        try {
            DccReportExemptCriteria dccReportExemptCriteria = dccReportExemptCriteriaRepository
                    .getReportExemptCriteriaByReportIdandReportSeq(request.getReportId(), request.getReportSeq())
                    .get(0);

            if (dccReportExemptCriteria == null) {
                errorMsg = "Report Em Criteria Data not found";
                throw new ExemptException("200", errorMsg);
            } else {
                System.out.println(dccReportExemptCriteria.getReportStatus());
                if (dccReportExemptCriteria.getReportStatus() == null) {
                    dccReportExemptCriteria.setReportStatus(AppConstant.REPORT_STATUS_WT);
                }

                if (AppConstant.REPORT_STATUS_WT.equals(dccReportExemptCriteria.getReportStatus())) {
                    dccReportExemptCriteriaRepository.deleteById(DccReportExemptCriteriaId.builder()
                            .reportId(request.getReportId()).reportSeq(request.getReportSeq()).build());

                    dccCalendarTransactionRepository.deleteCalendarTransaction("EM", 0, new Date(),
                            request.getReportId(), request.getReportSeq());
                } else {
                    dccReportExemptCriteriaRepository.updateReportStatusByReportIdandReportSeq(request.getUsername(),
                            AppConstant.REPORT_STATUS_DT, request.getReportId(),
                            request.getReportSeq());

                    dccDMREM001TmpRepository.deleteDccDMREM001TmpByReportIdandReportSeq(request.getReportId(),
                            request.getReportSeq());

                    dccCalendarTransactionRepository.updateCalendarTransaction(new Date(),
                            AppConstant.FLAG_Y, dccReportExemptCriteria.getLastUpdateBy(), 0,
                            "EM", 0L, Long.valueOf(dccReportExemptCriteria.getId().getReportSeq()),
                            dccReportExemptCriteria.getId().getReportId());
                }
            }

        } catch (ExemptException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception DeleteReportInformation : {}", e.getMessage(), e);
            throw e;
        }
    }

    private DccCalendarTransaction insertReportCalendarTransaction(String modeId,
            Long criteriaId,
            Date processDate,
            String jobType,
            Long setSeq,
            String prejobId,
            String jobId,
            String runResultFlag,
            String runResultDesc,
            String jobDescription,
            Integer priorityNo,
            String username) {

        DccCalendarTransactionId id = new DccCalendarTransactionId();
        id.setModeId(modeId);
        id.setCriteriaId(criteriaId);
        id.setRunDate(processDate);
        id.setJobType(jobType);
        id.setSetSeq(setSeq);

        DccCalendarTransaction request = new DccCalendarTransaction();
        request.setId(id);
        request.setPreJobId(prejobId);
        request.setJobId(jobId);
        request.setRunResultFlag(runResultFlag);
        request.setRunResultDesc(runResultDesc);
        request.setJobDescription(jobDescription);
        request.setPriorityNo(priorityNo);
        request.setLastUpdateBy(username);
        request.setLastUpdateDate(new Date());

        return dccCalendarTransactionRepository.save(request);
    }

    private String validateSearchParameter(SearchReportDataDMREM001Request request) {
        String errorMsg = null;
        if (StringUtils.isBlank(request.getReportId())) {
            errorMsg = "Missing key Report Id";
        }
        errorMsg = validateDateParameter(request.getCriteriaDateFrom(), request.getCriteriaDateTo(), "Criteria");
        if (errorMsg != null) {
            return errorMsg;
        }
        errorMsg = validateDateParameter(request.getProcessDateFrom(), request.getProcessDateTo(), "Process");
        if (errorMsg != null) {
            return errorMsg;
        }

        return errorMsg;

    }

    private String validateDateParameter(Calendar dateFrom, Calendar dateTo, String paramName) {
        if (dateFrom != null && dateTo == null) {
            return paramName + " Date To required";
        } else if (dateFrom == null && dateTo != null) {
            return paramName + " Date From required";
        } else if (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0) {
            return paramName + " Date To more than or equal to " + paramName + " Date From";
        }
        return null;
    }

    private String validateAmountParameter(BigDecimal amountFrom, BigDecimal amountTo, String paramName) {
        if (amountFrom != null && amountTo != null
                && (amountFrom.compareTo(amountTo) > 0)) {
            return paramName + "To must be greater than or equal to " + paramName + " From";
        }

        return null;
    }

    private String validateSaveOrUpdateInfo(SaveORUpdateDMREM001Request request) throws Exception {
        String errorMsg = null;
        if (StringUtils.isEmpty(request.getOperationMode())) {
            return "Operation Mode is required";
        } else if (!AppConstant.FLAG_A.equals(request.getOperationMode())
                && !AppConstant.FLAG_U.equals(request.getOperationMode())) {
            return "The operation mode does not mapping";
        } else if (StringUtils.isEmpty(request.getReportId())) {
            return "Report Id is required";
        } else if (StringUtils.isEmpty(request.getLocationList())) {
            return "Location List is required";
        } else if (request.getProcessDate() == null) {
            return "Process Date is required";
        } else if (request.getCompanyCodeList() == null) {
            return "Company Code is required";
        } else if (request.getExemptStatus() == null) {
            return "Exempt Status is required";
        } else if (request.getExemptFormatList() == null) {
            return "Exempt Format is required";
        }

        if (AppConstant.FLAG_U.equals(request.getOperationMode())) {
            List<DccReportExemptCriteria> dccReportExemptCriteria = reportDMREM001Dao
                    .queryDccReportExemptCriteriaByReportIdandReportSeq(request);
            if (dccReportExemptCriteria.isEmpty()) {
                return "Query report ng criteria(report status) data not found";
            } else if (StringUtils.isEmpty(dccReportExemptCriteria.get(0).getReportStatus())) {
                return "Query report ng criteria(report status) data not found ";
            } else if (AppConstant.REPORT_STATUS_WT.equals(dccReportExemptCriteria.get(0).getReportStatus())) {
                return "Unable to edit because report status is not equal to WT";
            }
        } else if (AppConstant.FLAG_A.equals(request.getOperationMode())) {
            Calendar currentDate = Calendar.getInstance();
            currentDate.set(Calendar.HOUR_OF_DAY, 0);
            currentDate.set(Calendar.MINUTE, 0);
            currentDate.set(Calendar.SECOND, 0);
            currentDate.set(Calendar.MILLISECOND, 0);
            Calendar processDate = request.getProcessDate();
            processDate.set(Calendar.HOUR_OF_DAY, 0);
            processDate.set(Calendar.MINUTE, 0);
            processDate.set(Calendar.SECOND, 0);
            processDate.set(Calendar.MILLISECOND, 0);
            if (processDate.compareTo(currentDate) < 0) {
                return "Process date must be greater than or equal to current date";
            }
        }

        errorMsg = validateDateParameter(request.getEffectiveDateFrom(), request.getEffectiveDateTo(), "Effective");
        if (errorMsg != null) {
            return errorMsg;
        }
        errorMsg = validateDateParameter(request.getEndDateFrom(), request.getEndDateTo(), "End");
        if (errorMsg != null) {
            return errorMsg;
        }

        errorMsg = validateDateParameter(request.getExpireDateFrom(), request.getExpireDateTo(), "Expired");
        if (errorMsg != null) {
            return errorMsg;
        }

        errorMsg = validateAmountParameter(request.getAmountFrom(), request.getAmountTo(), "Amount");
        if (errorMsg != null) {
            return errorMsg;
        }

        // request.getExemptFormatList().matches(AppConstant.EXEMPT_FORMAT_DETAIL)
        // ||

        if (request.getExemptFormatList().matches(AppConstant.EXEMPT_FORMAT_SUMMARY)) {
            if (StringUtils.isEmpty(request.getGroupForSummary())) {
                return "Group by For Summary is required";
            }
        }

        return null;
    }

}
