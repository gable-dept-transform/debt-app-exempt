package th.co.ais.mimo.debt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.ais.mimo.debt.dto.common.CommonDropdownListDto;
import th.co.ais.mimo.debt.model.common.CommonDropDownResponse;
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

}
