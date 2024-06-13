package th.co.ais.mimo.debt.exempt.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dao.CommonDao;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMasterDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;

import java.util.List;

@Component
public class CommonDaoImpl implements CommonDao {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

    public List<DccExemptCateMasterDto> searchExemptCateMaster() throws ExemptException {
        try{
            String sql = "select cate_code, cate_description,exempt_reason,active_flag, \n" +
                    "  last_update_by, to_char(last_update_dtm,'DD/MM/YYYY  HH24:MI:SS') last_update_dtm\n" +
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

    public List<DccExemptCateDetailDto> searchExemptCateDetail(String cateCode)throws ExemptException{
        try{
            String sql = "select mode_id,module_code,exempt_duration, to_char(expire_dat,'DD/MM/YYYY') expire_date, \n" +
                    "last_update_by, to_char(last_update_dtm,'DD/MM/YYYY  HH24:Mi:SS') last_update_dtm, exempt_level  \n" +
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

    @Override
    public String getBillingSystem(String billingAccNum) throws ExemptException {

        String sql = "select b.BILLING_SYSTEM \n" +
                " from BILLING_PROFILE b \n" +
                " where b.OU_NUM = :billingAccNum ";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("billingAccNum",billingAccNum);

        List list = query.getResultList();
        if(list != null && !list.isEmpty()){
            return (String)list.get(0);
        }
        return null;
    }

    @Override
    public String getReservePack() throws ExemptException {
        String sql = "select dccu_dbutil.get_exempt_reserved_pack reserved_pack from dual";
        Query query = entityManager.createNativeQuery(sql);

        List<String> list = query.getResultList();
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
