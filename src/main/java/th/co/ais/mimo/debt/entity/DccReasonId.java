package th.co.ais.mimo.debt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class DccReasonId implements Serializable {

    @Column(name = "reason_code")
    private String reasonCode;

    @Column(name = "reason_type")
    private String reasonType;
}
