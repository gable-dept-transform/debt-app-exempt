package th.co.ais.mimo.debt.exempt.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class DMSEM004UpdateInfoReq {
	
	private Date runAt;
	private String lastUpdateBy; 
	private String blacklistDatFlag;
	private Date blacklistDatFrom; 
	private Date blacklistDatTo; 
	private String modeId; 
	private Long criteriaId; 
	private String criteriaType;
}
