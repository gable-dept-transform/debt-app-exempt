package th.co.ais.mimo.debt.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.enums.ConfigSectionNameEnums;
import th.co.ais.mimo.debt.repo.DccGlobalParameterRepo;


import java.util.List;

@Service
@Transactional
public class CommonService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DccGlobalParameterRepo dccGlobalParameterRepo;

    public List<CommonDropdownListDto> getMobileStatus()  throws Exception {
        return dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.MOBILE_STATUS.toString(),ConfigSectionNameEnums.EXEMPT_CRITERIA.toString());
    }

    public List<CommonDropdownListDto> getExemptAction()  throws Exception{
        return  dccGlobalParameterRepo.getInfoByKeyWordAndSectionName(ConfigSectionNameEnums.EXEMPT_ACTION.toString(),ConfigSectionNameEnums.CRITERIA.toString());
    }
}
