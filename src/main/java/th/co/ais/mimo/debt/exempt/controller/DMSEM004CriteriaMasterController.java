package th.co.ais.mimo.debt.exempt.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.model.CommonDropDownReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004DeleteInfoReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004DeleteInfoResp;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataResp;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoResp;
import th.co.ais.mimo.debt.exempt.model.ProvinceInfoResp;
import th.co.ais.mimo.debt.exempt.model.RegionInfoResp;
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
	
	@PostMapping(value = "/delete-info", produces = "application/json")
	public ResponseEntity<DMSEM004UpdateInfoResp> updateInformation(
			@RequestBody DMSEM004UpdateInfoReq request) {
		String errorMsg = null;
		DMSEM004UpdateInfoResp response = DMSEM004UpdateInfoResp.builder().build();
		try {
			Date currentDate = new Date();
			String dateForm = DateUtils.toStringEngDateSimpleFormat(request.getBlacklistDatFrom(), DateUtils.DEFAULT_DATETIME_PATTERN_DATE_SLASH_YYYY_MM_DD);
			String dateTo = DateUtils.toStringEngDateSimpleFormat(request.getBlacklistDatTo(), DateUtils.DEFAULT_DATETIME_PATTERN_DATE_SLASH_YYYY_MM_DD);
			if(currentDate.after(request.getRunAt())) {
				errorMsg = this.criteriaMasterService.updateInfo(request.getLastUpdateBy(), request.getBlacklistDatFlag(), dateForm, dateTo, request.getModeId(), Long.valueOf(request.getCriteriaId()), request.getCriteriaType());
			}else {
				errorMsg = this.criteriaMasterService.deleteInfo(request.getModeId(), request.getCriteriaId());
			}		
		} catch (Exception e) {
			log.error("Exception updateInformation : {}", e.getMessage(), e);
			errorMsg = "updateInformation Internal server Error process";			
		}finally {
			response = new DMSEM004UpdateInfoResp(errorMsg);
		}		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/region-info")
	public ResponseEntity<RegionInfoResp> getRegion() throws Exception {
		String errorMsg = null;
		List<CommonDropdownListDto> listDto = null;
		RegionInfoResp response = null;
		try {
			listDto = criteriaMasterService.getRegionInfoCaseDropdown();
		}catch (Exception e){
			log.error("Exception GetRegion : {}", e.getMessage(), e);
			errorMsg = "GetRegion Internal server Error process";
		} finally {
			response = new RegionInfoResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/province-info", produces = "application/json")
	public ResponseEntity<ProvinceInfoResp> provinceInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<ProvinceDropdownListDto> listProvince = null;
		ProvinceInfoResp response = null;
		try {
			
			listProvince = criteriaMasterService.getProvinceInfoCaseDropdown(request.getVal());
			
		}catch (Exception e){
			log.error("Exception GetProvince : {}", e.getMessage(), e);
			errorMsg = "GetProvince Internal server Error process";
		} finally {
			response = new ProvinceInfoResp(errorMsg, listProvince);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/district-info", produces = "application/json")
	public ResponseEntity<ProvinceInfoResp> districtInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<ProvinceDropdownListDto> listProvince = null;
		ProvinceInfoResp response = null;
		try {
			
		}catch (Exception e){
			log.error("Exception GetProvince : {}", e.getMessage(), e);
			errorMsg = "GetProvince Internal server Error process";
		} finally {
			response = new ProvinceInfoResp(errorMsg, listProvince);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/subdistrict-info", produces = "application/json")
	public ResponseEntity<ProvinceInfoResp> subdistrictInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<ProvinceDropdownListDto> listProvince = null;
		ProvinceInfoResp response = null;
		try {
			
		}catch (Exception e){
			log.error("Exception GetProvince : {}", e.getMessage(), e);
			errorMsg = "GetProvince Internal server Error process";
		} finally {
			response = new ProvinceInfoResp(errorMsg, listProvince);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
		
}
