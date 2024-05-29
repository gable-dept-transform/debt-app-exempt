package th.co.ais.mimo.debt.exempt.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005SearchDataResponse;

@RestController
@RequestMapping("${api.path}/transaction/dmsem004")
public class DMSEM004CriteriaMasterController {
	 private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value = "/search-data", produces = "application/json")
	public ResponseEntity<DMSEM005SearchDataResponse> searchData(@RequestBody DMSEM005SearchDataRequest request) {

		String errorMsg = null;
		DMSEM005SearchDataResponse response = DMSEM005SearchDataResponse.builder().build();
		try {
			if (!StringUtils.isEmpty(request.getMode())) {
				if (request.getMode().equals("EM")) {
//					response.setResultList(this.transactionDMSEM005Service.searchData(request));
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

}
