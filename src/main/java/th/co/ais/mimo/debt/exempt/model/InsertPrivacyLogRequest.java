package th.co.ais.mimo.debt.exempt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertPrivacyLogRequest {
    private String username;
    private String locationCode;
    private String referenceType;
    private String referenceValue;
    private String functionName;
    private String dataPrivacyField;
    private String system;
    private String createdBy;
    private String updatedBy;

}
