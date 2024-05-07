package th.co.ais.mimo.debt.exempt.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import th.co.ais.mimo.debt.exempt.entity.DccDMREM001Tmp;

public interface DccDMREM001TmpRepository extends JpaRepository<DccDMREM001Tmp, String> {

    @Query(value = "DELETE FROM {h-schema}dcc_dmrem001_tmp " +
            "	where report_id = :reportId and  report_seq = :reportSeq   ", nativeQuery = true)
    void deleteDccDMREM001TmpByReportIdandReportSeq(@Param("reportId") String reportId,
            @Param("reportSeq") String reportSeq);
}
