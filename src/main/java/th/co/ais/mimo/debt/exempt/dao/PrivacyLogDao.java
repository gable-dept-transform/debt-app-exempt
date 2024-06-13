package th.co.ais.mimo.debt.exempt.dao;

import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.InsertPrivacyLogRequest;


public interface PrivacyLogDao {
    int insertPrivacyLog(InsertPrivacyLogRequest request) throws ExemptException;
}
