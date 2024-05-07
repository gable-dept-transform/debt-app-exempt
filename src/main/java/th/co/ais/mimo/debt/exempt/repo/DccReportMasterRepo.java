package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.dto.GenReportSeqDto;
import th.co.ais.mimo.debt.exempt.entity.DccReportMaster;

import java.util.List;

@Repository
public interface DccReportMasterRepo extends JpaRepository<DccReportMaster, String> {

	@Query(value = " SELECT CASE WHEN SUBSTR(REPORT_MAX_SEQ,1,4) =  TO_CHAR(SYSDATE,'YY')+43||TO_CHAR(SYSDATE,'MM')   "
			+
			" THEN TO_CHAR(TO_NUMBER(REPORT_MAX_SEQ)+1)  " +
			" ELSE TO_CHAR(SYSDATE,'YY')+43||TO_CHAR(SYSDATE,'MM') || '00001' END " +
			" reportMaxSeq , REPORT_NAME  AS reportName ,REPORT_DATABASE  AS reportDatabase " +
			" FROM DCC_REPORT_MASTER WHERE REPORT_ID = :reportId ", nativeQuery = true)
	GenReportSeqDto genReportSeq(@Param("reportId") String reportId);

	@Modifying
	@Query(value = "UPDATE {h-schema}DCC_REPORT_MASTER " +
			"		SET REPORT_MAX_SEQ = :reportSeq " +
			"		,LAST_UPDATE_BY=:updateBy" +
			"		,LAST_UPDATE_DTM=SYSDATE " +
			"	WHERE REPORT_ID = :reportId ", nativeQuery = true)
	void updateReportSeq(@Param("reportId") String reportId, @Param("reportSeq") String reportSeq,
			@Param("updateBy") String updateBy);

}
