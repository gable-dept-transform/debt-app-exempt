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
@Table(name = "CP_LOCATION_MASTER")
public class CpLocationMaster implements Serializable {

    @Id
    @Column(name = "ROW_ID", length = 50)
    private String rowId;

    @Column(name = "LOCATION_ID")
    private Integer locationId;

    @Column(name = "NAME", length = 150)
    private String name;

    @Column(name = "ADDRESS1", length = 100)
    private String address1;

    @Column(name = "ADDRESS2", length = 100)
    private String address2;

    @Column(name = "CONTACT_NAME", length = 100)
    private String contactName;

    @Column(name = "TYPE", length = 50)
    private String type;

    @Column(name = "SUB_TYPE", length = 50)
    private String subType;

    @Column(name = "REGION", length = 50)
    private String region;

    @Column(name = "SUB_REGION", length = 50)
    private String subRegion;

    @Column(name = "AIS_CLASS", length = 50)
    private String aisClass;

    @Column(name = "DPC_CLASS", length = 50)
    private String dpcClass;

    @Column(name = "AUTO_CLOSE_FLAG", length = 1)
    private String autoCloseFlag;

    @Column(name = "PAYMENT_CHANNEL_ID")
    private Integer paymentChannelId;

    @Column(name = "USER_ID", length = 10)
    private String userId;

    @Column(name = "USER_DATE")
    private Date userDate;

    @Column(name = "LAST_UPD_BY", length = 10)
    private String lastUpdatedBy;

    @Column(name = "LAST_UPD_DATE")
    private Date lastUpdatedDate;

    @Column(name = "ADDRESS_NUMBER", length = 30)
    private String addressNumber;

    @Column(name = "BUILDING", length = 80)
    private String building;

    @Column(name = "FLOOR", length = 10)
    private String floor;

    @Column(name = "MOOBAN", length = 50)
    private String mooban;

    @Column(name = "SOI", length = 50)
    private String soi;

    @Column(name = "STREET", length = 50)
    private String street;

    @Column(name = "TUMBOL", length = 50)
    private String tumbol;

    @Column(name = "AMPHUR", length = 50)
    private String amphur;

    @Column(name = "PROVINCE", length = 50)
    private String province;

    @Column(name = "COUNTRY", length = 50)
    private String country;

    @Column(name = "POST_CODE", length = 50)
    private String postCode;

    @Column(name = "NET_REGION", length = 50)
    private String netRegion;

    @Column(name = "AIS_STATUS", length = 50)
    private String aisStatus;

    @Column(name = "DPC_STATUS", length = 50)
    private String dpcStatus;

    @Column(name = "DPC_TRAD_STATUS", length = 50)
    private String dpcTradStatus;

    @Column(name = "TELEPHONE", length = 10)
    private String telephone;

    @Column(name = "FAX", length = 1)
    private Character fax;

    @Column(name = "LOAD_DATE")
    private Date loadDate;

    @Column(name = "GRP_COMPANY_ID", length = 15)
    private String grpCompanyId;

    @Column(name = "PR_ADDR_ID", length = 50)
    private String prAddrId;

    @Column(name = "HOLD_FLAG", length = 1)
    private Character holdFlag;

    @Column(name = "NO_MIN", length = 1)
    private Character noMin;

    @Column(name = "CAP_MAX_FLAG", length = 1)
    private Character capMaxFlag;

    @Column(name = "AIS_STAFF", length = 15)
    private String aisStaff;

    @Column(name = "OLD_ROW_ID", length = 1)
    private Character oldRowId;

    @Column(name = "PAYSTATION_FLAG", length = 1)
    private String paystationFlag;

    @Column(name = "PAYIN_DIRECT_FLG", length = 1)
    private String payinDirectFlag;

    @Column(name = "BUSINESS_UNIT", length = 50)
    private String businessUnit;

    // Constructors, getters, and setters
}
