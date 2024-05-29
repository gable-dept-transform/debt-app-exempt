package th.co.ais.mimo.debt.exempt.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import th.co.ais.mimo.debt.nego.dao.GetInstallmentInfoDao;
import th.co.ais.mimo.debt.nego.model.GetInstallmentInfoBean;
import th.co.ais.mimo.debt.nego.utils.SqlUtils;

@Repository
public class GetInstallmentInfoDaoImpl implements GetInstallmentInfoDao{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public List<GetInstallmentInfoBean> getInstallmentInfo(String baNo) throws Exception {
		List<GetInstallmentInfoBean> result = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql = getInstallmentQuerySql(sql);             
            Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
            query = getInstallmentQueryParam(query, baNo);
            result = SqlUtils.parseResult(query.getResultList(), GetInstallmentInfoBean.class);
        } catch (Exception e) {
            log.error("Exception searchScoreHistory : {}", e.getMessage(), e);
            throw e;
        } finally {
            SqlUtils.close(entityManager);
        }
        return result;
	}
	
	private StringBuffer getInstallmentQuerySql(StringBuffer sql) throws Exception {
        try {
            if (sql != null) {
                              
                sql.append(" select d.nego_seq AS \"negoSeq\", d.renego_time AS \"renegoTime\" , d.period_time_no AS \"periodTimeNo\" , d.paymt_mny AS \"paymentAmount\", ");
                sql.append("  d.paymt_due_dat AS \"paymentDueDate\", r.receipt_total_mny AS \"receiptTotalAmount\", r.receipt_dat AS \"receiptDat\" ");
                sql.append("  from dcc_nego_detail d, dcc_nego_receipt r ");
                sql.append("  where d.billing_acc_num = :baNo ");
                sql.append("  and r.billing_acc_num(+)  = d.billing_acc_num ");
                sql.append("  and r.nego_seq(+) = d.nego_seq ");
                sql.append("  and r.renego_time(+) = d.renego_time ");
                sql.append("  and r.period_time_no(+) = d.period_time_no ");
                sql.append(" order by d.period_time_no ");
            }
        } catch (Exception e) {
            log.error("Exception createNativeQuerySql : {}", e.getMessage(), e);
            throw e;
        }
        return sql;
    }
	
	private Query getInstallmentQueryParam(Query query,String baNo) throws Exception {
        try {
            if (query != null) {
	           
	            if (StringUtils.isNotBlank(baNo)) {
	                query.setParameter("baNo", baNo);
	            }               
            }
        } catch (Exception e) {
            log.error("Exception createNativeQueryParam : {}", e.getMessage(), e);
            throw e;
        }
        return query;
    }

}
