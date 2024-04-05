package th.co.ais.mimo.debt.service;

import th.co.ais.mimo.debt.dto.BillingAccDto;
import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.model.queryexempt.GetBillingRequest;
import th.co.ais.mimo.debt.model.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;

import java.util.List;

public interface QueryExemptService {
    List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException;

    List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException;


    List<BillingAccDto> getBillingAccNum(GetBillingRequest request) throws ExemptException;
}
