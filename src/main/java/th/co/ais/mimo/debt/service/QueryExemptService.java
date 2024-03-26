package th.co.ais.mimo.debt.service;

import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;

import java.util.List;

public interface QueryExemptService {
    public List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException;
}
