package org.tax.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tax.VO.LoginInfo;
import org.tax.VO.PasswordModification;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public interface TaxUserService {
	
	String updateInfo(TaxUser user);
	
	String modifyPassword(PasswordModification info);
	
	String publishQuestion(TaxQuestion question);
	
	String publishAnswer(TaxAnswer answer);
	
	String confirmSolution(int questionId);
	
	String collect(int questionId);
	
}
