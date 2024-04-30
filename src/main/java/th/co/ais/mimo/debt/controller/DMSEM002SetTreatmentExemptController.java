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
import th.co.ais.mimo.debt.dto.treatment.ExemptDetailDto;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchRequest;
import th.co.ais.mimo.debt.model.treatmentexempt.SearchResponse;
import th.co.ais.mimo.debt.service.DMSEM002SetTreatmentExemptService;

import java.util.List;

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
				response.setResultList(this.dmsem002SetTreatmentExemptService.searchData(request));
				if(!response.getResultList().isEmpty()){
					response.setResultDetailList(dmsem002SetTreatmentExemptService.searchExemptDetail(request.getSearchType(),request.getParamValue()));
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

	@PostMapping(value = "/search-exempt",produces = "application/json")
	public ResponseEntity searchExemptDetail(@RequestBody SearchRequest request){
        try {
            List<ExemptDetailDto> list =  this.dmsem002SetTreatmentExemptService.searchExemptDetail(request.getSearchType(), request.getParamValue());
			return ResponseEntity.ok().body(list);
        } catch (ExemptException e) {
            throw new RuntimeException(e);
        }
        //return ResponseEntity.ok().body("");
	}
	
}
