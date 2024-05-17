package th.co.ais.mimo.debt.exempt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.CustAccDto;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteExemptResponse {
    private String errorMsg;
    private String responseCode;

    private List<ExemptDetailDto> successList;
    private List<ExemptDetailDto> errorList;
}
