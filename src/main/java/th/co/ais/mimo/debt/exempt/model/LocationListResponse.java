package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.ais.mimo.debt.exempt.dto.CpLocationDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationListResponse {
    private List<CpLocationDto> resultList;
    private String errorMsg;
}
