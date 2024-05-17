package th.co.ais.mimo.debt.exempt.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.CommonDao;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;

@Component
public class CommonDaoImpl implements CommonDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public List<DccExemptCateMaster> searchExemptCateMaster() throws ExemptException {
        try{
            String sql = "select cate_code, cate_description,exempt_reason,active_flag, \n" +
                    "  last_update_by, to_char(last_update_dtm,'YYYY/MM/DD  HH24:MI:SS') last_update_dtm\n" +
                    " from dcc_exempt_cate_master\n" +
                    " where active_flag = 'Y'\n" +
                    " order by cate_code";
            Query query = entityManager.createNativeQuery(sql, "searchExemptCateMasterDtoMapping");
            //query.setParameter("mobileNum",request.getMobileNum());
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("exception occur when searchExemptCateMaster ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

    public List<DccExemptCateDetail> searchExemptCateDetail(String cateCode)throws ExemptException{
        try{
            String sql = "select mode_id,module_code,exempt_duration, to_char(expire_dat,'YYYY/MM/DD') expire_date, \n" +
                    "last_update_by, to_char(last_update_dtm,'YYYY/MM/DD  HH24:Mi:SS') last_update_dtm, exempt_level  \n" +
                    "from dcc_exempt_cate_detail where cate_code = :cate_code\n" +
                    "order by mode_id";
            Query query = entityManager.createNativeQuery(sql, "searchExemptCateDetailDtoMapping");
            query.setParameter("cate_code",cateCode);
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("exception occur when searchExemptCateMaster ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

    public int getASCLocationListByUsername(String username)throws ExemptException{
        try{
            String sql = "SELECT DISTINCT D.LOCATION_ID " +
                    "FROM ACS_USER A, ACS_USER_GROUP_LOCATION B, ACS_GROUP_LOCATION C, ACS_LOCATION_RELATION D, CP_LOCATION_MASTER E " +
                    "WHERE A.USER_ID = B.USER_ID AND B.GROUP_ID = C.GROUP_ID AND C.GROUP_ID = D.GROUP_ID AND D.LOCATION_ID = E.LOCATION_ID" +
                    "AND UPPER(A.USER_ID) = UPPER(:userName) ";
            Query query = entityManager.createNativeQuery(sql, Integer.class);
            query.setParameter("username",username);
            return query.getFirstResult();
        }catch (PersistenceException | IllegalArgumentException e){
            log.error("exception occur when getASCLocationListByUsername ",e);
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

}
