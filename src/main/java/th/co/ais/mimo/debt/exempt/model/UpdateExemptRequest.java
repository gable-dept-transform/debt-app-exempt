package th.co.ais.mimo.debt.exempt.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.AddExemptCustAccDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateExemptRequest {

    private String custAccNo;
    private String billingAccNo;
    private String mobileNo;
    private String module;
    private String exemptLevel;
    private String mode;
    private String reason;
    private String endDate;
    private Long noOfExempt;

}
