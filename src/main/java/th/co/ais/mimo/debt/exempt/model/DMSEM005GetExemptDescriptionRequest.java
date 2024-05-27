package th.co.ais.mimo.debt.exempt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM005GetExemptDescriptionRequest {
	private String sectionName;
	private String keyword;
	private String keywordValue;
}
