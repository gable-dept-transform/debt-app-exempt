package th.co.ais.mimo.debt.exempt.entity;

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
public class DccExemptHistoryId {

    @Column(name = "CUST_ACC_NUM")
    private String custAccNum;

    @Column(name = "BILLING_ACC_NUM")
    private String billingAccNum;

    @Column(name = "MOBILE_NUM")
    private String mobileNum;

    @Column(name = "MODE_ID")
    private String modeId;

    @Column(name = "NO_OF_EXEMPT")
    private Long noOfExempt;

    @Column(name = "EXEMPT_SEQ")
    private Long exemptSeq;

    @Column(name = "ACTION_TYPE")
    private String actionType;
}

