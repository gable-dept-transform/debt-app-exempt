package th.co.ais.mimo.debt.exempt.dao;

import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMasterDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;

import java.util.List;

public interface CommonDao {
    public List<DccExemptCateMasterDto> searchExemptCateMaster() throws ExemptException;

    public List<DccExemptCateDetailDto> searchExemptCateDetail(String cateCode)throws ExemptException;
}
