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
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.GetBillingRequest;
import th.co.ais.mimo.debt.exempt.model.GetBillingResponse;
import th.co.ais.mimo.debt.exempt.model.QueryExemptRequest;
import th.co.ais.mimo.debt.exempt.model.QueryExemptResponse;
import th.co.ais.mimo.debt.exempt.service.DMSEM003QueryExemptService;

@RestController
@RequestMapping("${api.path}/transaction/dmsem003")
public class DMSEM003QueryExemptController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DMSEM003QueryExemptService DMSEM003QueryExemptService;



	@PostMapping(value = "/search-data",produces = "application/json")
	public ResponseEntity<QueryExemptResponse> searchData(@RequestBody QueryExemptRequest queryExemptRequest){

		String errorMsg = null;
		QueryExemptResponse response = QueryExemptResponse.builder().build();
		try {
			if(!StringUtils.isEmpty(queryExemptRequest.getSelectType())) {
				response.setResultCurrentList(this.DMSEM003QueryExemptService.queryExempt(queryExemptRequest));
				response.setResultHistoryList(this.DMSEM003QueryExemptService.queryExemptHistory(queryExemptRequest));
			}else{
				errorMsg = "Select Type is require";
			}
		} catch (ExemptException e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/search-billing",produces = "application/json")
	public ResponseEntity<GetBillingResponse> searchBilling(@RequestBody GetBillingRequest request){

		String errorMsg = null;
		GetBillingResponse response = GetBillingResponse.builder().build();
		try {
			response.setResultList(this.DMSEM003QueryExemptService.getBillingAccNum(request));

		} catch (ExemptException e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
