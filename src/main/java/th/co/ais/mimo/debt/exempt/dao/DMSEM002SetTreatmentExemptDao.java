package th.co.ais.mimo.debt.exempt.dao;

import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.SearchRequest;

import java.util.List;

public interface DMSEM002SetTreatmentExemptDao {

    public List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException;

    public List<ExemptDetailDto> searchExemptDetail(String searchType, String vaule) throws ExemptException;

    public String getDCBOSSeq() throws ExemptException;
}
