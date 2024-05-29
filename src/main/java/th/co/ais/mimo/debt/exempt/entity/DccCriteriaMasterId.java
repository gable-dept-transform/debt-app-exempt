package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class DccCriteriaMasterId  implements Serializable {

	private static final long serialVersionUID = 5945588701041358744L;

	@Column(name = "MODE_ID")
	private String modeId;

	@Column(name = "CRITERIA_ID")
	private Long criteriaId;
}
