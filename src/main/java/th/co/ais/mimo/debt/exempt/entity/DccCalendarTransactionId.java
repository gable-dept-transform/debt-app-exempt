package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

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
public class DccCalendarTransactionId implements Serializable {

    @Column(name = "MODE_ID")
    private String modeId;

    @Column(name = "CRITERIA_ID")
    private Long criteriaId;

    @Column(name = "RUN_DAT")
    private Date runDate;

    @Column(name = "JOB_TYPE")
    private String jobType;

    @Column(name = "SET_SEQ")
    private Long setSeq;
}
