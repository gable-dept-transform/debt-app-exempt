package th.co.ais.mimo.debt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.dto.DcExemptDto;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.service.QueryExemptService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QueryExemptServiceImpl implements QueryExemptService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;


    public List<DcExemptDto> queryExempt(QueryExemptRequest request) throws ExemptException {
        log.info("queryExempt request :"+request);
        try {
            Query query = entityManager.createNativeQuery("select cust_acc_num, billing_acc_num, " +
                    "   mobile_num, module_code, mode_id, exempt_level," +
                    "   (select b.x_con_full_name from billing_profile b  where b.ou_num = billing_acc_num ) billing_acc_name, " +
                    "   to_char(effective_dat,'YYYY/MM/DD') AS effective_dat , to_char(end_dat,'YYYY/MM/DD') AS end_date , " +
                    "   to_char(expire_dat,'YYYY/MM/DD') AS expire_date," +
                    "   cate_code, " +
                    "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_ADD' and reason_code = e.add_reason) add_reason , " +
                    "   add_location, " +
                    "   (select reason_code || ' : ' || reason_description from dcc_reason where reason_type='EXEMPT_UPDATE' and reason_code=e.update_reason) update_reason, " +
                    "   update_location, last_update_by, to_char(last_update_dtm,'YYYY/MM/DD HH24:Mi:SS') AS last_update_date, " +
                    "   no_of_exempt, sent_interface_flag " +
                    "   from   dcc_exempt e", "dcExemptDtoMapping");
            return query.getResultList();
        }catch (PersistenceException | IllegalArgumentException e){
            e.printStackTrace();
            throw new ExemptException(AppConstant.FAIL,e.getMessage());
        }
    }

}


