package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 @Embeddable
 public class DccBosExemptId {
    @Column(name = "billing_acc_num")
    private String billing_acc_num;

    @Column(name = "mobile_num")
    private String mobileNum;

    @Column(name = "module_code")
    private String  moduleCode;

    @Column(name = "mode_id")
    private String modeId;

    @Column(name = "exempt_level")
    private String exemptLevel;

    @Column(name = "effective_dat")
    @Temporal(TemporalType.DATE)
    private Date effectiveDat;

    @Column(name = "end_dat")
    @Temporal(TemporalType.DATE)
    private Date endDat;

    @Column(name = "oper_type")
    private String operType;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "last_update_dtm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDtm;
}
