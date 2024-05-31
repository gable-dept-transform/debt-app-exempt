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
import th.co.ais.mimo.debt.exempt.dto.DistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.GetRefAssignDto;
import th.co.ais.mimo.debt.exempt.dto.ProvinceDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.SubDistrictDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.ZipCodeDropdownListDto;
import th.co.ais.mimo.debt.exempt.model.AddReasonDto;
import th.co.ais.mimo.debt.exempt.model.CategoryDto;
import th.co.ais.mimo.debt.exempt.model.CategoryResp;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentDto;
import th.co.ais.mimo.debt.exempt.model.CollectionSegmentResp;
import th.co.ais.mimo.debt.exempt.model.CommonDropDownReq;
import th.co.ais.mimo.debt.exempt.model.CommonDropDownResp;
import th.co.ais.mimo.debt.exempt.model.CommonDropdownDto;
import th.co.ais.mimo.debt.exempt.model.CompanyCodeReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004CriteriaMasterBean;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataRequest;
import th.co.ais.mimo.debt.exempt.model.DMSEM004SearchDataResp;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoReq;
import th.co.ais.mimo.debt.exempt.model.DMSEM004UpdateInfoResp;
import th.co.ais.mimo.debt.exempt.model.DistrictInfoResp;
import th.co.ais.mimo.debt.exempt.model.ProvinceInfoResp;
import th.co.ais.mimo.debt.exempt.model.ReasonResp;
import th.co.ais.mimo.debt.exempt.model.RegionInfoResp;
import th.co.ais.mimo.debt.exempt.model.SearchAssignReq;
import th.co.ais.mimo.debt.exempt.model.SearchAssignResp;
import th.co.ais.mimo.debt.exempt.model.SubCategoryDto;
import th.co.ais.mimo.debt.exempt.model.SubCategoryReq;
import th.co.ais.mimo.debt.exempt.model.SubCategoryResp;
import th.co.ais.mimo.debt.exempt.model.SubDistrictInfoResp;
import th.co.ais.mimo.debt.exempt.model.ZipCodeInfoResp;
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
			log.error("Exception provinceInformation : {}", e.getMessage(), e);
			errorMsg = "provinceInformation Internal server Error process";
		} finally {
			response = new ProvinceInfoResp(errorMsg, listProvince);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/district-info", produces = "application/json")
	public ResponseEntity<DistrictInfoResp> districtInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<DistrictDropdownListDto> listDistrict = null;
		DistrictInfoResp response = null;
		try {
			listDistrict = criteriaMasterService.getDistrictInfoCaseDropdown(request.getVal());
		}catch (Exception e){
			log.error("Exception districtInformation : {}", e.getMessage(), e);
			errorMsg = "districtInformation Internal server Error process";
		} finally {
			response = new DistrictInfoResp(errorMsg, listDistrict);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/subdistrict-info", produces = "application/json")
	public ResponseEntity<SubDistrictInfoResp> subdistrictInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<SubDistrictDropdownListDto> list = null;
		SubDistrictInfoResp response = null;
		try {
			list = criteriaMasterService.getSubDistrictInfoCaseDropdown(request.getVal());
		}catch (Exception e){
			log.error("Exception subdistrictInformation : {}", e.getMessage(), e);
			errorMsg = "subdistrictInformation Internal server Error process";
		} finally {
			response = new SubDistrictInfoResp(errorMsg, list);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/zipcode-info", produces = "application/json")
	public ResponseEntity<ZipCodeInfoResp> zipCodeInformation(
			@RequestBody CommonDropDownReq request) {
		String errorMsg = null;
		List<ZipCodeDropdownListDto> list = null;
		ZipCodeInfoResp response = null;
		try {
			list = criteriaMasterService.getZipCodeInfoCaseDropdown(request.getVal());
		}catch (Exception e){
			log.error("Exception zipCodeInformation : {}", e.getMessage(), e);
			errorMsg = "zipCodeInformation Internal server Error process";
		} finally {
			response = new ZipCodeInfoResp(errorMsg, list);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/collection-segment")
	public ResponseEntity<CollectionSegmentResp> getCollectionSegment() throws Exception {
		String errorMsg = null;
		List<CollectionSegmentDto> listDto = null;
		CollectionSegmentResp response = null;
		try {
			listDto = criteriaMasterService.getCollectionSegment();
		}catch (Exception e){
			log.error("Exception GetRegion : {}", e.getMessage(), e);
			errorMsg = "GetRegion Internal server Error process";
		} finally {
			response = new CollectionSegmentResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/bill-cycle")
	public ResponseEntity<CommonDropDownResp> getBillCycle() throws Exception {
		String errorMsg = null;
		List<CommonDropdownDto> listDto = null;
		CommonDropDownResp response = null;
		try {
			listDto = criteriaMasterService.getBillCycle();
		}catch (Exception e){
			log.error("Exception getBillCycle : {}", e.getMessage(), e);
			errorMsg = "getBillCycle Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ba-status")
	public ResponseEntity<CommonDropDownResp> getBaStatus() throws Exception {
		String errorMsg = null;
		List<CommonDropdownDto> listDto = null;
		CommonDropDownResp response = null;
		try {
			listDto = criteriaMasterService.getBastatus();
		}catch (Exception e){
			log.error("Exception getBastatus : {}", e.getMessage(), e);
			errorMsg = "getBastatus Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/mobile-status")
	public ResponseEntity<CommonDropDownResp> getMobileStatus() throws Exception {
		String errorMsg = null;
		List<CommonDropdownDto> listDto = null;
		CommonDropDownResp response = null;
		try {
			listDto = criteriaMasterService.getMobilestatus();
		}catch (Exception e){
			log.error("Exception getMobileStatus : {}", e.getMessage(), e);
			errorMsg = "getMobileStatus Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/module")
	public ResponseEntity<CommonDropDownResp> getModule() throws Exception {
		String errorMsg = null;
		List<CommonDropdownDto> listDto = null;
		CommonDropDownResp response = null;
		try {
			listDto = criteriaMasterService.getModule();
		}catch (Exception e){
			log.error("Exception getModule : {}", e.getMessage(), e);
			errorMsg = "getModule Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/exempt-level")
	public ResponseEntity<CommonDropDownResp> getExemptLevel() throws Exception {
		String errorMsg = null;
		List<CommonDropdownDto> listDto = null;
		CommonDropDownResp response = null;
		try {
			listDto = criteriaMasterService.getExemptLevel();
		}catch (Exception e){
			log.error("Exception getExemptLevel : {}", e.getMessage(), e);
			errorMsg = "getExemptLevel Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/company-code", produces = "application/json")
	public ResponseEntity<CommonDropDownResp> getCompanyInformation(
			@RequestBody CompanyCodeReq request) {
		String errorMsg = null;
		List<CommonDropdownDto> list = null;
		CommonDropDownResp response = null;
		try {
			list = criteriaMasterService.getCompanyByCode(request.getCompayCode());
		}catch (Exception e){
			log.error("Exception subdistrictInformation : {}", e.getMessage(), e);
			errorMsg = "subdistrictInformation Internal server Error process";
		} finally {
			response = new CommonDropDownResp(errorMsg, list);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/reason")
	public ResponseEntity<ReasonResp> getReason() throws Exception {
		String errorMsg = null;
		List<AddReasonDto> listDto = null;
		ReasonResp response = null;
		try {
			listDto = criteriaMasterService.getReason();
		}catch (Exception e){
			log.error("Exception getReason : {}", e.getMessage(), e);
			errorMsg = "getReason Internal server Error process";
		} finally {
			response = new ReasonResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/category")
	public ResponseEntity<CategoryResp> getCategory() throws Exception {
		String errorMsg = null;
		List<CategoryDto> listDto = null;
		CategoryResp response = null;
		try {
			listDto = criteriaMasterService.getCategory();
		}catch (Exception e){
			log.error("Exception getReason : {}", e.getMessage(), e);
			errorMsg = "getReason Internal server Error process";
		} finally {
			response = new CategoryResp(errorMsg, listDto);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sub-category", produces = "application/json")
	public ResponseEntity<SubCategoryResp> getSubCategory(
			@RequestBody SubCategoryReq request) {
		String errorMsg = null;
		List<SubCategoryDto> list = null;
		SubCategoryResp response = null;
		try {
			list = criteriaMasterService.getSubCategory(request.getCatCode());
		}catch (Exception e){
			log.error("Exception getSubCategory : {}", e.getMessage(), e);
			errorMsg = "getSubCategory Internal server Error process";
		} finally {
			response = new SubCategoryResp(errorMsg, list);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/seach-assign", produces = "application/json")
	public ResponseEntity<SearchAssignResp> getAssignId(
			@RequestBody SearchAssignReq request) {
		String errorMsg = null;
		List<GetRefAssignDto> list = null;
		SearchAssignResp response = null;
		try {
			list = criteriaMasterService.getRefAssignId(request.getAssignId());
		}catch (Exception e){
			log.error("Exception getSubCategory : {}", e.getMessage(), e);
			errorMsg = "getSubCategory Internal server Error process";
		} finally {
			response = new SearchAssignResp(errorMsg, list);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
			
}
