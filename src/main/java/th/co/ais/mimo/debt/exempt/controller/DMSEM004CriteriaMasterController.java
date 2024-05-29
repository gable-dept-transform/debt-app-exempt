package th.co.ais.mimo.debt.exempt.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataResp;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoResp;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM005UpdateExemptInfoResponse;
import th.co.ais.mimo.debt.exempt.service.DMSEM004CriteriaMasterService;
import th.co.ais.mimo.debt.exempt.utils.DateUtils;


@RestController
@RequestMapping("${api.path}/transaction/dmsem004")
public class DMSEM004CriteriaMasterController {
	 private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	 @Autowired
	 private DMSEM004CriteriaMasterService criteriaMasterService;
	
	@PostMapping(value = "/search-data", produces = "application/json")
	public ResponseEntity<DMSEM004SearchDataResp> searchData(@RequestBody DMSEM004SearchDataRequest request) {

		String errorMsg = null;	
		List<DMSEM004CriteriaMasterBean> criteriaMasterDto = null;
		DMSEM004SearchDataResp response = DMSEM004SearchDataResp.builder().build();
		try {
			if (!StringUtils.isEmpty(request.getModeId())) {
				criteriaMasterDto = criteriaMasterService.searchData(request.getModeId(), request.getCriteriaId(), request.getDescription());
				if(CollectionUtils.isEmpty(criteriaMasterDto)) {
					errorMsg = "Data not found";
				}
			} else {
				errorMsg = "Select Mode is require";
			}
		} catch (Exception e) {
			log.error("Exception searchData : {}", e.getMessage(), e);
			errorMsg = "searchData Internal server Error process";
		} finally {
			response = new DMSEM004SearchDataResp(errorMsg, criteriaMasterDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/update-info", produces = "application/json")
	public ResponseEntity<DMSEM004UpdateInfoResp> updateInformation(
			@RequestBody DMSEM004UpdateInfoReq request) {
		String errorMsg = null;
		DMSEM004UpdateInfoResp response = DMSEM004UpdateInfoResp.builder().build();
		try {
			String dateForm = DateUtils.toStringEngDateSimpleFormat(request.getBlacklistDatFrom(), DateUtils.DEFAULT_DATETIME_PATTERN_DATE_SLASH_YYYY_MM_DD);
			String dateTo = DateUtils.toStringEngDateSimpleFormat(request.getBlacklistDatTo(), DateUtils.DEFAULT_DATETIME_PATTERN_DATE_SLASH_YYYY_MM_DD);
			errorMsg = this.criteriaMasterService.updateInfo(request.getLastUpdateBy(), request.getBlacklistDatFlag(), dateForm, dateTo, request.getModeId(), Long.valueOf(request.getCriteriaId()), request.getCriteriaType());
		} catch (Exception e) {
			log.error("Exception queryExempt : {}", e.getMessage(), e);
			errorMsg = "queryExempt Internal server Error process";			
		}finally {
			response = new DMSEM004UpdateInfoResp(errorMsg);
		}		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
