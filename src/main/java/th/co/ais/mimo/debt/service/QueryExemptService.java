package th.co.ais.mimo.debt.service;

import th.co.ais.mimo.debt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;

import java.util.List;

public interface QueryExemptService {
    List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException;

    public List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException;
}
