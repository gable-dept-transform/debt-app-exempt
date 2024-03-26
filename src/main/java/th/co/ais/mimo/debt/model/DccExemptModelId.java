package th.co.ais.mimo.debt.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
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
