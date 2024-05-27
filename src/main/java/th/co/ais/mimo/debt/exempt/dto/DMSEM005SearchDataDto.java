package th.co.ais.mimo.debt.exempt.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM005SearchDataDto {
    private String modeId;
    private String preAssignId;
    private Date preAssignDate;
    private Number criteriaId;
    private String criteriaDesc;
    private String companyCode;
    private String billCycleList;
    private String collectionSegment;
    private String baStatusList;
    private String mobileStatusList;
    private BigDecimal arBalanceFrom;
    private BigDecimal arBalanceTo;
    private Date registerDateFrom;
    private Date registerDateTo;
    private Date baStatusDateFrom;
    private Date baStatusDateTo;
    private Date mobileStatusDateFrom;
    private Date mobileStatusDateTo;
    private String creditLimitFlag;
    private String creditLimitReason;
    private String creditLimitSet;
    private String cateSubCateList;
    private String exemptAction;
    private String exemptModule;
    private String exemptLevel;
    private String exemptMode;
    private String exemptReasonList;
    private Date effectiveDate;
    private Date endDate;
    private Number duration;
    private String autoAssignFlag;
    private String region;
    private String province;
    private String aumphur;
    private String tumbol;
    private String zipCode;
    private String criteriaType;
    private String processStatus;
    
    private String assignId;
    private Date assignDate;
    private String assignStatus;
    private String lastUpdateBy;
    private Date lastUpdateDate;
    private Date confirmDate;
    
}
