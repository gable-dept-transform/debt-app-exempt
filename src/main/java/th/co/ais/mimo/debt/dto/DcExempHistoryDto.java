package th.co.ais.mimo.debt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DcExempHistoryDto {

    private String custAccNum;
    private String billingAccNum;
    private String mobileNum;
    private String moduleCode;
    private String modeId;
    private String exemptLevel;
    private String billingAccName;
    private String effectiveDat;
    private String endDate;
    private String expireDate;
    private String addReason;
    private Integer addLocation;
    private String updateReason;
    private Integer updateLocation;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private Integer noOfExempt;
    private Integer exemptSeq;
    private String cateCode;
    private String actionType;
    private String mobileStatus;
    private Integer rowNumber;
}
