package th.co.ais.mimo.debt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DcExemptDto {
    private String custAccNum;
    private String billingAccNum;
    private String mobileNum;
    private String moduleCode;
    private String modeId;
    private String exemptLevel;
    private String billingAccName;
    private String effectiveDat;
    private String endDat;
    private String expireDat;
    private String cateCode;
    private String addReason;
    private Integer addLocation;
    private String updateReason;
    private Integer updateLocation;
    private String lastUpdateBy;
    private String lastUpdateDtm;
    private Integer noOfExempt;
    private String sentInterfaceFlag;

}
