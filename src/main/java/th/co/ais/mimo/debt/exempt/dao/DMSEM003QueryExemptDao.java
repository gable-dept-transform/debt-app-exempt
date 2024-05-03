package th.co.ais.mimo.debt.exempt.dao;

import th.co.ais.mimo.debt.exempt.dto.BillingAccDto;
import th.co.ais.mimo.debt.exempt.dto.DcExempHistoryDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.GetBillingRequest;
import th.co.ais.mimo.debt.exempt.model.QueryExemptRequest;

import java.util.List;

public interface DMSEM003QueryExemptDao {

    public List<DcExempHistoryDto> queryExemptHistory(QueryExemptRequest request) throws ExemptException;

    public List<BillingAccDto> getBillingAccNum(GetBillingRequest request) throws ExemptException;
}
