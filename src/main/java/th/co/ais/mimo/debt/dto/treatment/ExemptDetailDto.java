package th.co.ais.mimo.debt.dto.treatment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExemptDetailDto {
    private String custAccNum;
    private String billingAccNum;
    private String mobileNum;
    private String moduleCode;
    private String modeId;
    private String exemptLevel;
    private String billingAccName;
    private String effectiveDate;
    private String endDate;
    private String expireDate;
    private String cateCode;
    private String addReason;
    private String addLocation;
    private String updateReason;
    private String updateLocation;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private long noOfExempt;
    private String sentInterfaceFlag;
    private long rowNumber;
}
