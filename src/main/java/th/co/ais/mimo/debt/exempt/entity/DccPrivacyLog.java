package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DCC_PRIVACY_LOG")
public class DccPrivacyLog implements Serializable {

    @EmbeddedId
    DccPrivacyLogId id;

}
