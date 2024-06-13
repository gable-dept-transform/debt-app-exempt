package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class DccTempTransactionId implements Serializable {
	private static final long serialVersionUID = 5945588701041358744L;
	
	@Column(name = "MODE_ID")
	private String modeId;

	@Column(name = "PREASSIGN_ID")
	private String preassignId;

	@Column(name = "BILLING_ACC_NUM")
	private String billingAccNum;

	@Column(name = "MOBILE_NUM")
	private String mobileNum;
}
