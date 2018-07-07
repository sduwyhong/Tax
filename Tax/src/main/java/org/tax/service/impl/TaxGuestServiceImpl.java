package org.tax.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tax.VO.LoginInfo;
import org.tax.model.TaxUser;
import org.tax.service.TaxGuestService;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public class TaxGuestServiceImpl implements TaxGuestService {

	@Override
	public String register(TaxUser user) {

		return null;
	}

	@Override
	public String login(LoginInfo loginInfo, HttpServletRequest request,
			HttpServletResponse response) {

		return null;
	}

	@Override
	public String search(String keyword, String proId) {

		return null;
	}

	@Override
	public String getByCondition(String type, int page) {

		return null;
	}

	@Override
	public String getQuestions(String type, int page) {

		return null;
	}

	@Override
	public String getShares(int page) {

		return null;
	}

	@Override
	public String getArticlesOfExperts(int page) {

		return null;
	}

}
