package th.co.ais.mimo.debt.entity.queryexempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryExemptRequest {

    private String custAccNum;

    private String billingAccNum;

    private String mobileNum;
}
