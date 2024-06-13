package th.co.ais.mimo.debt.exempt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.AddExemptCustAccDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddExemptResponse {
    private String errorMsg;
    private String responseCode;

    private String exemptLevel;
    private String custAccNum;
    private String mode;
    private Long countExempt;

    private List<AddExemptCustAccDto> custAccNo;
}
