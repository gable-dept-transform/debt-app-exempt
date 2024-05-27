package th.co.ais.mimo.debt.exempt.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM005UpdateExemptInfoRequest {
	private String modeId;
	private String preAssignId;
	private String confirmAssignFlag;
	private String username;
}
