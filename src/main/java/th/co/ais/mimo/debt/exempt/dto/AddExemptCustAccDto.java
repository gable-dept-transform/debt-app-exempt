package th.co.ais.mimo.debt.exempt.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AddExemptCustAccDto {
    private String custAccNum;
    private String billingAccNum;
    private String baStatus;
    private String serviceNum;
    private String mobileStatus;
    // result to show when add complete
    private String result;
}
