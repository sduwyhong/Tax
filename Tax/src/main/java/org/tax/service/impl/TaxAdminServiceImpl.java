package org.tax.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.tax.VO.UserQuery;
import org.tax.constant.PageConst;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionExample;
import org.tax.result.Result;
import org.tax.service.TaxAdminService;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wyhong
 * @date 2018-7-25
 */
public class TaxAdminServiceImpl implements TaxAdminService {

	@Autowired
	MapperFactory mapperFactory;
	
	@Override
	public String getUncheckedQuestion(int page) {
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxQuestionMapper().getUncheckedQuestion(0, Integer.MAX_VALUE)));
	}

	@Override
	public String check(int questionId) {
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxQuestionMapper().check(questionId) > 0 ? true : false));
	}

	@Override
	public String getUserList(int page) {
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxUserMapper().selectList(0, Integer.MAX_VALUE)));
	}

	@Override
	public String getUserByCondition(UserQuery query, int page) {
		query.setOffset((page - 1) * PageConst.NUM_PER_PAGE);
		query.setNum(PageConst.NUM_PER_PAGE);
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxUserMapper().selectByCondition(query)));
	}

	@Override
	public String deleteUser(String userId) {
		return null;
	}

	@Override
	public String checkAuthority(String userId) {
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxUserMapper().selectPrivilegeByUserId(userId)));
	}

}
