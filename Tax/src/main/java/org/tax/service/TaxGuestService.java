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
	
	String getByCondition(String type, int page);//type=latest|hot|reward
	
	/**动态专区*/
	String getQuestions(int page);
	
	String getShares(int page);
	
	String getArticlesOfExperts(int page);

	String validateUsername(String username);

	String validateTelephone(String telephone);

	void verifyCode(HttpServletRequest request, HttpServletResponse response);
	
}
