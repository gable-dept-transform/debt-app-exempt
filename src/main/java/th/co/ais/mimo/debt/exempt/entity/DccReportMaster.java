package th.co.ais.mimo.debt.exempt.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DCC_REPORT_MASTER")
public class DccReportMaster implements Serializable {

	private static final long serialVersionUID = 6760540532310706235L;

	@Id
	@Column(name = "REPORT_ID")
	private String reportId;

	@Column(name = "REPORT_NAME")
	private String reportName;

	@Column(name = "REPORT_MAX_SEQ")
	private String reportMaxSeq;

	@Column(name = "REPORT_DATABASE")
	private String reportDatabase;

	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DTM")
	private Date lastUpdateDtm;
}