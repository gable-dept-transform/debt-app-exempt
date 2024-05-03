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
@Table(name = "DCC_EXEMPT_HISTORY")
public class DccExemptHistory {

    @Id
    private DccExemptHistoryId id;

    @Column(name = "MODULE_CODE")
    private String moduleCode;
    @Column(name = "EXEMPT_LEVEL")
    private String exemptLevel;

    @Column(name = "EFFECTIVE_DAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDat;

    @Column(name = "END_DAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDat;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRE_DAT")
    private Date expireDat;

    @Column(name = "CATE_CODE")
    private String cateCode;

    @Column(name = "ADD_LOCATION")
    private Long addLocation;

    @Column(name = "UPDATE_LOCATION")
    private Long updateLocation;

    @Column(name = "ADD_REASON")
    private String addReason;

    @Column(name = "UPDATE_REASON")
    private String updateReason;

    @Column(name = "SENT_INTERFACE_FLAG")
    private String sentInterfaceFlag;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DTM")
    private Date lastUpdateDtm;

    @Column(name = "CHECK_FLAG")
    private String checkFlag;
}

