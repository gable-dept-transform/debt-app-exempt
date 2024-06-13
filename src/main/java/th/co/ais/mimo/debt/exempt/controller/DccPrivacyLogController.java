package th.co.ais.mimo.debt.exempt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.ais.mimo.debt.exempt.constant.AppConstant;
import th.co.ais.mimo.debt.exempt.model.PrivacyLogInsertRequest;
import th.co.ais.mimo.debt.exempt.model.PrivacyLogInsertResponse;
import th.co.ais.mimo.debt.exempt.service.DccPrivacyLogService;


@RestController
@RequestMapping("${api.path}/common/dcc-privacy-log")
public class DccPrivacyLogController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DccPrivacyLogService dccPrivacyLogService;

    @PostMapping(value = "/insert-log")
    public ResponseEntity<PrivacyLogInsertResponse> insertLog(@RequestHeader(name = AppConstant.X_LOCATION) Integer location,
                                                              @RequestHeader(name = AppConstant.X_USER_ID) String username,
                                                              RequestEntity<PrivacyLogInsertRequest> request)  {
        PrivacyLogInsertRequest insertReq = request.getBody();
        PrivacyLogInsertResponse response = new PrivacyLogInsertResponse();
        String resMsg = null;
        try {   
            resMsg = dccPrivacyLogService.insertDataLog(insertReq,username,location);
        } catch (Exception e) {
            log.error("Exception insertLog : {}", e.getMessage(), e);
            resMsg = "insertLog Internal server Error process";
        } finally {
                response = new PrivacyLogInsertResponse(resMsg);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
