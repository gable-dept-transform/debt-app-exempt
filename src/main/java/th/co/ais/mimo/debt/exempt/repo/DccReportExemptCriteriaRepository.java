package th.co.ais.mimo.debt.exempt.repo;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteria;
import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteriaId;

@Repository
public interface DccReportExemptCriteriaRepository
                extends JpaRepository<DccReportExemptCriteria, DccReportExemptCriteriaId> {

        @Query(value = " SELECT * FROM {h-schema}DCC_REPORT_EM_CRITERIA " +
                        "WHERE REPORT_ID = :reportId AND REPORT_SEQ = :reportSeq  ", nativeQuery = true)
        List<DccReportExemptCriteria> getReportExemptCriteriaByReportIdandReportSeq(@Param("reportId") String reportId,
                        @Param("reportSeq") String reportSeq) throws Exception;

        // @Query(value = " delete from {h-schema}DCC_REPORT_EM_CRITERIA " +
        //                 "where report_id = :reportId and report_seq = :reportSeq and report_status <> 'DL' ", nativeQuery = true)
        // List<DccReportExemptCriteria> deleteReportExemptCriteria(@Param("reportId") String reportId,
        //                 @Param("reportSeq") String reportSeq) throws Exception;

        @Modifying
        @Query(value = " Update {h-schema}DCC_REPORT_EM_CRITERIA " +
                        "set LAST_UPDATE_BY = :lastUpdateBy, last_update_dtm = sysdate, " +
                        " debt_age_from = null, report_status = :reportStatus, " +
                        " debt_age_to = null, ca_debt_mny_from  = null, ca_debt_mny_to = null, " +
                        " ba_debt_mny_from = null, ba_debt_mny_to = null " +
                        "  where report_id = :reportId and report_seq = :reportSeq ", nativeQuery = true)
        void updateReportStatusByReportIdandReportSeq(@Param("lastUpdateBy") String lastUpdateBy,
                        @Param("reportStatus") String reportStatus,
                        @Param("reportId") String reportId,
                        @Param("reportSeq") String reportSeq) throws Exception;

}
