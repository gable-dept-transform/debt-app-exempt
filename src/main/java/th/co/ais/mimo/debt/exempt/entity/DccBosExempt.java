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
@Table(name = "DCC_BOS_EXEMPT")
public class DccBosExempt {
    @EmbeddedId
    private DccBosExemptId id;
}


