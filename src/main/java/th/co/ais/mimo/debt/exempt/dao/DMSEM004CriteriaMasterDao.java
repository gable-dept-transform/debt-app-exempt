package th.co.ais.mimo.debt.exempt.dao;

import java.util.List;

import th.co.ais.mimo.debt.exempt.entity.DccCriteriaHistory;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumReq;
import th.co.ais.mimo.debt.exempt.model.GetBillAccNumByMobileNumResp;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdReq;
import th.co.ais.mimo.debt.exempt.model.InsertAssignIdResp;

public interface DMSEM004CriteriaMasterDao {
	
	//public List<GetInstallmentInfoBean> getInstallmentInfo(String baNo) throws Exception;

	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception;

	public InsertAssignIdResp insertCriteriaMaster(InsertAssignIdReq req) throws Exception;
	
	public InsertAssignIdResp updateCriteriaMaster(InsertAssignIdReq req) throws Exception;

	public DccCriteriaHistory insertDccCriteriaHistory(InsertAssignIdReq req, Long newCriteriaId) throws Exception;

	public GetBillAccNumByMobileNumResp validateGetBillAccNumByMobileNum(GetBillAccNumByMobileNumReq req) throws Exception;
}
