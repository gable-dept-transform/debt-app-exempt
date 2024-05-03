package th.co.ais.mimo.debt.exempt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DcExemptCurrentDto implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private Integer addLocation;
    private String updateReason;
    private Integer updateLocation;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private Integer noOfExempt;
    private String sentInterFaceFlag;
    private String mobileStatus;
    private Integer rowNumber;

}
