package th.co.ais.mimo.debt.exempt.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dto.ExemptDetailDto;
import th.co.ais.mimo.debt.exempt.dto.SearchTreatmentDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.*;
import th.co.ais.mimo.debt.exempt.repo.DccExemptRepo;
import th.co.ais.mimo.debt.exempt.repo.impl.DccExemptProcRepoImpl;
import th.co.ais.mimo.debt.exempt.service.DMSEM002SetTreatmentExemptService;
import th.co.ais.mimo.debt.exempt.utils.DateUtils;

import java.util.ArrayList;
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

	@PostMapping(value = "/search-load-text-data",produces = "application/json")
	public ResponseEntity<SearchLoadTextResponse> searchLoadTextData(@RequestBody SearchLoadTextRequest request){

		String errorMsg = null;
		int total = 0;
		int totalFail = 0;
		int totalSuccess = 0;
		SearchLoadTextResponse response = SearchLoadTextResponse.builder().build();
		try {
			if(!StringUtils.isEmpty(request.getSearchType())) {
				if(request.getParamValue() != null) {
					List<SearchTreatmentDto> resultList = new ArrayList<>();
					List<ExemptDetailDto> exemptDetailDtoList = new ArrayList<>();
					List<String> errorList = new ArrayList<>();
					for (String param: request.getParamValue())
					{
						total++;
						SearchRequest searchRequest = new SearchRequest();
						searchRequest.setSearchType(request.getSearchType());
						searchRequest.setParamValue(param);

						List<SearchTreatmentDto> list =  this.dmsem002SetTreatmentExemptService.searchData(searchRequest);
						resultList.addAll(list);
						//response.getResultList().addAll(list);
						if (!list.isEmpty()) {
							totalSuccess++;
							List<ExemptDetailDto> listDetail = dmsem002SetTreatmentExemptService.searchExemptDetail(searchRequest.getSearchType(), searchRequest.getParamValue());
//							response.getResultDetailList().addAll(listDetail);
							exemptDetailDtoList.addAll(listDetail);
						}else{
							totalFail++;
							errorList.add(param);
						}

					}
					response.setTotal(total);
					response.setTotalFail(totalFail);
					response.setTotalSuccess(totalSuccess);
					response.setErrorList(errorList);
					response.setResultList(resultList);
					response.setResultDetailList(exemptDetailDtoList);
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

	@PostMapping(value = "/search-exempt-load-text",produces = "application/json")
	public ResponseEntity<SearchResponse> searchLoadTextExemptDetail(@RequestBody SearchLoadTextRequest request){
		try {
			List<ExemptDetailDto> resultList = new ArrayList<>();
			for(String param: request.getParamValue()) {
				List<ExemptDetailDto> list = this.dmsem002SetTreatmentExemptService.searchExemptDetail(request.getSearchType(), param);
				resultList.addAll(list);
			}

			return ResponseEntity.ok().body(SearchResponse.builder().resultDetailList(resultList).build());
		} catch (ExemptException e) {
			log.error("search load text exempt detail error ",e);
			return ResponseEntity.ok().body(SearchResponse.builder().errorMsg("error").build());
		}

	}


	@PostMapping(value = "/add-exempt",produces = "application/json")
	public ResponseEntity<AddExemptResponse> addExempt(@RequestBody AddExemptRequest request,
													   @RequestHeader(name = AppConstant.X_USER_ID) String userId,
													   @RequestHeader(name = AppConstant.X_LOCATION,required = false) Integer location){
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
															 @RequestHeader(name = "x-location",required = false) Integer location){
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
															 @RequestHeader(name = "x-location",required = false) Integer location){
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

	@Autowired
	DccExemptRepo exemptRepo;

	@Autowired
	DccExemptProcRepoImpl exemptProcRepo;
	@GetMapping(value = "/test-proc")
	public ResponseEntity getCateDetail() {
//		exemptRepo.ffGetNegoExemSff("32100070221632"
//				,"32100070221638"
//				,"8870004434","DC","2024/05/01","2024/05/29","CA");
		String res = exemptProcRepo.callDGetNGExemSFF("321000702216321111"
				,"32100070221638"
				,"8870004434","DC", DateUtils.getCurrentDate(),DateUtils.getCurrentDate(),"CA");
		log.debug(" Res : {}",res);
		return ResponseEntity.ok("success");
	}

}
