package th.co.ais.mimo.debt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "DCC_REASON")
public class DccReason {

    @EmbeddedId
    private DccReasonId id;

    @Column(name = "reason_description")
    private String reasonDescription;
}
