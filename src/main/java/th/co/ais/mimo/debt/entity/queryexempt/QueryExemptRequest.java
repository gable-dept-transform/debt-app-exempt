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

    private String effectiveDateFrom;

    private String effectiveDateTo;

    private String endDateFrom;

    private String endDateTo;

    private String expireDateFrom;

    private String expireDateTo;

    private String mobileStatus;

    private String exemptAction;

    private String mode;

    private Integer startRow;

    private Integer endRow;

    private String effectiveDateLast;
}
