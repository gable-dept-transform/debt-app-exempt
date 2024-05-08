package th.co.ais.mimo.debt.exempt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.entity.DccExemptModel;

import java.util.List;

@Repository
public interface DccExemptRepo extends JpaRepository<DccExemptModel,String> {

    @Query(value = "select count(1)\n" +
            " from dcc_exempt e \n" +
            "  where e.cust_acc_num = :custAccNum AND EXEMPT_LEVEL IN (:exemptLevel)",nativeQuery = true)
    public Long countExemptByLevel(@Param("custAccNum") String custAccNum,@Param("exemptLevel") List<String> exemptLevel);

    @Query(value = "update dcc_exempt set  end_dat = :endDate, \n" +
            " update_reason   = :updateReason, update_location = :updateLocation,\n" +
            " last_update_by  = :lastUpdateBy, last_update_dtm = sysdate ,\n" +
            " sent_interface_flag = :sentInterfaceFlag ,\n" +
            " SENT_BOS_FLAG = (select g.KEYWORD_VALUE from DCC_GLOBAL_PARAMETER g where g.SECTION_NAME = 'CONFIG_BOS_EXEMPT' and g.KEYWORD = 'SEND_BOS_FLAG')  \n" +
            " where cust_acc_num = :custAccNum and module_code = :moduleCode and mode_id = :modeId \n" +
            " and exempt_level  = :exemptLevel and no_of_exempt = :noOfExempt",nativeQuery = true)
    @Modifying
    public int updateExemptByCA(@Param("updateReason") String updateReason,
                            @Param("updateLocation") Integer updateLocation,
                            @Param("lastUpdateBy") String lastUpdateBy,
                            @Param("sentInterfaceFlag") String sentInterfaceFlag,
                            @Param("custAccNum") String custAccNum,
                            @Param("moduleCode") String moduleCode,
                            @Param("modeId") String modeId,
                            @Param("exemptLevel") String exemptLevel,
                            @Param("noOfExempt") Long noOfExempt);

    @Query(value = "delete from dcc_exempt where  cust_acc_num=:custAccNum and billing_acc_num =:billingAccNum and  module_code=:moduleCode  and \n" +
            " mode_id=:modeId and exempt_level = :exemptLevel and no_of_exempt = :noOfExempt",nativeQuery = true)
    @Modifying
    public int deleteExemptByCA(@Param("custAccNum") String custAccNum,
                                @Param("billingAccNum") String billingAccNum,
                                @Param("moduleCode") String moduleCode,
                                @Param("modeId") String modeId,
                                @Param("exemptLevel") String exemptLevel,
                                @Param("noOfExempt") Long noOfExempt);

    @Query(value = "update dcc_exempt set  end_dat = :endDate, \n" +
            " update_reason   = :updateReason, update_location = :updateLocation,\n" +
            " last_update_by  = :lastUpdateBy, last_update_dtm = sysdate ,\n" +
            " sent_interface_flag = :sentInterfaceFlag ,\n" +
            " SENT_BOS_FLAG = (select g.KEYWORD_VALUE from DCC_GLOBAL_PARAMETER g where g.SECTION_NAME = 'CONFIG_BOS_EXEMPT' and g.KEYWORD = 'SEND_BOS_FLAG')  \n" +
            " where cust_acc_num = :custAccNum and billing_acc_num = :billingAccNum and mobile_num = :mobileNum and module_code = :moduleCode and mode_id = :modeId \n" +
            " and exempt_level  = :exemptLevel",nativeQuery = true)
    @Modifying
    public int updateExemptByBA(@Param("updateReason") String updateReason,
                                @Param("updateLocation") String updateLocation,
                                @Param("lastUpdateBy") String lastUpdateBy,
                                @Param("sentInterfaceFlag") String sentInterfaceFlag,
                                @Param("custAccNum") String custAccNum,
                                @Param("billingAccNum") String billingAccNum,
                                @Param("mobileNum") String mobileNum,
                                @Param("moduleCode") String moduleCode,
                                @Param("modeId") String modeId,
                                @Param("exemptLevel") String exemptLevel);

    @Query(value = "update dcc_exempt set  end_dat = :endDate, \n" +
            " update_reason   = :updateReason, update_location = :updateLocation,\n" +
            " last_update_by  = :lastUpdateBy, last_update_dtm = sysdate ,\n" +
            " sent_interface_flag = :sentInterfaceFlag ,\n" +
            " SENT_BOS_FLAG = (select g.KEYWORD_VALUE from DCC_GLOBAL_PARAMETER g where g.SECTION_NAME = 'CONFIG_BOS_EXEMPT' and g.KEYWORD = 'SEND_BOS_FLAG')  \n" +
            " where cust_acc_num = :custAccNum and billing_acc_num = :billingAccNum and mobile_num = :mobileNum and module_code = :moduleCode and mode_id = :modeId \n" +
            " and exempt_level  = :exemptLevel",nativeQuery = true)
    @Modifying
    public int updateExemptByMO(@Param("updateReason") String updateReason,
                                @Param("updateLocation") String updateLocation,
                                @Param("lastUpdateBy") String lastUpdateBy,
                                @Param("sentInterfaceFlag") String sentInterfaceFlag,
                                @Param("custAccNum") String custAccNum,
                                @Param("billingAccNum") String billingAccNum,
                                @Param("mobileNum") String mobileNum,
                                @Param("moduleCode") String moduleCode,
                                @Param("modeId") String modeId,
                                @Param("exemptLevel") String exemptLevel);
}
