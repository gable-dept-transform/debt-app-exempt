package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class DccReportExemptCriteriaId implements Serializable {

    @Column(name = "REPORT_ID")
    private String reportId;

    @Column(name = "REPORT_SEQ")
    private String reportSeq;
}