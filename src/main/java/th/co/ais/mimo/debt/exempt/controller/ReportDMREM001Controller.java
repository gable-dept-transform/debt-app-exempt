package th.co.ais.mimo.debt.exempt.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.DataDMREM001;
import th.co.ais.mimo.debt.exempt.model.DeleteReportDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.DeleteReportDMREM001Response;
import th.co.ais.mimo.debt.exempt.model.SaveORUpdateDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SaveORUpdateDMREM001Response;
import th.co.ais.mimo.debt.exempt.model.SearchReportDataDMREM001Request;
import th.co.ais.mimo.debt.exempt.model.SearchReportDataDMREM001Response;
import th.co.ais.mimo.debt.exempt.service.ReportDMREM001Service;

@RestController
@RequestMapping("${api.path}/report/dmrem001")
public class ReportDMREM001Controller {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportDMREM001Service reportDMREM001Service;

    @PostMapping(value = "/search-data")
    public ResponseEntity<SearchReportDataDMREM001Response> searchReportData(
            RequestEntity<SearchReportDataDMREM001Request> request) throws Exception {
        SearchReportDataDMREM001Request searchReq = request.getBody();
        SearchReportDataDMREM001Response response = new SearchReportDataDMREM001Response();
        List<DataDMREM001> resultList = new ArrayList<>();
        String errorMsg = null;
        try {
            if (searchReq != null) {
                resultList = reportDMREM001Service.searchReportData(searchReq);
            } else {
                errorMsg = "Search report data : request data not found";
            }
        } catch (ExemptException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            log.error("Exception searchReportData : {}", e.getMessage(), e);
            errorMsg = "Search Report Data Internal server Error process";
        } finally {
            response = new SearchReportDataDMREM001Response(resultList, errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/save-or-update-info")
    public ResponseEntity<SaveORUpdateDMREM001Response> saveOrUpdate(
            RequestEntity<SaveORUpdateDMREM001Request> request, @RequestHeader(name = AppConstant.X_USER_ID) String username)
            throws Exception {
        SaveORUpdateDMREM001Response response = new SaveORUpdateDMREM001Response();
        DataDMREM001 resultModel = new DataDMREM001();
        String errorMsg = null;
        try {
            if (request != null) {
                request.getBody().setUsername(username);
                resultModel = reportDMREM001Service.saveOrUpdateInfo(request.getBody());
            } else {
                errorMsg = "Save OR Update : request data not found";
            }
        } catch (ExemptException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            log.error("Exception searchReportData : {}", e.getMessage(), e);
            errorMsg = "Save OR Update Internal server Error process";
        } finally {
            response = new SaveORUpdateDMREM001Response(resultModel, errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/delete-report")
    public ResponseEntity<DeleteReportDMREM001Response> deleteInformation(
            RequestEntity<DeleteReportDMREM001Request> request, @RequestHeader(name = AppConstant.X_USER_ID) String username)
            throws Exception {
        DeleteReportDMREM001Response response = new DeleteReportDMREM001Response();
        String errorMsg = null;
        try {
            if (request != null) {
                request.getBody().setUsername(username);
                reportDMREM001Service.deleteReportInformation(request.getBody());
            } else {
                errorMsg = "Delete Report : request data not found";
            }
        } catch (ExemptException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            log.error("Exception searchReportData : {}", e.getMessage(), e);
            errorMsg = "Delete Report Internal server Error process";
        } finally {
            response = new DeleteReportDMREM001Response(request.getBody().getReportId(),
                    request.getBody().getReportSeq(), errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}