package th.co.ais.mimo.debt.exempt.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.dto.CommonCheckListDto;
import th.co.ais.mimo.debt.exempt.dto.CommonDropdownListDto;
import th.co.ais.mimo.debt.exempt.dto.CpLocationDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateDetailDto;
import th.co.ais.mimo.debt.exempt.dto.DccExemptCateMasterDto;
import th.co.ais.mimo.debt.exempt.exception.ExemptException;
import th.co.ais.mimo.debt.exempt.model.AscLocationListResponse;
import th.co.ais.mimo.debt.exempt.model.CommonCheckListResponse;
import th.co.ais.mimo.debt.exempt.model.CommonDropDownResponse;
import th.co.ais.mimo.debt.exempt.model.ExemptCateDetailResponse;
import th.co.ais.mimo.debt.exempt.model.ExemptCateMasterResponse;
import th.co.ais.mimo.debt.exempt.model.LocationListResponse;
import th.co.ais.mimo.debt.exempt.model.ReservePackResponse;
import th.co.ais.mimo.debt.exempt.service.impl.CommonService;


@RestController
@RequestMapping("${api.path}/common")
public class CommonController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/mobile-status")
    public ResponseEntity<CommonDropDownResponse> getMobileStatus()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getMobileStatus();
            if(resultList.isEmpty()){
                errorMsg= "Mobile status not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get mobile status : {}", e.getMessage(), e);
            errorMsg = "Get mobile status Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exempt-action")
    public ResponseEntity<CommonDropDownResponse> getExemptAction()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptAction();
            if(resultList.isEmpty()){
                errorMsg= "exempt action not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get exempt action : {}", e.getMessage(), e);
            errorMsg = "Get exempt action Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/global-parameter/{keyword}/{sectionName}")
    public ResponseEntity<CommonDropDownResponse> getGlobalParameter(@PathVariable(name = "keyword") String keyword, @PathVariable(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getGlobalParameter(keyword,sectionName);
            if(resultList.isEmpty()){
                errorMsg= "keyword "+keyword+" and sectionName + "+sectionName +" not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getGlobalParameter : {}", e.getMessage(), e);
            errorMsg = "Get globalParameter Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exempt-reason/{reasonType}")
    public ResponseEntity<CommonDropDownResponse> getExemptReason(@PathVariable(name="reasonType") String reasonType)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getDccReason(reasonType);
            if(resultList.isEmpty()){
                errorMsg= "reason not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get reason : {}", e.getMessage(), e);
            errorMsg = "Get reason Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exempt-reason-code-and-type/{reasonCode}/{reasonType}")
    public ResponseEntity<CommonDropDownResponse> getExemptReason(@PathVariable(name="reasonCode") String reasonCode,@PathVariable(name="reasonType") String reasonType)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getDccReason(reasonCode,reasonType);
            if(resultList.isEmpty()){
                errorMsg= "reason not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get reason : {}", e.getMessage(), e);
            errorMsg = "Get reason Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/mode/{module}/{level}")
    public ResponseEntity<CommonDropDownResponse> getMode(@PathVariable(name="module") String module
            ,@PathVariable(name="level") String level
            ,@RequestHeader(name = AppConstant.X_LOCATION,required = false) Integer location)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        String userLocationId = location == null ? "" : location.toString();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getPopupMode(userLocationId,module,level);
            if(resultList.isEmpty()){
                errorMsg= "mode not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get mode : {}", e.getMessage(), e);
            errorMsg = "Get mode Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/cate")
    public ResponseEntity<ExemptCateMasterResponse> getCateMaster()  {
        ExemptCateMasterResponse response = new ExemptCateMasterResponse();

        List<DccExemptCateMasterDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.searchExemptCateMaster();
            if(resultList.isEmpty()){
                errorMsg= "cate not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get cate : {}", e.getMessage(), e);
            errorMsg = "Get cate Internal server Error process";
        } finally {
            response = new ExemptCateMasterResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/cate-detail/{cateCode}")
    public ResponseEntity<ExemptCateDetailResponse> getCateDetail(@PathVariable(name="cateCode") String cateCode)  {

        ExemptCateDetailResponse response = new ExemptCateDetailResponse();

        List<DccExemptCateDetailDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.searchExemptCateDetail(cateCode);
            if(resultList.isEmpty()){
                errorMsg= "cate not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception get cate : {}", e.getMessage(), e);
            errorMsg = "Get cate Internal server Error process";
        } finally {
            response = new ExemptCateDetailResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/reserved-pack")
    public ResponseEntity<ReservePackResponse> getReservedPack()  {
        ReservePackResponse response = new ReservePackResponse();


        String errorMsg = null;
        try {
            String packCodeList = commonService.getReservePack();
            response.setReservePackCodeList(packCodeList);
        } catch (ExemptException e) {
            log.error("Exception get cate : {}", e.getMessage(), e);
            errorMsg = "Get cate Internal server Error process";
            response = ReservePackResponse.builder().errorMsg(errorMsg).build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/drop-down-list/report-status")
    public ResponseEntity<CommonDropDownResponse> getReportStatus()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getReportStatus();
            if(resultList.isEmpty()){
                errorMsg= "Report Status not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getReportStatus : {}", e.getMessage(), e);
            errorMsg = "Get ReportStatus Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/company-code")
    public ResponseEntity<CommonDropDownResponse> getCompanyCode(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getCompanyCode(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Company Code not found";
            }else{
                response.setResultList(resultList);
            }
        } catch (Exception e) {
            log.error("Exception getCompanyCode : {}", e.getMessage(), e);
            errorMsg = "Get CompanyCode Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-status")
    public ResponseEntity<CommonDropDownResponse> getExemptStatus(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptStatus(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Exempt Status not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptStatus : {}", e.getMessage(), e);
            errorMsg = "Get ExemptStatus Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-format")
    public ResponseEntity<CommonDropDownResponse> getExemptFormat(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptFormat(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Exempt Format not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptFormat : {}", e.getMessage(), e);
            errorMsg = "Get ExemptFormat Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/group-by-for-summary")
    public ResponseEntity<CommonDropDownResponse> getGroupByForSummary(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getGroupByForSummary(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Group By For Summary not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getGroupByForSummary : {}", e.getMessage(), e);
            errorMsg = "Get GroupByForSummary Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-mode-rep")
    public ResponseEntity<CommonDropDownResponse> getExemptModeRep()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptModeRep();
            if(resultList.isEmpty()){
                errorMsg= "Exempt Mode Rep not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptModeRep : {}", e.getMessage(), e);
            errorMsg = "Get Exempt Mode Rep Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-mode")
    public ResponseEntity<CommonDropDownResponse> getExemptMode()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptMode();
            if(resultList.isEmpty()){
                errorMsg= "Exempt Mode not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptMode : {}", e.getMessage(), e);
            errorMsg = "Get Exempt Mode Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-level")
    public ResponseEntity<CommonDropDownResponse> getExemptLevel(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptLevel(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Exempt Level not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptLevel : {}", e.getMessage(), e);
            errorMsg = "Get Exempt Level Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-action")
    public ResponseEntity<CommonDropDownResponse> getExemptActionCaseDropdown()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptActionCaseDropdown();
            if(resultList.isEmpty()){
                errorMsg= "Exempt Action not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptAction : {}", e.getMessage(), e);
            errorMsg = "Get Exempt Action Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/mobile-status")
    public ResponseEntity<CommonDropDownResponse> getMobileStatusCaseDropdown(@RequestParam(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getMobileStatusCaseDropdown(sectionName);
            if(resultList.isEmpty()){
                errorMsg= "Mobile Status not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getMobileStatus : {}", e.getMessage(), e);
            errorMsg = "Get Mobile Status Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/drop-down-list/exempt-mobile-status")
    public ResponseEntity<CommonDropDownResponse> getExemptMobileStatus()  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getExemptMobileStatus();
            if(resultList.isEmpty()){
                errorMsg= "Mobile Status not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getExemptMobileStatus : {}", e.getMessage(), e);
            errorMsg = "Get Mobile Status Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/check-list/account-category")
    public ResponseEntity<CommonCheckListResponse> getAccountCategory()  {
        CommonCheckListResponse response = new CommonCheckListResponse();
        List<CommonCheckListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getAccountCategory();
            if(resultList.isEmpty()){
                errorMsg= "Account Category not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getAccountCategory : {}", e.getMessage(), e);
            errorMsg = "Get Account Category Internal server Error process";
        } finally {
            response = new CommonCheckListResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/check-list/account-sub-category")
    public ResponseEntity<CommonCheckListResponse> getAccountSubCategory(@RequestParam(name = "category") String category)  {
        CommonCheckListResponse response = new CommonCheckListResponse();
        List<CommonCheckListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getAccountSubCategory(category);
            if(resultList.isEmpty()){
                errorMsg= "Account Sub Category not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getAccountSubCategory : {}", e.getMessage(), e);
            errorMsg = "Get Account Sub Category Internal server Error process";
        } finally {
            response = new CommonCheckListResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/asc-location-list")
    public ResponseEntity<AscLocationListResponse> getASCLocationList(@RequestHeader(AppConstant.X_LOCATION) String username)  {
        AscLocationListResponse response = new AscLocationListResponse();
        Integer result = null;
        String errorMsg = null;

        try {
            result = commonService.getASCLocationList(username);
            if(result == null){
                errorMsg= "ASCLocationList not found";
            }else{
                response.setResult(result);
            }

        } catch (Exception e) {
            log.error("Exception getASCLocationList : {}", e.getMessage(), e);
            errorMsg = "Get ASCLocationList Internal server Error process";
        } finally {
            response = new AscLocationListResponse(result,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/location-list")
    public ResponseEntity<LocationListResponse> getLocationList(@RequestParam(name = "filterLocation") boolean filterLocation,
                                                                @RequestHeader(name = AppConstant.X_LOCATION,required = false) Integer locationId)  {
        LocationListResponse response = new LocationListResponse();
        List<CpLocationDto> result = new ArrayList<CpLocationDto>();
        String errorMsg = null;

        try {
            if(filterLocation) {
                result = commonService.getLocationList(locationId);
            } else {
                result = commonService.getLocationList(null);
            }

            if(result.isEmpty()){
                errorMsg= "getLocationList not found";
            }else{
                response.setResultList(result);
            }

        } catch (Exception e) {
            log.error("Exception getLocationList : {}", e.getMessage(), e);
            errorMsg = "Get LocationList Internal server Error process";
        } finally {
            response = new LocationListResponse(result,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/global-parameter-dropdown/{keyword}/{sectionName}")
    public ResponseEntity<CommonDropDownResponse> getInfoByKeyWordAndSectionNameCaseDropdown(@PathVariable(name = "keyword") String keyword, @PathVariable(name = "sectionName") String sectionName)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        List<CommonDropdownListDto> resultList = null;
        String errorMsg = null;
        try {
            resultList = commonService.getInfoByKeyWordAndSectionNameCaseDropdown(keyword,sectionName);
            if(resultList.isEmpty()){
                errorMsg= "keyword "+keyword+" and sectionName + "+sectionName +" not found";
            }else{
                response.setResultList(resultList);
            }

        } catch (Exception e) {
            log.error("Exception getInfoByKeyWordAndSectionNameCaseDropdown : {}", e.getMessage(), e);
            errorMsg = "Get InfoByKeyWordAndSectionNameCaseDropdown Internal server Error process";
        } finally {
            response = new CommonDropDownResponse(resultList,errorMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
