package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DCC_CALENDAR_TRANSACTION")
public class DccCalendarTransaction implements Serializable {

    @Id
    @Column(name = "MODE_ID")
    private String modeId;

    @Column(name = "CRITERIA_ID")
    private Integer criteriaId;

    @Column(name = "RUN_DAT")
    private Date runDate;

    @Column(name = "JOB_TYPE")
    private String jobType;

    @Column(name = "SET_SEQ")
    private Integer setSeq;

    @Column(name = "PREJOB_ID")
    private String preJobId;

    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "RUN_RESULT_FLAG")
    private String runResultFlag;

    @Column(name = "RUN_RESULT_DESC")
    private String runResultDesc;

    @Column(name = "JOB_DESCRIPTION")
    private String jobDescription;

    @Column(name = "PRIORITY_NO")
    private Integer priorityNo;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Column(name = "LAST_UPDATE_DTM")
    private Date lastUpdateDate;
}
