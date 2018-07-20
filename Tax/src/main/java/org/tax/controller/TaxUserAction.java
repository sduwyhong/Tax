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
import org.springframework.web.multipart.MultipartFile;
import org.tax.VO.PasswordModification;
import org.tax.constant.MediaType;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxMessage;
import org.tax.model.TaxMessageReply;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxUser;
import org.tax.service.TaxUserService;

/**
 * @author wyhong
 * @date 2018-7-12
 */
@Component
@Controller
@RequestMapping("/api/v1/user")
public class TaxUserAction {

	final String JSON = MediaType.JSON;
	
	@Autowired
	TaxUserService taxUserService;
	
	@RequestMapping(value="/info",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getInfo(HttpServletRequest request) {
		return taxUserService.getInfo(request);
	}
	
	@RequestMapping(value="/getUserByPro",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getUserByPro(String proId) {
		return taxUserService.getUserByPro(proId);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String updateInfo(TaxUser user, HttpServletRequest request) {
		return taxUserService.updateInfo(user, request);
	}
	
	@RequestMapping(value="/password",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String modifyPassword(PasswordModification info, HttpServletRequest request) {
		return taxUserService.modifyPassword(info, request);
	}
	
	@RequestMapping(value="/question",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String publishQuestion(TaxQuestion question, String invitationList, HttpServletRequest request) {
		return taxUserService.publishQuestion(question, invitationList, request);
	}
	
	@RequestMapping(value="/answer",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String publishAnswer(TaxAnswer answer, HttpServletRequest request) {
		return taxUserService.publishAnswer(answer, request);
	}
	
	@RequestMapping(value="/message",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String sendMessage(TaxMessage message, HttpServletRequest request) {
		return taxUserService.sendMessage(message, request);
	}
	
	@RequestMapping(value="/confirm/{questionId}/{answerId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String confirmSolution(@PathVariable("questionId") int questionId,@PathVariable("answerId") int answerId, HttpServletRequest request) {
		return taxUserService.confirmSolution(questionId, answerId, request);
	}
	
	@RequestMapping(value="/likeAnswer/{answerId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String likeAnswer(@PathVariable("answerId") int answerId) {
		return taxUserService.likeAnswer(answerId);
	}
	
	@RequestMapping(value="/collectQuestion/{questionId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String collectQuestion(@PathVariable("questionId") int questionId, HttpServletRequest request) {
		return taxUserService.collectQuestion(questionId, request);
	}
	
	@RequestMapping(value="/collectAnswer/{questionId}/{answerId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String collectAnswer(@PathVariable("answerId")int answerId, @PathVariable("questionId")int questionId, HttpServletRequest request) {
		return taxUserService.collectAnswer(answerId, questionId, request);
	}
	
	@RequestMapping(value="/cancelCollectQuestion/{questionId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String cancelCollectQuestion(@PathVariable("questionId") int questionId, HttpServletRequest request) {
		return taxUserService.cancelCollectQuestion(questionId, request);
	}
	
	@RequestMapping(value="/cancelCollectAnswer/{answerId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String cancelCollectAnswer(@PathVariable("answerId") int answerId, HttpServletRequest request) {
		return taxUserService.cancelCollectAnswer(answerId, request);
	}
	
	@RequestMapping(value="/checkFavouriteQuestion/{questionId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String checkFavouriteQuestion(@PathVariable("questionId") int questionId, HttpServletRequest request) {
		return taxUserService.checkFavouriteQuestion(questionId, request);
	}
	
	@RequestMapping(value="/checkFavouriteAnswer/{answerId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String checkFavouriteAnswer(@PathVariable("answerId") int answerId, HttpServletRequest request) {
		return taxUserService.checkFavouriteAnswer(answerId, request);
	}
	
	@RequestMapping(value="/avatar/{userId}",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String modifyAvatar(@PathVariable("userId") String userId, @RequestParam("avatar") MultipartFile multipartFile) {
		return taxUserService.modifyAvatar(userId, multipartFile);
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return taxUserService.logout(request, response);
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.POST,produces=JSON)
	@ResponseBody
	public String reply(TaxMessageReply reply) {
		return taxUserService.replyMessage(reply);
	}
	
	@RequestMapping(value="/messageDetail/{messageId}",method=RequestMethod.GET,produces=JSON)
	@ResponseBody
	public String getMessageDetail(@PathVariable("messageId")int messageId) {
		return taxUserService.getMessageDetail(messageId);
	}
	
	
}
