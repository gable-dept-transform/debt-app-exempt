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
import th.co.ais.mimo.debt.exempt.utils.DateUtils;

import java.util.Date;

@RestController
@RequestMapping("${api.path}/transaction/dmsem003")
public class DMSEM003QueryExemptController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DMSEM003QueryExemptService DMSEM003QueryExemptService;

	private String validateSearchDataRequest(QueryExemptRequest queryExemptRequest){
		if(StringUtils.equals("CNO",queryExemptRequest.getSelectType()) && StringUtils.isEmpty(queryExemptRequest.getCustAccNum())){
			return "Customer acc num is required";
		}

		if(StringUtils.equals("BNO",queryExemptRequest.getSelectType()) && StringUtils.isEmpty(queryExemptRequest.getBillingAccNum())){
			return "Billing acc num is required";
		}

		if(StringUtils.equals("MNO",queryExemptRequest.getSelectType()) && StringUtils.isEmpty(queryExemptRequest.getMobileNum())){
			return "Mobile No is required";
		}

		if(StringUtils.equals("EFD",queryExemptRequest.getSelectType())){
			var processResult = validateDateParameter(queryExemptRequest.getEffectiveDateFrom(), queryExemptRequest.getEffectiveDateTo(), "Effective Date");
			if(processResult != null){
				return processResult;
			}
		}

		if(StringUtils.equals("END",queryExemptRequest.getSelectType())){
			var processResult = validateDateParameter(queryExemptRequest.getEndDateFrom(), queryExemptRequest.getEndDateTo(), "End Date");
			if(processResult != null){
				return processResult;
			}
		}


		if(StringUtils.equals("EPD",queryExemptRequest.getSelectType())){
			var processResult = validateDateParameter(queryExemptRequest.getExpireDateFrom(), queryExemptRequest.getExpireDateTo(), "Expire Date");
			if(processResult != null){
				return processResult;
			}
		}

		return null;
	}

	private String validateDateParameter(String dateFrom , String dateTo, String paramName){
		if(dateFrom != null && dateTo == null){
			return paramName + " Date To required";
		}else if(dateFrom == null && dateTo != null){
			return paramName + " Date From required";
		}

		var dateFromValue =DateUtils.getDateByFormatEnLocale(dateFrom, "yyyy-MM-dd");
		var dateToValue =DateUtils.getDateByFormatEnLocale(dateTo, "yyyy-MM-dd");

		if(dateFrom != null && dateTo != null && dateFromValue.after(dateToValue)){
			return paramName +" Date To more than or equal to "+ paramName + " Date From";
		}
		return null;
	}

	@PostMapping(value = "/search-data",produces = "application/json")
	public ResponseEntity<QueryExemptResponse> searchData(@RequestBody QueryExemptRequest queryExemptRequest){

		String errorMsg = null;
		QueryExemptResponse response = QueryExemptResponse.builder().build();
		try {
			var validateResult = validateSearchDataRequest(queryExemptRequest);
			if(validateResult != null){
				errorMsg = validateResult;
			}else{
				if(!StringUtils.isEmpty(queryExemptRequest.getSelectType())) {
					response.setResultCurrentList(this.DMSEM003QueryExemptService.queryExempt(queryExemptRequest));
					response.setResultHistoryList(this.DMSEM003QueryExemptService.queryExemptHistory(queryExemptRequest));
				}else{
					errorMsg = "Select Type is require";
				}
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
			var resultList = this.DMSEM003QueryExemptService.getBillingAccNum(request);
			if(resultList.isEmpty()){
				errorMsg = "Data not found";
			}else{
				response.setResultList(resultList);
			}


		} catch (ExemptException e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response.setErrorMsg(errorMsg);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
}
