package th.co.ais.mimo.debt.exempt.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.ais.mimo.debt.exempt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.exempt.entity.DccExemptModel;
import th.co.ais.mimo.debt.exempt.entity.DccExemptModelId;

import java.util.List;

@Repository
public interface DMSEM003NativeQueryService extends JpaRepository<DccExemptModel, DccExemptModelId> {

    @Query(value = "SELECT * " +
            "FROM ( " +
            "    SELECT aa.*, " +
            "           row_number() over (order by exemptLevel desc, moduleCode, modeId, custAccNum, billingAccNum, mobileStatus, mobileNum) as rownumber " +
            "    FROM ( " +
            "        SELECT cust_acc_num AS custAccNum, " +
            "               billing_acc_num AS billingAccNum, " +
            "               mobile_num AS mobileNum, " +
            "               module_code AS moduleCode, " +
            "               mode_id AS modeId, " +
            "               exempt_level AS exemptLevel, " +
            "               (SELECT a.name FROM account a WHERE a.ou_num = billing_acc_num AND a.accnt_type_cd = 'Billing') AS billingAccName, " +
            "               to_char(effective_dat,'YYYY/MM/DD') AS effectiveDate, " +
            "               to_char(end_dat,'YYYY/MM/DD') AS endDate, " +
            "               to_char(expire_dat,'YYYY/MM/DD') AS expireDate, " +
            "               cate_code AS cateCode, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_ADD' AND reason_code=e.add_reason) AS addReason, " +
            "               add_location AS addLocation, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_UPDATE' AND reason_code=e.update_reason) AS updateReason, " +
            "               update_location AS updateLocation, " +
            "               last_update_by AS lastUpdateBy, " +
            "               to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "               no_of_exempt AS noOfExempt, " +
            "               sent_interface_flag AS sentInterFlag, " +
            "               decode(mb.x_suspend_type, null, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus " +
            "        FROM dcc_exempt e, account_has_mobile mb " +
            "        WHERE billing_acc_num= :billingAccNum " +
            "          AND mobile_num= :mobileNum " +
            "          AND exempt_level='MO' " +
            "          AND e.billing_acc_num = mb.bill_accnt_num " +
            "          AND e.mobile_num = mb.service_num " +
            "          AND (:inDccMobileStatusList = 'ALL' OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, status_cd) > 0 OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, mb.status_cd || '/' || trim(mb.x_suspend_type)) > 0) " +
            "        UNION " +
            "        SELECT cust_acc_num AS custAccNum, " +
            "               billing_acc_num AS billingAccNum, " +
            "               :mobileNum AS mobileNum, " +
            "               module_code AS moduleCode, " +
            "               mode_id AS modeId, " +
            "               exempt_level AS exemptLevel, " +
            "               (SELECT b.x_con_full_name FROM billing_profile b WHERE b.ou_num = billing_acc_num) AS billingAccName, " +
            "               to_char(effective_dat,'YYYY/MM/DD') AS effectiveDate, " +
            "               to_char(end_dat,'YYYY/MM/DD') AS endDate, " +
            "               to_char(expire_dat,'YYYY/MM/DD') AS expireDate, " +
            "               cate_code AS cateCode, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_ADD' AND reason_code=e.add_reason) AS addReason, " +
            "               add_location AS addLocation, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_UPDATE' AND reason_code=e.update_reason) AS updateReason, " +
            "               update_location AS updateLocation, " +
            "               last_update_by AS lastUpdateBy, " +
            "               to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "               no_of_exempt AS noOfExempt, " +
            "               sent_interface_flag AS sentInterFlag, " +
            "               decode(mb.x_suspend_type, null, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus " +
            "        FROM dcc_exempt e, account_has_mobile mb " +
            "        WHERE billing_acc_num= :billingAccNum " +
            "          AND exempt_level='BA' " +
            "          AND e.billing_acc_num = mb.bill_accnt_num " +
            "          AND e.mobile_num = mb.service_num " +
            "          AND (:inDccMobileStatusList = 'ALL' OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, status_cd) > 0 OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, mb.status_cd || '/' || trim(mb.x_suspend_type)) > 0) " +
            " " +
            "        UNION " +
            " " +
            "        SELECT cust_acc_num AS custAccNum, " +
            "               :billingAccNum AS billingAccNum, " +
            "               :mobileNum AS mobileNum, " +
            "               module_code AS moduleCode, " +
            "               mode_id AS modeId, " +
            "               exempt_level AS exemptLevel, " +
            "               (SELECT b.x_con_full_name FROM billing_profile b WHERE b.ou_num = billing_acc_num) AS billingAccName, " +
            "               to_char(effective_dat,'YYYY/MM/DD') AS effectiveDate, " +
            "               to_char(end_dat,'YYYY/MM/DD') AS endDate, " +
            "               to_char(expire_dat,'YYYY/MM/DD') AS expireDate, " +
            "               cate_code AS cateCode, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_ADD' AND reason_code=e.add_reason) AS addReason, " +
            "               add_location AS addLocation, " +
            "               (SELECT reason_code || ' : ' || reason_description FROM dcc_reason WHERE reason_type='EXEMPT_UPDATE' AND reason_code=e.update_reason) AS updateReason, " +
            "               update_location AS updateLocation, " +
            "               last_update_by AS lastUpdateBy, " +
            "               to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "               no_of_exempt AS noOfExempt, " +
            "               sent_interface_flag AS sentInterFlag, " +
            "               decode(mb.x_suspend_type, null, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus " +
            "        FROM dcc_exempt e, account_has_mobile mb " +
            "        WHERE cust_acc_num= :custAccNum " +
            "          AND exempt_level='CA' " +
            "          AND e.billing_acc_num = mb.bill_accnt_num " +
            "          AND e.mobile_num = mb.service_num " +
            "          AND (:inDccMobileStatusList = 'ALL' OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, status_cd) > 0 OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, mb.status_cd || '/' || trim(mb.x_suspend_type)) > 0) " +
            "    ) aa " +
            ")  " +
            "WHERE rownumber >= :startRow AND rownumber <= :endRow  " +
            "ORDER BY exemptLevel DESC, moduleCode, modeId, custAccNum, billingAccNum, mobileNum  ",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByMobileAndBilling(
            @Param("mobileNum")String mobileNum,
            @Param("custAccNum")String custAccNum,
            @Param("billingAccNum")String billingAccNum,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);

    @Query(value = "select * from ( " +
            "    select  " +
            "    aa.*,  " +
            "    row_number() over ( " +
            "        order by  " +
            "        exemptLevel desc,  " +
            "        moduleCode,  " +
            "        modeId,  " +
            "        custAccNum,  " +
            "        billingAccNum,  " +
            "        mobileStatus,  " +
            "        mobileNum " +
            "    ) as rownumber  " +
            "    from ( " +
            "        select   " +
            "        cust_acc_num as custAccNum,  " +
            "        billing_acc_num as billingAccNum,  " +
            "        mobile_num as mobileNum,  " +
            "        module_code as moduleCode,  " +
            "        mode_id as modeId,  " +
            "        exempt_level as exemptLevel,   " +
            "        (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing') as billingAccName,   " +
            "        to_char(effective_dat,'YYYY/MM/DD') as effectiveDate,   " +
            "        to_char(end_dat,'YYYY/MM/DD') as endDate,    " +
            "        to_char(expire_dat,'YYYY/MM/DD') as expireDate,    " +
            "        cate_code as cateCode,    " +
            "        (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) as addReason,    " +
            "        add_location as addLocation,    " +
            "        (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) as updateReason,    " +
            "        update_location as updateLocation,  " +
            "        last_update_by as lastUpdateBy,    " +
            "        to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as lastUpdateDate,    " +
            "        no_of_exempt as noOfExempt,  " +
            "        sent_interface_flag as sentInterFaceFlag,  " +
            "        decode(mb.x_suspend_type,null,mb.status_cd,mb.status_cd || '/' || mb.x_suspend_type) as mobileStatus    " +
            "        from dcc_exempt e, account_has_mobile mb    " +
            "        where billing_acc_num= :billingAccNum   " +
            "        and e.billing_acc_num = mb.bill_accnt_num    " +
            "        and e.mobile_num = mb.service_num    " +
            "        and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)    " +
            "        UNION   " +
            "        select   " +
            "        cust_acc_num as custAccNum,  " +
            "        :billingAccNum as billingAccNum,   " +
            "        mobile_num as mobileNum,  " +
            "        module_code as moduleCode,  " +
            "        mode_id as modeId,  " +
            "        exempt_level as exemptLevel,   " +
            "        (select a.name from account a where a.ou_num = billing_acc_num and a.accnt_type_cd = 'Billing') as billingAccName,   " +
            "        to_char(effective_dat,'YYYY/MM/DD') as effectiveDate,   " +
            "        to_char(end_dat,'YYYY/MM/DD') as endDate,    " +
            "        to_char(expire_dat,'YYYY/MM/DD') as expireDate,    " +
            "        cate_code as cateCode,    " +
            "        (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code=e.add_reason) as addReason ,    " +
            "        add_location as addLocation,    " +
            "        (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) as updateReason,    " +
            "        update_location as updateLocation,  " +
            "        last_update_by as lastUpdateBy,  " +
            "        to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') as lastUpdateDate,    " +
            "        no_of_exempt as noOfExempt,  " +
            "        sent_interface_flag as sentInterFaceFlag,  " +
            "        mb.status_cd as mobileStatus    " +
            "        from dcc_exempt e, account_has_mobile mb    " +
            "        where cust_acc_num= :custAccNum    " +
            "        and exempt_level='CA'    " +
            "        and e.billing_acc_num = mb.bill_accnt_num    " +
            "        and e.mobile_num = mb.service_num    " +
            "        and (:inDccMobileStatusList = 'ALL' or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,status_cd) > 0 or DCCU_UTIL.FIND_LIST(:inDccMobileStatusList,MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0)    " +
            "    ) aa " +
            ")  " +
            "where rownumber >= :startRow and rownumber <= :endRow   " +
            "order by exemptLevel desc, moduleCode, modeId, custAccNum, billingAccNum, mobileNum ",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByBilling(
            @Param("custAccNum")String custAccNum,
            @Param("billingAccNum")String billingAccNum,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);


    @Query(value = "SELECT  * FROM ( " +
            " SELECT " +
            "  cust_acc_num AS custAccNum , " +
            "  billing_acc_num AS billingAccNum, " +
            "  mobile_num AS mobileNum, " +
            "  module_code AS moduleCode, " +
            "  mode_id AS modeId, " +
            "  exempt_level AS exemptLevel, " +
            "  ( " +
            "  SELECT " +
            "   a.name " +
            "  FROM " +
            "   account a " +
            "  WHERE " +
            "   a.ou_num = billing_acc_num " +
            "   AND a.accnt_type_cd = 'Billing' ) AS billingAccName, " +
            "  to_char(effective_dat, 'YYYY/MM/DD') AS effectiveDate , " +
            "  to_char(end_dat, 'YYYY/MM/DD') AS endDate, " +
            "  to_char(expire_dat, 'YYYY/MM/DD') AS expireDate, " +
            "  cate_code AS cateCode, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_ADD' " +
            "   AND reason_code = e.add_reason) AS addReason , " +
            "  add_location AS addLocation, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_UPDATE' " +
            "   AND reason_code = e.update_reason) AS updateReason, " +
            "  update_location AS updateLocation, " +
            "  last_update_by AS lastUpdateBy, " +
            "  to_char(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "  no_of_exempt AS noOfExempt, " +
            "  sent_interface_flag AS sentInterFaceFlag, " +
            "  decode(mb.x_suspend_type, NULL, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus, " +
            "  ROW_NUMBER() OVER ( " +
            "  ORDER BY exempt_Level DESC, " +
            "  module_Code, " +
            "  mode_Id, " +
            "  CUST_ACC_NUM , " +
            "  billing_acc_num , " +
            "  mobile_num , " +
            "  no_of_exempt) AS rownumber " +
            " FROM " +
            "  dcc_exempt e, " +
            "  account_has_mobile mb " +
            " WHERE " +
            "  cust_acc_num = :custAccNum " +
            "  AND e.billing_acc_num = mb.bill_accnt_num " +
            "  AND e.mobile_num = mb.service_num " +
            "  AND (:inDccMobileStatusList = 'ALL' " +
            "   OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "   status_cd) > 0 " +
            "    OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "    MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) ) " +
            "WHERE " +
            " rownumber >= :startRow " +
            " AND rownumber <= :endRow " +
            "ORDER BY " +
            " rownumber",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByCustAccNum(
            @Param("custAccNum")String custAccNum,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);

