package org.tax.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tax.VO.LoginInfo;
import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
/**
 * 少了接口getQuestionDetail(String questionId)进入question详情页面的 还有应该加上page分页信息
 * 少了接口search(String keyword, String type, int page) 
 * 
 * */
public interface TaxGuestService {

	String register(TaxUser user, String code, HttpServletRequest request);
	
	String login(LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response);
	
	String search(String keyword, String proId, int page);
	
	String searchByWyhong(String keyword, String proId, int page);
	
	String getByCondition(String type, int page);//type=latest|hot|reward
	
	/**动态专区*/
	String getQuestions(int page);
	
	String getShares(int page);
	
	String getArticlesOfExperts(int page);

	String validateUsername(String username);

	String validateTelephone(String telephone);

	void verifyCode(HttpServletRequest request, HttpServletResponse response);

	String getQuestion(int questionId);

	String getAnswer(int questionId, int page);
	//用户专区（查询总用户数据、查询优秀用户、查询积分排行榜)
	String getUserModule();
	//个人界面（需要数据：我的提问、回答、发出的邀请、收到的邀请、收藏、发出的私信、收到的私信），每个模块显示4条
	String getUserDetail(String userId);
	
	String getQuestionsByUser(String userId);
	
	String getAnswersByUser(String userId);
	
	String getInvitationSentByUser(String userId);
	
	String getInvitationReceivedByUser(String userId);
	
	String getFavouriteByUser(String userId);
	
	String getMessagesSentByUser(String userId);
	
	String getMessagesReceivedByUser(String userId);
	
	void getAvatar(String userId, HttpServletResponse response);

	String ultimateSearch(String keyword, String proId, int page);
	
}
