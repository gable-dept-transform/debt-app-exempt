package th.co.ais.mimo.debt.exempt.dao;

import java.util.List;

import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004GetBaByMobileNumDto;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdReq;

public interface DMSEM004CriteriaMasterDao {
	
	//public List<GetInstallmentInfoBean> getInstallmentInfo(String baNo) throws Exception;

	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception;

	public void insertCriteriaMaster(InsertAssignIdReq req, Long newCriteriaId) throws Exception;
	
	public void updateCriteriaMaster(InsertAssignIdReq req) throws Exception;

	public DMSEM004GetBaByMobileNumDto validateGetBillAccNumByMobileNum(String mobileNum) throws Exception;

}
