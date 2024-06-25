package th.co.ais.mimo.debt.exempt.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.exempt.dao.PrivacyLogDao;
import th.co.ais.mimo.debt.exempt.model.InsertPrivacyLogRequest;
import th.co.ais.mimo.debt.exempt.model.PrivacyLogInsertRequest;
import th.co.ais.mimo.debt.exempt.repo.DccPrivacyLogRepository;


@Service
@Transactional
public class DccPrivacyLogService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DccPrivacyLogRepository dccPrivacyLogRepo;

    @Autowired
    private PrivacyLogDao privacyLogDao;
    public String insertDataLog(PrivacyLogInsertRequest request, String username, Integer locationCode){
        try {

            privacyLogDao.insertPrivacyLog(InsertPrivacyLogRequest.builder()
                    .username(username)
                    .locationCode(locationCode == null ? null : String.valueOf(locationCode))
                    .referenceType(request.getRefType())
                    .referenceValue(request.getRefValue())
                    .functionName(request.getFunctionName())
                    .dataPrivacyField(request.getPrivacyField())
                    .system(request.getSystem())
                    .createdBy(username)
                    .updatedBy(username)
                    .build());
            return null;
        } catch (Exception e) {
            log.error(" Exception insertDataLog : " + e.getMessage(), e);
            return e.getMessage();
        }

    }
}
