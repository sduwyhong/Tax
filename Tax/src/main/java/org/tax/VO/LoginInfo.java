package org.tax.VO;

import lombok.Data;

import org.tax.model.TaxAnswer;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class LoginInfo {
	private String username;
	private String password;
	private String verifyCode; 
}