    @Query(value = "SELECT  * FROM ( " +
            " SELECT " +
            "  cust_acc_num AS custAccNum , " +
            "  billing_acc_num AS billingAccNum, " +
            "  mobile_num AS mobileNum, " +
            "  module_code AS moduleCode, " +
            "  mode_id AS modeId, " +
            "  exempt_level AS exemptLevel, " +
            "  ( " +
            "  SELECT " +
            "   a.name " +
            "  FROM " +
            "   account a " +
            "  WHERE " +
            "   a.ou_num = billing_acc_num " +
            "   AND a.accnt_type_cd = 'Billing' ) AS billingAccName, " +
            "  to_char(effective_dat, 'YYYY/MM/DD') AS effectiveDate , " +
            "  to_char(end_dat, 'YYYY/MM/DD') AS endDate, " +
            "  to_char(expire_dat, 'YYYY/MM/DD') AS expireDate, " +
            "  cate_code AS cateCode, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_ADD' " +
            "   AND reason_code = e.add_reason) AS addReason , " +
            "  add_location AS addLocation, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_UPDATE' " +
            "   AND reason_code = e.update_reason) AS updateReason, " +
            "  update_location AS updateLocation, " +
            "  last_update_by AS lastUpdateBy, " +
            "  to_char(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "  no_of_exempt AS noOfExempt, " +
            "  sent_interface_flag AS sentInterFaceFlag, " +
            "  decode(mb.x_suspend_type, NULL, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus, " +
            "  ROW_NUMBER() OVER ( " +
            "  ORDER BY exempt_Level DESC, " +
            "  module_Code, " +
            "  mode_Id, " +
            "  CUST_ACC_NUM , " +
            "  billing_acc_num , " +
            "  mobile_num , " +
            "  no_of_exempt) AS rownumber " +
            " FROM " +
            "  dcc_exempt e, " +
            "  account_has_mobile mb " +
            "  where ( :effectiveDateFrom is null or effective_dat >= to_date( :effectiveDateFrom,'YYYY/MM/DD'))   \n" +
            "  AND ( :effectiveDateTo is null or effective_dat <= to_date( :effectiveDateTo,'YYYY/MM/DD')) " +
            "  AND e.billing_acc_num = mb.bill_accnt_num " +
            "  AND e.mobile_num = mb.service_num " +
            "  AND (:inDccMobileStatusList = 'ALL' " +
            "   OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "   status_cd) > 0 " +
            "    OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "    MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) ) " +
            "WHERE " +
            " rownumber >= :startRow " +
            " AND rownumber <= :endRow " +
            "ORDER BY " +
            " rownumber",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByEffectiveDate(
            @Param("effectiveDateFrom")String effectiveDateFrom,
            @Param("effectiveDateTo")String effectiveDateTo,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);

    @Query(value = "SELECT  * FROM ( " +
            " SELECT " +
            "  cust_acc_num AS custAccNum , " +
            "  billing_acc_num AS billingAccNum, " +
            "  mobile_num AS mobileNum, " +
            "  module_code AS moduleCode, " +
            "  mode_id AS modeId, " +
            "  exempt_level AS exemptLevel, " +
            "  ( " +
            "  SELECT " +
            "   a.name " +
            "  FROM " +
            "   account a " +
            "  WHERE " +
            "   a.ou_num = billing_acc_num " +
            "   AND a.accnt_type_cd = 'Billing' ) AS billingAccName, " +
            "  to_char(effective_dat, 'YYYY/MM/DD') AS effectiveDate , " +
            "  to_char(end_dat, 'YYYY/MM/DD') AS endDate, " +
            "  to_char(expire_dat, 'YYYY/MM/DD') AS expireDate, " +
            "  cate_code AS cateCode, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_ADD' " +
            "   AND reason_code = e.add_reason) AS addReason , " +
            "  add_location AS addLocation, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_UPDATE' " +
            "   AND reason_code = e.update_reason) AS updateReason, " +
            "  update_location AS updateLocation, " +
            "  last_update_by AS lastUpdateBy, " +
            "  to_char(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "  no_of_exempt AS noOfExempt, " +
            "  sent_interface_flag AS sentInterFaceFlag, " +
            "  decode(mb.x_suspend_type, NULL, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus, " +
            "  ROW_NUMBER() OVER ( " +
            "  ORDER BY exempt_Level DESC, " +
            "  module_Code, " +
            "  mode_Id, " +
            "  CUST_ACC_NUM , " +
            "  billing_acc_num , " +
            "  mobile_num , " +
            "  no_of_exempt) AS rownumber " +
            " FROM " +
            "  dcc_exempt e, " +
            "  account_has_mobile mb " +
            " where ( :endDateFrom is null or end_dat>=to_date( :endDateFrom,'YYYY/MM/DD'))   \n" +
            "  and ( :endDateTo is null or end_dat<=to_date( :endDateTo,'YYYY/MM/DD'))   " +
            "  AND e.billing_acc_num = mb.bill_accnt_num " +
            "  AND e.mobile_num = mb.service_num " +
            "  AND (:inDccMobileStatusList = 'ALL' " +
            "   OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "   status_cd) > 0 " +
            "    OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "    MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) ) " +
            "WHERE " +
            " rownumber >= :startRow " +
            " AND rownumber <= :endRow " +
            "ORDER BY " +
            " rownumber",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByEndDate(
            @Param("endDateFrom")String endDateFrom,
            @Param("endDateTo")String endDateTo,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);


    @Query(value = "SELECT  * FROM ( " +
            " SELECT " +
            "  cust_acc_num AS custAccNum , " +
            "  billing_acc_num AS billingAccNum, " +
            "  mobile_num AS mobileNum, " +
            "  module_code AS moduleCode, " +
            "  mode_id AS modeId, " +
            "  exempt_level AS exemptLevel, " +
            "  ( " +
            "  SELECT " +
            "   a.name " +
            "  FROM " +
            "   account a " +
            "  WHERE " +
            "   a.ou_num = billing_acc_num " +
            "   AND a.accnt_type_cd = 'Billing' ) AS billingAccName, " +
            "  to_char(effective_dat, 'YYYY/MM/DD') AS effectiveDate , " +
            "  to_char(end_dat, 'YYYY/MM/DD') AS endDate, " +
            "  to_char(expire_dat, 'YYYY/MM/DD') AS expireDate, " +
            "  cate_code AS cateCode, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_ADD' " +
            "   AND reason_code = e.add_reason) AS addReason , " +
            "  add_location AS addLocation, " +
            "  ( " +
            "  SELECT " +
            "   reason_code || ' : ' || reason_description " +
            "  FROM " +
            "   dcc_reason " +
            "  WHERE " +
            "   reason_type = 'EXEMPT_UPDATE' " +
            "   AND reason_code = e.update_reason) AS updateReason, " +
            "  update_location AS updateLocation, " +
            "  last_update_by AS lastUpdateBy, " +
            "  to_char(last_update_dtm, 'YYYY/MM/DD HH24:Mi:SS') AS lastUpdateDate, " +
            "  no_of_exempt AS noOfExempt, " +
            "  sent_interface_flag AS sentInterFaceFlag, " +
            "  decode(mb.x_suspend_type, NULL, mb.status_cd, mb.status_cd || '/' || mb.x_suspend_type) AS mobileStatus, " +
            "  ROW_NUMBER() OVER ( " +
            "  ORDER BY exempt_Level DESC, " +
            "  module_Code, " +
            "  mode_Id, " +
            "  CUST_ACC_NUM , " +
            "  billing_acc_num , " +
            "  mobile_num , " +
            "  no_of_exempt) AS rownumber " +
            " FROM " +
            "  dcc_exempt e, " +
            "  account_has_mobile mb " +
            "    where ( :expireDateFrom is null or expire_dat>=to_date( :expireDateFrom,'YYYY/MM/DD'))   \n" +
            "       and ( :expireDateTo is null or expire_dat<=to_date( :expireDateTo,'YYYY/MM/DD'))  "+
            "  AND e.billing_acc_num = mb.bill_accnt_num " +
            "  AND e.mobile_num = mb.service_num " +
            "  AND (:inDccMobileStatusList = 'ALL' " +
            "   OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "   status_cd) > 0 " +
            "    OR DCCU_UTIL.FIND_LIST(:inDccMobileStatusList, " +
            "    MB.STATUS_CD || '/' || trim(mb.x_suspend_type)) > 0) ) " +
            "WHERE " +
            " rownumber >= :startRow " +
            " AND rownumber <= :endRow " +
            "ORDER BY " +
            " rownumber",nativeQuery = true)
    List<DcExemptCurrentDtoMapping> getExemptByExpireDate(
            @Param("expireDateFrom")String expireDateFrom,
            @Param("expireDateTo")String expireDateTo,
            @Param("inDccMobileStatusList")String inDccMobileStatusList,
            @Param("startRow")Integer startRow,
            @Param("endRow")Integer endRow);
}
