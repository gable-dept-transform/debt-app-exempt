package th.co.ais.mimo.debt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.ais.mimo.debt.dto.DccExemptCateDetail;
import th.co.ais.mimo.debt.dto.DccExemptCateMaster;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.model.common.CommonDropDownResponse;
import th.co.ais.mimo.debt.model.common.ExemptCateDetailResponse;
import th.co.ais.mimo.debt.model.common.ExemptCateMasterResponse;
import th.co.ais.mimo.debt.service.CommonService;

import java.util.List;


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
    public ResponseEntity<CommonDropDownResponse> getMode(@PathVariable(name="module") String module,@PathVariable(name="level") String level)  {
        CommonDropDownResponse response = new CommonDropDownResponse();
        String userLocationId = "";
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

        List<DccExemptCateMaster> resultList = null;
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

        List<DccExemptCateDetail> resultList = null;
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
}
