package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DccPrivacyLogId implements Serializable {
    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "LOCATION_CD")
    private String locationCode;

    @Column(name = "REFERENCE_TYPE")
    private String referenceType;

    @Column(name = "REFERENCE_VALUE")
    private String referenceValue;

    @Column(name = "FUNCTION")
    private String functionName;

    @Column(name = "DATA_PRIVACY_FIELD")
    private String dataPrivacyField;

    @Column(name = "ACCESS_DATE")
    private Date accessDate;

    @Column(name = "SYSTEM")
    private String system;

    @Column(name = "CREATED")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED")
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
