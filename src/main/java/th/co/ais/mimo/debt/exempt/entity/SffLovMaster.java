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
@Table(name = "SFF_LOV_MASTER")
public class SffLovMaster implements Serializable {

    @Id
    @Column(name = "ROW_ID", nullable = false)
    private String rowId;

    @Column(name = "PAR_ROW_ID")
    private String parRowId;

    @Column(name = "LOV_TYPE")
    private String lovType;

    @Column(name = "LOV_NAME")
    private String lovName;

    @Column(name = "DISPLAY_VAL")
    private String displayVal;

    @Column(name = "LOV_VAL1")
    private String lovVal1;

    @Column(name = "LOV_VAL2")
    private String lovVal2;

    @Column(name = "LOV_VAL3")
    private String lovVal3;

    @Column(name = "LOV_VAL4")
    private String lovVal4;

    @Column(name = "LOV_VAL5")
    private String lovVal5;

    @Column(name = "ACTIVE_FLG")
    private String activeFlg;

    @Column(name = "TEXT_DESC")
    private String textDesc;

    @Column(name = "ORDER_BY")
    private Integer orderBy;

    @Column(name = "MODIFICATION_NUM")
    private Integer modificationNum;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_UPD")
    private Date lastUpd;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "GROUP_TYPE")
    private String groupType;

    @Column(name = "LOV_VAL6")
    private String lovVal6;

    @Column(name = "LOV_VAL7")
    private String lovVal7;

    @Column(name = "LOV_VAL8")
    private String lovVal8;

    @Column(name = "LOV_VAL9")
    private String lovVal9;

    // Constructors, getters, and setters
}