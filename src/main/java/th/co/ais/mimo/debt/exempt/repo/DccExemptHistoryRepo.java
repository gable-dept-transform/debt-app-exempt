package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import th.co.ais.mimo.debt.exempt.entity.DccExemptHistory;

@Repository
public interface DccExemptHistoryRepo extends JpaRepository<DccExemptHistory,String> {


    @Query(value =" select nvl(max(no_of_exempt),0) as max_seq from {h-schema}dcc_exempt_history\n" +
            "where cust_acc_num = :cust_acc_num and module_code = :module_code and mode_id = :mode_id",nativeQuery = true)
    public Long countNumberOfExempt(@Param("cust_acc_num") String custAccNum,@Param("module_code") String moduleCode,@Param("mode_id") String modeId);

    @Query(value ="select nvl(max(exempt_seq),0) as max_seq from dcc_exempt_history "+
            " where cust_acc_num = :cust_acc_num and module_code =:module_code and mode_id =:mode_id and no_of_exempt =:no_of_exempt ",nativeQuery = true)
    public Long countExemptHisForUpdate(@Param("cust_acc_num") String custAccNum,@Param("module_code") String moduleCode,@Param("mode_id") String modeId,@Param("no_of_exempt") Long noOfExempt);

    @Query(value = "select count(1) COUNT_EXEMPT \n" +
            "from dcc_exempt_history h \n" +
            "where h.action_type = 'ADD' \n" +
            "and h.cust_acc_num = :custAccNum\n" +
            "and h.effective_dat >= add_months(to_date(to_char(trunc(sysdate),'dd/mm/yyyy'),'dd/mm/yyyy'),-(TO_NUMBER(DCCU_DBUTIL.GET_GLOBAL_PARAMETER('CONFIG','EXEMPT_LIMIT')))) \n" +
            "and h.effective_dat <= trunc(sysdate) \n" +
            "and h.exempt_level = :exemptLevel \n" +
            "and instr(:modeId,h.mode_id) > 0 ",nativeQuery = true)
    public Long countExemptHistoryByCA(@Param("custAccNum") String custAccNum,@Param("exemptLevel") String exemptLevel,@Param("modeId") String modeId);

    @Query(value = "select count(1) COUNT_EXEMPT \n" +
            "from dcc_exempt_history h \n" +
            "where h.action_type = 'ADD' \n" +
            "and h.cust_acc_num = :custAccNum\n" +
            "and h.billing_acc_num = :billingAccNum\n" +
            "and h.effective_dat >= add_months(to_date(to_char(trunc(sysdate),'dd/mm/yyyy'),'dd/mm/yyyy'),-(TO_NUMBER(DCCU_DBUTIL.GET_GLOBAL_PARAMETER('CONFIG','EXEMPT_LIMIT')))) \n" +
            " and h.effective_dat <= trunc(sysdate) \n" +
            " and h.exempt_level = :exemptLevel \n" +
            " and instr(:modeId,h.mode_id) > 0 ",nativeQuery = true)
    public Long countExemptHistoryByBA(@Param("custAccNum") String custAccNum,@Param("billingAccNum") String billingAccNum,@Param("exemptLevel") String exemptLevel,@Param("modeId") String modeId);

    @Query(value = "select count(1) COUNT_EXEMPT \n" +
            "from dcc_exempt_history h \n" +
            "where h.action_type = 'ADD' \n" +
            "and h.cust_acc_num = :custAccNum\n" +
            "and h.billing_acc_num = :billingAccNum\n" +
            "and h.mobile_num = :mobileNum\n" +
            "and h.effective_dat >= add_months(to_date(to_char(trunc(sysdate),'dd/mm/yyyy'),'dd/mm/yyyy'),-(TO_NUMBER(DCCU_DBUTIL.GET_GLOBAL_PARAMETER('CONFIG','EXEMPT_LIMIT')))) \n" +
            "and h.effective_dat <= trunc(sysdate) \n" +
            "and h.exempt_level = :exemptLevel \n" +
            "and instr(:modeId,h.mode_id) > 0 ",nativeQuery = true)
    public Long countExemptHistoryByMobile(@Param("custAccNum") String custAccNum,@Param("billingAccNum") String billingAccNum,@Param("mobileNum") String mobileNum,@Param("exemptLevel") String exemptLevel,@Param("modeId") String modeId);
}
