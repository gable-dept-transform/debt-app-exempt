package th.co.ais.mimo.debt.exempt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Entity
@Table(name = "DCC_GLOBAL_PARAMETER")
public class DccGlobalParameter implements Serializable{

	private static final long serialVersionUID = 890877129749200974L;

	@Id
	@Column(name = "SECTION_NAME")
	private String sectionName;
	
	@Column(name = "KEYWORD")
	private String keyword;
	
	@Column(name = "KEYWORD_DESC")
	private String keywordDesc;
	
	@Column(name = "KEYWORD_VALUE")
	private String keywordValue;
	
	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DTM")
	private Date lastUpdateDtm;

}
