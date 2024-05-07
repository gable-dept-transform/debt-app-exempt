package th.co.ais.mimo.debt.exempt.dao;

import java.util.List;

import th.co.ais.mimo.debt.exempt.entity.DccReportExemptCriteria;
import th.co.ais.mimo.debt.exempt.model.DataDMREM001;
import th.co.ais.mimo.debt.exempt.model.SaveORUpdateDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SearchReportDataDMREM001Request;
import th.co.ais.mimo.debt.exempt.repo.DccCalendarTransactionRepository;

public interface ReportDMREM001Dao {
    List<DataDMREM001> searchReportDMREM001(SearchReportDataDMREM001Request request) throws Exception;

    DccReportExemptCriteria saveDccReportExemptCriteria(SaveORUpdateDMREM001Request request) throws Exception;

    List<DccReportExemptCriteria> queryDccReportExemptCriteriaByReportIdandReportSeq(SaveORUpdateDMREM001Request request) throws Exception;

}
