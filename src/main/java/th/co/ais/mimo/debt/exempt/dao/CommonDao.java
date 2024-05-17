package th.co.ais.mimo.debt.exempt.dao;

import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;

import java.util.List;

public interface CommonDao {
    public List<DccExemptCateMaster> searchExemptCateMaster() throws ExemptException;

    public List<DccExemptCateDetail> searchExemptCateDetail(String cateCode)throws ExemptException;

    public int getASCLocationListByUsername(String username) throws ExemptException;
}
