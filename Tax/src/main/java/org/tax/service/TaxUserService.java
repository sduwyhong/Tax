package org.tax.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.tax.VO.LoginInfo;
import org.tax.VO.PasswordModification;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxMessage;
import org.tax.model.TaxMessageReply;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
/**还需要加上 
 * 发私信
 * 邀请回答
 * 退出
 * */
public interface TaxUserService {
	
	String updateInfo(TaxUser user, HttpServletRequest request);
	
	String modifyPassword(PasswordModification info, HttpServletRequest request);
	
	//邀请回答可以用这个
	String publishQuestion(TaxQuestion question, String invitationList, HttpServletRequest request);
	
	String publishAnswer(TaxAnswer answer, HttpServletRequest request);
	
	String confirmSolution(int questionId, int answerId, HttpServletRequest request);
	
	String collectQuestion(int questionId, HttpServletRequest request);
	
	String modifyAvatar(String userId, MultipartFile multipartFile);
	
	String getUserByPro(String proId);

	String getInfo(HttpServletRequest request);

	String logout(HttpServletRequest request, HttpServletResponse response);
	
	String collectAnswer(int answerId, int questionId, HttpServletRequest request);
	
	String likeAnswer(int answerId);
	
	String sendMessage(TaxMessage message, HttpServletRequest request);

	String cancelCollectQuestion(int questionId, HttpServletRequest request);

	String checkFavouriteQuestion(int questionId, HttpServletRequest request);

	String checkFavouriteAnswer(int answerId, HttpServletRequest request);

	String cancelCollectAnswer(int answerId, HttpServletRequest request);
	
	String replyMessage(TaxMessageReply reply);
	
	String getMessageDetail(int messageId);
}
