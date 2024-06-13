package th.co.ais.mimo.debt.exempt.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.AddExemptCustAccDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddExemptRequest {

    private String exemptBy;//mode/cate
    private String module;
    private String exemptLevel;
    private String exemptMode;// A,B
    private String reason;
    private String effectiveDate;
    private String endDate;
    private Integer duration;
    private String cateCode;

    private List<AddExemptCustAccDto> custAccNo;

    private List<DccExemptCateDetailDto> exemptCateDetails;
}
