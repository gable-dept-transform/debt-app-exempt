package th.co.ais.mimo.debt.exempt.model;

import java.util.Calendar;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchReportDataDMREM001Request {
    private String reportId;
    private String reportSeq;
    private String reportStatus;
    private String criteriaBy;
    private Calendar criteriaDateFrom;
    private Calendar criteriaDateTo;
    private Calendar processDateFrom;
    private Calendar processDateTo;
}
