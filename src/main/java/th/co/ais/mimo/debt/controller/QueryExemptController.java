package th.co.ais.mimo.debt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.ais.mimo.debt.constant.AppConstant;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptRequest;
import th.co.ais.mimo.debt.entity.queryexempt.QueryExemptResponse;
import th.co.ais.mimo.debt.exception.ExemptException;
import th.co.ais.mimo.debt.service.QueryExemptService;

@RestController
@RequestMapping("${api.path}")
public class QueryExemptController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QueryExemptService queryExemptService;



	@PostMapping(value = "/list-query-exempt",produces = "application/json")
	public ResponseEntity<QueryExemptResponse> queryExempt(@RequestBody QueryExemptRequest queryExemptRequest){

		String errorMsg = null;
		QueryExemptResponse response = QueryExemptResponse.builder().build();
		try {
			response.setCurrentDataList(queryExemptService.queryExempt(queryExemptRequest));
			response.setStatus(AppConstant.SUCCESS);
		} catch (ExemptException e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
			response.setStatus(AppConstant.FAIL);
		} finally {
			response.setMessage(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
