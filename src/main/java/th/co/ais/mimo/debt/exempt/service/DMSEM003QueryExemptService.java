package th.co.ais.mimo.debt.exempt.service;

import th.co.ais.mimo.debt.exempt.dto.BillingAccDto;
import th.co.ais.mimo.debt.exempt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.exempt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.GetBillingRequest;
import th.co.ais.mimo.debt.exempt.model.QueryExemptRequest;

import java.util.List;

public interface DMSEM003QueryExemptService {
    List<DcExemptCurrentDtoMapping> queryExempt(QueryExemptRequest request) throws ExemptException;

    List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException;


    List<BillingAccDto> getBillingAccNum(GetBillingRequest request) throws ExemptException;


}
