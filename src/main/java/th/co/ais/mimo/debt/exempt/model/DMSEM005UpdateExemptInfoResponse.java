package th.co.ais.mimo.debt.exempt.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM005UpdateExemptInfoResponse {
	private String errorMsg;
	private String modeId;
	private String preAssignId;
	private String confirmAssignFlag;
}
