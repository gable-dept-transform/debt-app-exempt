package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ACCOUNT_HAS_MOBILE")
public class AccHasMobileModel {

    @EmbeddedId
    AccHasMobileModelId id;

    @Column(name = "OWNER_ACCNT_ID")
    private String ownerAccntId;

    @Column(name = "STATUS_CD")
    private String statusCd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "X_STATUS_DATE")
    private Date xStatusDate;

    @Column(name = "X_SIM_SR_NUM")
    private String xSimSrNum;

    @Column(name = "X_MOBILE_NUM_REGION")
    private String xMobileNumRegion;

    @Column(name = "X_SUSPEND_TYPE")
    private String xSuspendType;

    @Column(name = "COLLECTION_SEGMENT")
    private String collectionSegment;

    @Column(name = "MARKET_CLASS_CD")
    private String marketClassCd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPD")
    private Date lastUpd;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "X_OUTGOING_DATE")
    private Date xOutgoingDate;

    @Column(name = "PREFIX_FLAG")
    private String prefixFlag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PREFIX_DATE")
    private Date prefixDate;

    @Column(name = "COMPANY_TYPE")
    private String companyType;

    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    @Column(name = "CHARGE_TYPE")
    private String chargeType;

    @Column(name = "MAIN_PROMO_BRAND")
    private String mainPromoBrand;

    @Column(name = "CIS_TYPE")
    private String cisType;

    @Column(name = "MIGRATE_TYPE")
    private String migrateType;

    @Column(name = "RELOCATION_STATUS")
    private String relocationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RELOCATION_DATE")
    private Date relocationDate;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PROD_NAME")
    private String prodName;

    @Column(name = "PROD_LINE")
    private String prodLine;

}
