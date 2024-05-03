package th.co.ais.mimo.debt.exempt.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.dao.DMSEM002SetTreatmentExemptDao;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.SearchRequest;
import th.co.ais.mimo.debt.exempt.service.DMSEM002SetTreatmentExemptService;

import java.util.List;

@Service
@Transactional
public class DMSEM002SetTreatmentExemptServiceImpl implements DMSEM002SetTreatmentExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DMSEM002SetTreatmentExemptDao dmsem002SetTreatmentExemptDao;

    @Override
    public List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException {

        return dmsem002SetTreatmentExemptDao.searchData(request);
    }

    @Override
    public List<ExemptDetailDto> searchExemptDetail(String searchType, String value) throws ExemptException {
        return dmsem002SetTreatmentExemptDao.searchExemptDetail(searchType,value);
    }


}


