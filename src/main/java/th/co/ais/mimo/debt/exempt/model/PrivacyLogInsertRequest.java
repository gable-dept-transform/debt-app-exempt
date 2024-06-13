package th.co.ais.mimo.debt.exempt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivacyLogInsertRequest {
    private String refType;
    private String refValue;
    private String functionName;
    private String privacyField;
    private String system;
}
