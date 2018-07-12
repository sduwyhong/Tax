package org.tax.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartRequest;
import org.tax.VO.LoginInfo;
import org.tax.VO.PasswordModification;
import org.tax.model.TaxAnswer;
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
	
	String updateInfo(TaxUser user);
	
	//加了个参数HttpServletRequest request 获取当前用户 以后用鸿哥SessionFactory获取即可
	String modifyPassword(PasswordModification info, HttpServletRequest request);
	
	//加了个参数HttpServletRequest request 获取当前用户 以后用鸿哥SessionFactory获取即可
	String publishQuestion(TaxQuestion question, HttpServletRequest request);
	
	//加了个参数HttpServletRequest request 获取当前用户 以后用鸿哥SessionFactory获取即可
	String publishAnswer(TaxAnswer answer, HttpServletRequest request);
	
	String confirmSolution(int questionId);
	
	String collect(int questionId, HttpServletRequest request);
	
	String modifyAvatar(String userId, MultipartRequest multipartRequest);
	
}
