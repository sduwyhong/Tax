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
	
	@RequestMapping(value="/search",method={RequestMethod.GET,RequestMethod.POST},produces=JSON)
	@ResponseBody
	public String search(@RequestParam("keyword") String keyword,@RequestParam("proId") String proId,@RequestParam("page") int page) {
		return taxGuestService.search(keyword, proId, page);
	}
	
	@RequestMapping(value="/question/{type}/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getByCondition(@PathVariable("type") String type, @PathVariable("page") int page) {
		return taxGuestService.getByCondition(type, page);
	}
	
	@RequestMapping(value="/question",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getQuestions(@RequestParam("page") int page) {
		return taxGuestService.getQuestions(page);
	}
	
	@RequestMapping(value="/userModule",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getUserModule() {
		return taxGuestService.getUserModule();
	}
	
	@RequestMapping(value="/personData/{userId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getPersonData(@PathVariable("userId") String userId) {
		return taxGuestService.getUserDetail(userId);
	}
	
	@RequestMapping(value="/share/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getShares(@PathVariable("page") int page) {
		return taxGuestService.getShares(page);
	}
	
	@RequestMapping(value="/expert/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getArticlesOfExperts(@PathVariable("page") int page) {
		return taxGuestService.getArticlesOfExperts(page);
	}
	
	@RequestMapping(value="/question/{questionId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getQuestion(@PathVariable("questionId") int questionId) {
		return taxGuestService.getQuestion(questionId);
	}
	
	@RequestMapping(value="/answer/{questionId}/{page}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getAnswer(@PathVariable("questionId") int questionId, @PathVariable("page") int page) {
		return taxGuestService.getAnswer(questionId, page);
	}
	
	@RequestMapping(value="/avatar/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public void getAvatar(@PathVariable("userId") String userId, HttpServletResponse response) {
		taxGuestService.getAvatar(userId, response);
	}
}
