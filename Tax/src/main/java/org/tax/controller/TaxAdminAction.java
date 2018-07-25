package org.tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tax.VO.UserQuery;
import org.tax.constant.MediaType;
import org.tax.factory.MapperFactory;
import org.tax.service.TaxAdminService;

/**
 * @author wyhong
 * @date 2018-7-24
 */
@Controller
@Component
@RequestMapping("/api/v1/admin")
public class TaxAdminAction {

	final String JSON = MediaType.JSON;
	
	@Autowired
	MapperFactory mapperFactory;
	
	@Autowired
	TaxAdminService taxAdminService;
	
	@RequestMapping(value="/question/unchecked/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getUncheckedQuestion(@PathVariable("page")int page){
		return taxAdminService.getUncheckedQuestion(page);
	}
	
	@RequestMapping(value="/question/check/{questionId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String check(@PathVariable("questionId")int questionId){
		return taxAdminService.check(questionId);
	}
	
	@RequestMapping(value="/user/list/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getUserList(@PathVariable("page")int page){
		return taxAdminService.getUserList(page);
	}
	
	@RequestMapping(value="/user/query",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getUserByCondition(UserQuery query, @RequestParam("page")int page){
		return taxAdminService.getUserByCondition(query, page);
	}
	
	@RequestMapping(value="/checkAuthority/{userId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String checkAuthority(@PathVariable("userId")String userId){
		return taxAdminService.checkAuthority(userId);
	}
	
	public String deleteUser(String userId){
		return taxAdminService.deleteUser(userId);
	}
	
}
