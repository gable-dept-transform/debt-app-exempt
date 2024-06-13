package th.co.ais.mimo.debt.exempt.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.*;
import th.co.ais.mimo.debt.exempt.service.DMSEM002SetTreatmentExemptService;

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
	public ResponseEntity<SearchResponse> searchExemptDetail(@RequestBody SearchRequest request){
        try {
            List<ExemptDetailDto> list =  this.dmsem002SetTreatmentExemptService.searchExemptDetail(request.getSearchType(), request.getParamValue());

			return ResponseEntity.ok().body(SearchResponse.builder().resultDetailList(list).build());
        } catch (ExemptException e) {
//            throw new RuntimeException(e);
			return ResponseEntity.ok().body(SearchResponse.builder().errorMsg("error").build());
        }
        //return ResponseEntity.ok().body("");
	}


	@PostMapping(value = "/add-exempt",produces = "application/json")
	public ResponseEntity<AddExemptResponse> addExempt(@RequestBody AddExemptRequest request,
													   @RequestHeader(name = "x-user-id") String userId,
													   @RequestHeader(name = "x-location") Integer location){
		try {
			AddExemptResponse response = this.dmsem002SetTreatmentExemptService.insertExempt(request,location,userId);
			return ResponseEntity.ok().body(response);
		} catch (ExemptException e) {
			throw new RuntimeException(e);
		}

	}

	@PostMapping(value = "/validate-add-exempt",produces = "application/json")
	public ResponseEntity<AddExemptResponse> preAddExempt(@RequestBody AddExemptRequest request){
		try {
			AddExemptResponse response = this.dmsem002SetTreatmentExemptService.validateAddExempt(request);
			return ResponseEntity.ok().body(response);
		} catch (ExemptException e) {
			return ResponseEntity.ok().body(AddExemptResponse.builder()
					.responseCode(e.getCode())
					.errorMsg(e.getMessage())
					.build());
		}

	}


	@PostMapping(value = "/update-exempt",produces = "application/json")
	public ResponseEntity<UpdateExemptResponse> updateExempt(@RequestBody UpdateExemptRequest request,
															 @RequestHeader(name = "x-user-id") String userId,
															 @RequestHeader(name = "x-location") Integer location){
		try {
			UpdateExemptResponse response = this.dmsem002SetTreatmentExemptService.updateExempt(request,location,userId);
			return ResponseEntity.ok().body(response);
		} catch (ExemptException e) {
			return ResponseEntity.ok().body(UpdateExemptResponse.builder()
					.responseCode(e.getCode())
					.errorMsg(e.getMessage())
					.build());
		}

	}

	@PostMapping(value = "/delete-exempt",produces = "application/json")
	public ResponseEntity<DeleteExemptResponse> deleteExempt(@RequestBody DeleteExemptRequest request,
															 @RequestHeader(name = "x-user-id") String userId,
															 @RequestHeader(name = "x-location") Integer location){
		try {
			DeleteExemptResponse response = this.dmsem002SetTreatmentExemptService.deleteExempt(request,location,userId);
			return ResponseEntity.ok().body(response);
		} catch (ExemptException e) {
			return ResponseEntity.ok().body(DeleteExemptResponse.builder()
					.responseCode(e.getCode())
					.errorMsg(e.getMessage())
					.build());
		}

	}





}
