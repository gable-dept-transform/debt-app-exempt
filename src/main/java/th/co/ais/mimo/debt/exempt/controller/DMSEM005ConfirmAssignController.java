package th.co.ais.mimo.debt.exempt.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetExemptDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005GetReasonDescriptionResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005QueryAssignRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005QueryAssignResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataResponse;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoResponse;
import th.co.ais.mimo.debt.exempt.service.DMSEM005ConfirmAssignService;

@RestController
@RequestMapping("${api.path}/transaction/dmsem005")
public class DMSEM005ConfirmAssignController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DMSEM005ConfirmAssignService transactionDMSEM005Service;

	@PostMapping(value = "/search-data", produces = "application/json")
	public ResponseEntity<DMSEM005SearchDataResponse> searchData(@RequestBody DMSEM005SearchDataRequest request) {

		String errorMsg = null;
		DMSEM005SearchDataResponse response = DMSEM005SearchDataResponse.builder().build();
		try {
			if (!StringUtils.isEmpty(request.getMode())) {
				if (request.getMode().equals("EM")) {
					response.setResultList(this.transactionDMSEM005Service.searchData(request));
				} else {
					errorMsg = "Mode not mapping";
				}
			} else {
				errorMsg = "Select Mode is require";
			}
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/query-assign", produces = "application/json")
	public ResponseEntity<DMSEM005QueryAssignResponse> queryAssign(@RequestBody DMSEM005QueryAssignRequest request) {

		String errorMsg = null;
		DMSEM005QueryAssignResponse response = DMSEM005QueryAssignResponse.builder().build();
		try {
			if (!StringUtils.isEmpty(request.getModeId())) {
			response.setResultList(this.transactionDMSEM005Service.queryAssign(request));
			} else {
				errorMsg = "Select Mode is require";
			}
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/save-or-update-info", produces = "application/json")
	public ResponseEntity<DMSEM005UpdateExemptInfoResponse> updateExemptInfo(
			@RequestBody DMSEM005UpdateExemptInfoRequest request) {
		String errorMsg = null;
		DMSEM005UpdateExemptInfoResponse response = DMSEM005UpdateExemptInfoResponse.builder().build();
		try {
			response = this.transactionDMSEM005Service.updateExemptInfo(request);
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
			response.setErrorMsg(errorMsg);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/delete-info", produces = "application/json")
	public ResponseEntity<DMSEM005UpdateExemptInfoResponse> deleteInformation(
			@RequestBody DMSEM005UpdateExemptInfoRequest request) {
		String errorMsg = null;
		DMSEM005UpdateExemptInfoResponse response = DMSEM005UpdateExemptInfoResponse.builder().build();
		try {
			response = this.transactionDMSEM005Service.deleteInformation(request);
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
			response.setErrorMsg(errorMsg);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/get-exempt-description", produces = "application/json")
	public ResponseEntity<DMSEM005GetExemptDescriptionResponse> getExemptDescription ( @RequestBody DMSEM005GetExemptDescriptionRequest request) {
		String errorMsg = null;
		DMSEM005GetExemptDescriptionResponse response = DMSEM005GetExemptDescriptionResponse.builder().build();
		try {
			response = this.transactionDMSEM005Service.getExemptDescription(request);
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
			response.setErrorMsg(errorMsg);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/get-reason-description", produces = "application/json")
	public ResponseEntity<DMSEM005GetReasonDescriptionResponse> getReasonDescription ( @RequestBody DMSEM005GetReasonDescriptionRequest request) {
		String errorMsg = null;
		DMSEM005GetReasonDescriptionResponse response = DMSEM005GetReasonDescriptionResponse.builder().build();
		try {
			response = this.transactionDMSEM005Service.getReasonDescription(request);
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
			response.setErrorMsg(errorMsg);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
