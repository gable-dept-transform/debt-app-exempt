package th.co.ais.mimo.debt.exempt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenReportSeqDto {
    private String reportMaxSeq;
    private String reportName;
    private String reportDatabase;
}
