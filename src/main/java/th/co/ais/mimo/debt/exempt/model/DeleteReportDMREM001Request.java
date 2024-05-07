package th.co.ais.mimo.debt.exempt.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteReportDMREM001Request implements Serializable {
    private String reportId;
    private String reportSeq;
    private String username;
}
