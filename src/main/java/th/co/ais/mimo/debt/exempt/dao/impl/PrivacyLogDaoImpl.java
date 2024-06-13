package th.co.ais.mimo.debt.exempt.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.PrivacyLogDao;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.InsertPrivacyLogRequest;


@Component
public class PrivacyLogDaoImpl implements PrivacyLogDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int insertPrivacyLog(InsertPrivacyLogRequest request) throws ExemptException {
        try{
            String sql = "INSERT INTO DCC_PRIVACY_LOG " +
                    "(" +
                    "    USERNAME, " +
                    "    LOCATION_CD, " +
                    "    REFERENCE_TYPE, " +
                    "    REFERENCE_VALUE, " +
                    "    FUNCTION, " +
                    "    DATA_PRIVACY_FIELD, " +
                    "    SYSTEM, " +
                    "    CREATED_BY, " +
                    "    UPDATED_BY" +
                    ") " +
                    "VALUES " +
                    "(" +
                    "    :username," +
                    "    :locationCode, " +
                    "    :referenceType,  " +
                    "    :referenceValue, " +
                    "    :functionName, " +
                    "    :dataPrivacyField," +
                    "    :system, " +
                    "    :createdBy," +
                    "    :updatedBy " +
                    ")";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("username",request.getUsername());
            query.setParameter("locationCode",request.getLocationCode());
            query.setParameter("referenceType",request.getReferenceType());
            query.setParameter("referenceValue",request.getReferenceValue());
            query.setParameter("functionName",request.getFunctionName());
            query.setParameter("dataPrivacyField",request.getDataPrivacyField());
            query.setParameter("system",request.getSystem());
            query.setParameter("createdBy",request.getCreatedBy());
            query.setParameter("updatedBy",request.getUpdatedBy());
            return query.executeUpdate();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("Exception occur when insert privacy log ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }
}

