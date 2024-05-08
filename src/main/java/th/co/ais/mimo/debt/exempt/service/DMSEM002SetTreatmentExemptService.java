package th.co.ais.mimo.debt.exempt.service;

import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.*;

import java.util.List;

public interface DMSEM002SetTreatmentExemptService {
    List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException;

    List<ExemptDetailDto> searchExemptDetail(String searchType,String vaule)throws ExemptException;

    public AddExemptResponse insertExempt(AddExemptRequest addExemptRequest, Integer location, String insertBy)throws ExemptException;

    public UpdateExemptResponse updateExempt(UpdateExemptRequest updateExemptRequest, Integer location, String updateBy)throws ExemptException;

    public DeleteExemptResponse deleteExempt(DeleteExemptRequest deleteExemptRequest, Integer location, String deleteBy)throws ExemptException;


    public AddExemptResponse validateAddExempt(AddExemptRequest addExemptRequest)throws ExemptException;

}
