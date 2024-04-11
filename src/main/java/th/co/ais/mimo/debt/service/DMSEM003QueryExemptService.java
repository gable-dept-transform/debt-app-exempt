package th.co.ais.mimo.debt.service;

import th.co.ais.mimo.debt.dto.BillingAccDto;
import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptCurrentDto;
import th.co.ais.mimo.debt.dto.DcExemptCurrentDtoMapping;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.model.queryexempt.GetBillingRequest;
import th.co.ais.mimo.debt.model.queryexempt.QueryExemptRequest;

import java.util.List;

public interface DMSEM003QueryExemptService {
    List<DcExemptCurrentDtoMapping> queryExempt(QueryExemptRequest request) throws ExemptException;

    List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException;


    List<BillingAccDto> getBillingAccNum(GetBillingRequest request) throws ExemptException;

}
