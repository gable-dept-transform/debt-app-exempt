package th.co.ais.mimo.debt.exempt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDMREM001 implements Serializable {
    private String reportId;
    private String reportSeq;
    private Date criteriaDate;
    private String criteriaBy;
    private Integer criteriaLocation;
    private Date processDate;
    private String reportStatus;
    private String reportStatusDesc;
    private String exemptStatus;
    // private String exemptStatusDesc;
    private String exemptFormat;
    private String groupByForSummary;
    private String companyCode;
    private String modeIdList;
    private String exemptLevelList;
    private Date effectiveDateFrom;
    private Date effectiveDateTo;
    private Date endDateFrom;
    private Date endDateTo;
    private Date expireDateFrom;
    private Date expireDateTo;
    private String exemptCategory;
    private String exemptAction;
    private String mobileStatusList;
    private String locationCodeList;
    private BigDecimal debtMnyFrom;
    private BigDecimal debtMnyTo;
    private String addReason;
    private String catSubCatList;
    private Integer monthPeriod;
    private Integer dayOver;
    private Integer durationOver;
    private Integer locationFrom;
    private Integer locationTo;
}
