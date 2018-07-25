package org.tax.service;

import org.tax.VO.UserQuery;

/**
 * @author wyhong
 * @date 2018-7-24
 */
public interface TaxAdminService {

	String getUncheckedQuestion(int page);

	String check(int questionId);

	String getUserList(int page);

	String getUserByCondition(UserQuery query, int page);

	String deleteUser(String userId);

	String checkAuthority(String userId);

}
