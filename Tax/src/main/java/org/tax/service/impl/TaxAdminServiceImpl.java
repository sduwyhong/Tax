package org.tax.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.tax.VO.UserQuery;
import org.tax.constant.PageConst;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxQuestion;
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
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxQuestionMapper().getUncheckedQuestion((page - 1) * PageConst.NUM_PER_PAGE, PageConst.NUM_PER_PAGE)));
	}

	@Override
	public String check(int questionId) {
		TaxQuestion record = new TaxQuestion();
		record.setId(questionId);
		record.setChecked(new Byte(Integer.toBinaryString(1)));
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxQuestionMapper().updateByPrimaryKey(record ) > 0 ? true : false));
	}

	@Override
	public String getUserList(int page) {
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxUserMapper().selectList((page - 1) * PageConst.NUM_PER_PAGE, PageConst.NUM_PER_PAGE)));
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

}
