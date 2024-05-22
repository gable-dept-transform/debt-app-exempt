package th.co.ais.mimo.debt.exempt.model;

import java.math.BigDecimal;
import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveORUpdateDMREM001Request {
    private String operationMode;
    private Integer location;
    private String reportId;
    private String reportSeq;
    private String reportStatus;
    private String companyCodeList;
    private String exemptStatus;
    private String exemptFormatList;
    private String groupForSummary;
    private String exemptModeList;
    private String exemptLevelList;
    private String exemptCategoryList;
    private String reasonCodeList;
    private String exemptActionList;
    private String mobileStatusList;
    private String locationList;
    private Calendar processDate;
    private Calendar effectiveDateFrom;
    private Calendar effectiveDateTo;
    private Calendar endDateFrom;
    private Calendar endDateTo;
    private Calendar expireDateFrom;
    private Calendar expireDateTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String catSubCateList;
    private Boolean selectAllCate;
    private Integer monthPeriod;
    private Integer exemptTime;
    private Integer durationOver;
    private String username;
}
