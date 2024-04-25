package th.co.ais.mimo.debt.service;

import th.co.ais.mimo.debt.dto.treatment.SearchTreatmentDto;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchRequest;

import java.util.List;

public interface DMSEM002SetTreatmentExemptService {
    List<SearchTreatmentDto> searchData(SearchRequest request) throws ExemptException;


}
