package th.co.ais.mimo.debt.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.entity.DccExemptModel;

import java.util.List;

@Repository
public interface DMSEM003NativeQueryService extends JpaRepository<DccExemptModel,String> {

    @Query(value = "SELECT  " +
            "                      cust_acc_num AS custAccNum,  " +
            "                      billing_acc_num AS billingAccNum,  " +
            "                      mobile_num AS mobileNum,  " +
            "                      module_code AS moduleCode,  " +
            "                      mode_id AS modeId,  " +
            "                      exempt_level AS exemptLevel,  " +
            "                      (  " +
            "                      SELECT  " +
            "                       b.x_con_full_name  " +
            "                      FROM  " +
            "                       billing_profile b  " +
            "                      WHERE  " +
            "                       b.ou_num = billing_acc_num ) AS billingAccName,  " +
            "                      to_char(effective_dat, 'YYYY/MM/DD') AS effectiveDate ,  " +
            "                      to_char(end_dat, 'YYYY/MM/DD') AS endDate ,  " +
            "                      to_char(expire_dat, 'YYYY/MM/DD') AS expireDate,  " +
            "                      e.CATE_CODE  AS cateCode,  " +
            "                      (  " +
            "                      SELECT  " +
            "                       reason_code || ' : ' || reason_description  " +
            "                      FROM  " +
            "                       dcc_reason  " +
            "                      WHERE  " +
            "                       reason_type = 'EXEMPT_ADD'  " +
            "                       AND reason_code = e.add_reason) AS addReason ,  " +
            "                      e.ADD_LOCATION  AS addLocation,  " +
            "                      (  " +
            "                      SELECT  " +
            "                       reason_code || ' : ' || reason_description  " +
            "                      FROM  " +
            "                       dcc_reason  " +
            "                      WHERE  " +
            "                       reason_type = 'EXEMPT_UPDATE'  " +
            "                       AND reason_code = e.update_reason) updateReason,  " +
            "                      e.UPDATE_LOCATION  AS updateLocation,  " +
            "                      e.LAST_UPDATE_BY AS lastUpdateBy,  " +
            "                      to_char(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate,  " +
            "                      e.NO_OF_EXEMPT  AS noOfExempt,  " +
            "                      e.SENT_INTERFACE_FLAG  AS sentInterFaceFlag  , null as mobileStatus , 0 as rowNumber " +
            "                      FROM  " +
            "                      dcc_exempt e ",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getSearchCurrent();
}
