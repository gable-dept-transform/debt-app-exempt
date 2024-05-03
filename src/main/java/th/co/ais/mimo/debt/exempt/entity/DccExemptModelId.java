package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DccExemptModelId implements Serializable {

    @Column(name = "CUST_ACC_NUM")
    private String custAccNum;

    @Column(name = "BILLING_ACC_NUM")
    private String billingAccNum;

    @Column(name = "MOBILE_NUM")
    private String mobileNum;

    @Column(name = "MODE_ID")
    private String modeId;

    @Column(name = "EXEMPT_LEVEL")
    private String exemptLevel;
}
