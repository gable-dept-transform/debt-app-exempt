package th.co.ais.mimo.debt.exempt.service;

import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterDto;


public interface DMSEM004CriteriaMasterService {
	
	DMSEM004CriteriaMasterDto searchData( String modeId, Long criteriaId, String description) throws ExemptException;

}
