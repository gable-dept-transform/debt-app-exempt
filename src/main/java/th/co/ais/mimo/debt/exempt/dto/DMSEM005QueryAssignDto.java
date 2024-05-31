package th.co.ais.mimo.debt.exempt.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DMSEM005QueryAssignDto {
	private String modeId;
	private String assignId;
	private Date assignDate;
	private String assignStatus;
}
