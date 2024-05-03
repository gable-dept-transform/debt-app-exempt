package th.co.ais.mimo.debt.exempt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchTreatmentDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String custAccNum;
    private String billingAccNum;
    private String name;
    private String baStatus;
    private String serviceNum;
    private String mobileStatus;
    private Date xStatusDate;
    private String wocFlag;
    private BigDecimal limitMny;
    private String companyCode;
    private String customerId;
    private String xIdType;
    private String billStyle;
    private String whtReq;
    private String paymentMethod;
    private String paymtTerm;
    private String billCycle;
    private String creditTerm;
    private BigDecimal debtMny;
    private String accountName;
    private String saNum;
    private String saStatus;
    private String companyType;
    private String caName;
    private String mainPromo;
    private String noPrintBillFlag;
    private String salesRep;
    private String salesRepMst;
    private String salesRepMobile;
    private String collectionSegment;

    public SearchTreatmentDto(String custAccNum, String billingAccNum, String name, String baStatus, String serviceNum, String mobileStatus) {
        this.custAccNum = custAccNum;
        this.billingAccNum = billingAccNum;
        this.name = name;
        this.baStatus = baStatus;
        this.serviceNum = serviceNum;
        this.mobileStatus = mobileStatus;
    }
}
