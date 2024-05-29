package th.co.ais.mimo.debt.exempt.dao;

import java.util.List;

import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;

public interface DMSEM004CriteriaMasterDao {
	
	//public List<GetInstallmentInfoBean> getInstallmentInfo(String baNo) throws Exception;

	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception;
}
