package org.tax.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartRequest;
import org.tax.VO.LoginInfo;
import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public interface TaxGuestService {

	String register(TaxUser user);
	
	String login(LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response);
	
	String search(String keyword, String proId);
	
	String getByCondition(String type, int page);//type=latest|hot|reward
	
	String getQuestions(String type, int page);
	
	String getShares(int page);
	
	String getArticlesOfExperts(int page);
	
}
