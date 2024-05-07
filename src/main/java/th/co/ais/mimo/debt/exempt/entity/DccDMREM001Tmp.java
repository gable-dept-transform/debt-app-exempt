package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DCC_DMREM001_TMP")
public class DccDMREM001Tmp implements Serializable {

    @Id
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "REPORT_SEQ")
    private Long reportSeq;

    @Column(name = "COMPANY_CODE")
    private String companyCode;

    @Column(name = "MODULE_CODE")
    private String moduleCode;

    @Column(name = "MODE_ID")
    private String modeId;

    @Column(name = "BILLING_ACC_NUM")
    private String billingAccNum;

    @Column(name = "CUST_ACC_NUM")
    private String custAccNum;

    @Column(name = "MOBILE_NUM")
    private String mobileNum;

    @Column(name = "BA_NAME")
    private String baName;

    @Column(name = "CA_NAME")
    private String caName;

    @Column(name = "CATE_CODE")
    private String cateCode;

    @Column(name = "CATE_DESC")
    private String cateDesc;

    @Column(name = "EXEMPT_LEVEL")
    private String exemptLevel;

    @Column(name = "EXEMPT_ACTION")
    private String exemptAction;

    @Column(name = "ADD_REASON_CODE")
    private String addReasonCode;

    @Column(name = "ADD_REASON_DESC")
    private String addReasonDesc;

    @Column(name = "ADD_LOCATION")
    private String addLocation;

    @Column(name = "UPDATE_REASON_CODE")
    private String updateReasonCode;

    @Column(name = "UPDATE_REASON_DESC")
    private String updateReasonDesc;

    @Column(name = "UPDATE_LOCATION")
    private String updateLocation;

    @Column(name = "EFFECTIVE_DAT")
    private Date effectiveDat;

    @Column(name = "END_DAT")
    private Date endDat;

    @Column(name = "EXPIRE_DAT")
    private Date expireDat;

    @Column(name = "CURR_BA_STATUS")
    private String currBaStatus;

    @Column(name = "CURR_DEBT_MNY")
    private Double currDebtMny;

    @Column(name = "NO_OF_EXEMPT")
    private Integer noOfExempt;

    @Column(name = "INVOICE_CNT")
    private Integer invoiceCnt;

    @Column(name = "FIRST_AR_DAT")
    private Date firstArDat;

    @Column(name = "EXEMPT_BY")
    private String exemptBy;

    @Column(name = "EXEMPT_DTM")
    private Date exemptDtm;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Column(name = "LAST_UPDATE_DTM")
    private Date lastUpdateDtm;

    @Column(name = "LINE_NO")
    private Integer lineNo;

    @Column(name = "ADD_LOCATION_DESC")
    private String addLocationDesc;

    @Column(name = "CURR_ID_MNY")
    private Double currIdMny;

    @Column(name = "CATE_TYPE")
    private String cateType;

    @Column(name = "SUBCATE_TYPE")
    private String subcateType;

    @Column(name = "REGISTER_DATE")
    private Date registerDate;

    @Column(name = "REGISTER_MONTH")
    private String registerMonth;

    @Column(name = "FIRST_BILL_FLAG")
    private String firstBillFlag;

    @Column(name = "DURATION_DAY")
    private Integer durationDay;

    @Column(name = "COLLECTION_SEGMENT")
    private String collectionSegment;

    @Column(name = "AGENT_CODE")
    private String agentCode;

    @Column(name = "INDUSTRY_TYPE")
    private String industryType;

    @Column(name = "SALES_REP_NAME")
    private String salesRepName;

    @Column(name = "COMPANY_TYPE")
    private String companyType;

    @Column(name = "SALES_REP_MST")
    private String salesRepMst;

    // Constructors, getters, and setters
}
