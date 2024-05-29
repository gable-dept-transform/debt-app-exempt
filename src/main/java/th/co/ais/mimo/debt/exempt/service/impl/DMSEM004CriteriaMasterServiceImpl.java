package th.co.ais.mimo.debt.exempt.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import th.co.ais.mimo.debt.exempt.dao.DMSEM004CriteriaMasterDao;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.repo.DMSEM004CriteriaMasterRepo;
import th.co.ais.mimo.debt.exempt.service.DMSEM004CriteriaMasterService;

@Service
@Transactional
public class DMSEM004CriteriaMasterServiceImpl implements DMSEM004CriteriaMasterService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	DMSEM004CriteriaMasterRepo criteriaMasterRepo;
	
	@Autowired
	DMSEM004CriteriaMasterDao criteriaMasterDao;
	
	public List<DMSEM004CriteriaMasterBean> searchData(String modeId, Long criteriaId, String description) throws Exception {
		return criteriaMasterDao.searchData(modeId,  criteriaId,  description);
	}

	@Override
	public String updateInfo(String lastUpdateBy, String blacklistDatFlag, String blacklistDatFrom,
			String blacklistDatTo, String modeId, Long criteriaId, String criteriaType) throws Exception {
		
		return null;
	}

	@Override
	public String deleteInfo(String modeId, Long criteriaId) throws Exception {
		
		return null;
	}
}
