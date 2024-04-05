package th.co.ais.mimo.debt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingAccDto {
    private String baNum;
    private String baName;
    private String mobileStatus;
}
