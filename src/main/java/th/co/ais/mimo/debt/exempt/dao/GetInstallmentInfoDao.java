package th.co.ais.mimo.debt.exempt.dao;

import java.util.List;

import th.co.ais.mimo.debt.nego.model.GetInstallmentInfoBean;

public interface GetInstallmentInfoDao {
	
	public List<GetInstallmentInfoBean> getInstallmentInfo(String baNo) throws Exception;
}
