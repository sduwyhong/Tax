package org.tax.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tax.VO.LoginInfo;
import org.tax.constant.MediaType;
import org.tax.model.TaxUser;
import org.tax.service.TaxGuestService;

/**
 * @author wyhong
 * @date 2018-7-12
 */
@Component
@Controller
@RequestMapping("/api/v1/guest")
public class TaxGuestAction {
	
	final String JSON = MediaType.JSON;
	
	@Autowired
	TaxGuestService taxGuestService;
	
	@RequestMapping(value="/verifyCode",method=RequestMethod.GET)
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		taxGuestService.verifyCode(request, response);
	}
	
	@RequestMapping(value="/validateTelephone",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String validateTelephone(String telephone) {
		return taxGuestService.validateTelephone(telephone);
	}
	
	@RequestMapping(value="/validateUsername",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String validateUsername(String username) {
		return taxGuestService.validateUsername(username);
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String register(TaxUser user, @RequestParam("code") String code, HttpServletRequest request) {
		return taxGuestService.register(user, code, request);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String login(LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response) {
		return taxGuestService.login(loginInfo, request, response);
	}
	
	@RequestMapping(value="/search/{keyword}/{proId}/{page}",method={RequestMethod.GET,RequestMethod.POST},produces=JSON)
	@ResponseBody
	public String search(@PathVariable("keyword") String keyword,@PathVariable("proId") String proId,@PathVariable("page") int page) {
		return taxGuestService.search(keyword, proId, page);
	}
	
	@RequestMapping(value="/question/{type}/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getByCondition(@PathVariable("type") String type, @PathVariable("page") int page) {
		return taxGuestService.getByCondition(type, page);
	}
	
	@RequestMapping(value="/question/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getQuestions(@PathVariable("page") int page) {
		return taxGuestService.getQuestions(page);
	}
	
	@RequestMapping(value="/share/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getShares(@PathVariable("page") int page) {
		return taxGuestService.getShares(page);
	}
	
	@RequestMapping(value="/expert/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String method(@PathVariable("page") int page) {
		return taxGuestService.getArticlesOfExperts(page);
	}
}
