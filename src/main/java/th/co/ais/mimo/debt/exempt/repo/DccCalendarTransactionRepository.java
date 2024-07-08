package th.co.ais.mimo.debt.exempt.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccCalendarTransaction;
import th.co.ais.mimo.debt.exempt.entity.DccCalendarTransactionId;

@Repository
public interface DccCalendarTransactionRepository extends JpaRepository<DccCalendarTransaction, DccCalendarTransactionId> {

        @Modifying
        @Query(value = "UPDATE {h-schema}DCC_CALENDAR_TRANSACTION " +
                        "	SET RUN_DAT = TO_DATE(:runDate,'YYYY/MM/DD') " +
                        "	,PAUSE_FLAG = :pauseFlag, LAST_UPDATE_BY = :username" +
                        "	,LAST_UPDATE_DTM=SYSDATE " +
                        "       ,PRIORITY_NO = :priorityNo " +
                        "	WHERE MODE_ID = :modeId AND CRITERIA_ID = :criteriaId " +
                        "       AND SET_SEQ = :setSeq AND JOB_TYPE = :jobType " +
                        "       AND EXECUTE_DTM IS NULL", nativeQuery = true)
        void updateCalendarTransaction(@Param("runDate") Date runDate, @Param("pauseFlag") String pauseFlag,
                        @Param("username") String username, @Param("priorityNo") Integer priorityNo,
                        @Param("modeId") String modeId,
                        @Param("criteriaId") Integer criteriaId, @Param("setSeq") String setSeq,
                        @Param("jobType") String jobType);

        @Modifying
        @Query(value = "DELETE FROM {h-schema}DCC_CALENDAR_TRANSACTION " +
                        "	WHERE MODE_ID = :modeId AND CRITERIA_ID = :criteriaId   " +
                        "	AND RUN_DAT = TRUNC(:runDate)  " +
                        "	AND JOB_TYPE = :jobType   " +
                        "      AND SET_SEQ = TO_NUMBER(:setSeq)  ", nativeQuery = true)
        void deleteCalendarTransaction(@Param("modeId") String modeId,
                        @Param("criteriaId") Integer criteriaId, @Param("runDate") Date runDate,
                        @Param("jobType") String jobType, @Param("setSeq") String setSeq);
        
        @Query(nativeQuery = true, value = "select {h-schema}dcc_calendar_tran_seq.nextval from dual")
        int getDccCalendarTranNextval();
}
