package th.co.ais.mimo.debt.exempt.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBillAccNumByMobileNumResp {
    private List<DMSEM004GetBaByMobileNumDto> resultList;
    private String errorMsg;
}
