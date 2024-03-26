package th.co.ais.mimo.debt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.co.ais.mimo.debt.service.QueryExemptService;

@RestController
@RequestMapping("${api.path}")
public class QueryExemptController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QueryExemptService queryExemptService;

	/*@PostMapping(value = "/list-menu")
	public ResponseEntity<MenuResp> getMenuByUser(RequestEntity<MenuReq> request) throws Exception {
		MenuReq menuDto = request.getBody();
		MenuResp response = null;
		List<MenuModel> listMenu = null;
		String errorMsg = null;
		try {
			if (menuDto != null) {
				menuDto.trim();
				listMenu = menuService.listMenuByUser(menuDto.getUserName());
			}
		} catch (Exception e) {
			log.error("Exception getMenuByUser : {}", e.getMessage(), e);
			errorMsg = "Get menu list Internal server Error process";
		} finally {
			response = new MenuResp(listMenu, errorMsg);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}*/

	
}
