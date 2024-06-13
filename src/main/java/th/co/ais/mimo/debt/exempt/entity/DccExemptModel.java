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
@Table(name = "DCC_EXEMPT")
public class DccExemptModel {

    @Id
    private DccExemptModelId id;

    @Column(name = "MODULE_CODE")
    private String moduleCode;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_DAT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DAT")
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRE_DAT")
    private Date expireDate;

    @Column(name = "CATE_CODE")
    private String cateCode;

    @Column(name = "ADD_LOCATION")
    private Integer addLocation;


    @Column(name = "UPDATE_LOCATION")
    private Integer updateLocation;


    @Column(name = "ADD_REASON")
    private String addReason;


    @Column(name = "UPDATE_REASON")
    private String updateReason;


    @Column(name = "NO_OF_EXEMPT")
    private Long noOfExempt;

    @Column(name = "SENT_INTERFACE_FLAG")
    private String sentIntefaceFlag;


    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DTM")
    private Date lastUpdateDtm;

    @Column(name = "SENT_BOS_FLAG")
    private String sentBosFlag;

    @Column(name = "SO_NBR")
    private String soNbr;
}
