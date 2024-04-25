package th.co.ais.mimo.debt.controller;

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
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchRequest;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchResponse;
import th.co.ais.mimo.debt.service.DMSEM002SetTreatmentExemptService;

@RestController
@RequestMapping("${api.path}/transaction/dmsem002")
public class DMSEM002SetTreatmentExemptController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DMSEM002SetTreatmentExemptService dmsem002SetTreatmentExemptService;



	@PostMapping(value = "/search-data",produces = "application/json")
	public ResponseEntity<SearchResponse> searchData(@RequestBody SearchRequest request){

		String errorMsg = null;
		SearchResponse response = SearchResponse.builder().build();
		try {
			if(!StringUtils.isEmpty(request.getSearchType())) {
				if ("BA".equals(request.getSearchType()) || "CA".equals(request.getSearchType()) || "MO".equals(request.getSearchType())) {
					response.setResultSearchList(this.dmsem002SetTreatmentExemptService.searchData(request));
				} else {
					errorMsg = "search Type is invalid";
				}
			}else{
				errorMsg = "search Type is require";
			}
		} catch (ExemptException e) {
			log.error("Exception Treatment searchData : {}", e.getMessage(), e);
			errorMsg = "Treatment searchData Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
