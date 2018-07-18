package org.tax.VO;

import java.util.List;

import lombok.Data;

import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class UserModule {

	private long userNum;
	private long answerNum;
	private long solvedNum;
	//都是按积分排序
	//private List<TaxUser> greateExperts;
	private List<TaxUserVO> expertRankingList;
	
}
