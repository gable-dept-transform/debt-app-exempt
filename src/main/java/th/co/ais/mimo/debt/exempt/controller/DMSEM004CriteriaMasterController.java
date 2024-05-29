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

import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterDto;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataResp;
import th.co.ais.mimo.debt.exempt.service.DMSEM004CriteriaMasterService;


@RestController
@RequestMapping("${api.path}/transaction/dmsem004")
public class DMSEM004CriteriaMasterController {
	 private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
	 private DMSEM004CriteriaMasterService criteriaMasterService;
	
	@PostMapping(value = "/search-data", produces = "application/json")
	public ResponseEntity<DMSEM004SearchDataResp> searchData(@RequestBody DMSEM004SearchDataRequest request) {

		String errorMsg = null;	
		DMSEM004CriteriaMasterDto criteriaMasterDto = null;
		DMSEM004SearchDataResp response = DMSEM004SearchDataResp.builder().build();
		try {
			if (!StringUtils.isEmpty(request.getModeId())) {
				criteriaMasterDto = criteriaMasterService.searchData(request.getModeId(), request.getCriteriaId(), request.getDescription());
			} else {
				errorMsg = "Select Mode is require";
			}
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";
		} finally {
			response = new DMSEM004SearchDataResp(errorMsg, criteriaMasterDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
