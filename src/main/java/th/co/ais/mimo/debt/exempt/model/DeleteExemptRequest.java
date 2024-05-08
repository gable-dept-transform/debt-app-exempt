package th.co.ais.mimo.debt.exempt.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteExemptRequest {

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
