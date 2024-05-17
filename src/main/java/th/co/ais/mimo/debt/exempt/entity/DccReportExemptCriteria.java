package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "DCC_REPORT_EM_CRITERIA")
public class DccReportExemptCriteria implements Serializable {

	@EmbeddedId
    DccReportExemptCriteriaId id;

    @Column(name = "CRITERIA_DTM")
    private Date criteriaDtm;

    @Column(name = "CRITERIA_BY")
    private String criteriaBy;

    @Column(name = "CRITERIA_LOCATION")
    private Integer criteriaLocation;

    @Column(name = "PROCESS_DAT")
    private Date processDate;

    @Column(name = "START_DTM")
    private Date startDate;

    @Column(name = "STOP_DTM")
    private Date stopDate;

    @Column(name = "REPORT_STATUS")
    private String reportStatus;

    @Column(name = "COMPANY_CODE")
    private String companyCodeList;

    @Column(name = "MODE_ID_LIST")
    private String exemptModeList;

    @Column(name = "EXEMPT_LEVEL_LIST")
    private String exemptLevelList;

    @Column(name = "EFFECTIVE_DAT_FROM")
    private Date effectiveDateFrom;

    @Column(name = "EFFECTIVE_DAT_TO")
    private Date effectiveDateTo;

    @Column(name = "END_DAT_FROM")
    private Date endDateFrom;

    @Column(name = "END_DAT_TO")
    private Date endDateTo;

    @Column(name = "EXPIRE_DAT_FROM")
    private Date expireDateFrom;

    @Column(name = "EXPIRE_DAT_TO")
    private Date expireDateTo;

    @Column(name = "LOCATION_FROM")
    private Integer locationFrom;

    @Column(name = "LOCATION_TO")
    private Integer locationTo;

    @Column(name = "DEBT_MNY_FROM")
    private BigDecimal amountFrom;

    @Column(name = "DEBT_MNY_TO")
    private BigDecimal amountTo;

    @Column(name = "ADD_REASON_LIST")
    private String reasonList;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Column(name = "LAST_UPDATE_DTM")
    private Date lastUpdateDtm;

    @Column(name = "EXEMPT_STATUS")
    private String exemptStatus;

    @Column(name = "EXEMPT_FORMAT")
    private String exemptFormat;

    @Column(name = "CATE_CODE")
    private String exmeptCategory;

    @Column(name = "GROUP_BY_LIST")
    private String groupForSummary;

    @Column(name = "CATE_SUBCATE_LIST")
    private String cateSubCateList;

    @Column(name = "MOBILE_STATUS_LIST")
    private String mobileStatusList;

    @Column(name = "DEBT_AGE_FROM")
    private BigDecimal debtAgeFrom;

    @Column(name = "DEBT_AGE_TO")
    private BigDecimal debtAgeTo;

    @Column(name = "CA_DEBT_MNY_FROM")
    private BigDecimal caDebtMnyFrom;

    @Column(name = "CA_DEBT_MNY_TO")
    private BigDecimal caDebtMnyTo;

    @Column(name = "BA_DEBT_MNY_FROM")
    private BigDecimal baDebtMnyFrom;

    @Column(name = "BA_DEBT_MNY_TO")
    private BigDecimal baDebtMnyTo;

    @Column(name = "LOCATION_CODE_LIST")
    private String locationList;

    @Column(name = "MONTH_PERIOD")
    private Integer monthPeriod;

    @Column(name = "DAY_OVER")
    private Integer dayOver;

    @Column(name = "DURATION_OVER")
    private Integer durationOver;

    @Column(name = "PAYMENT_TYPE_LIST")
    private String exemptActionList;
}