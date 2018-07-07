package org.tax.VO;

import lombok.Data;

import org.tax.model.TaxUser;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class UserModule {

	private int expertNum;
	private int solvedNum;
	private int confirmationNum;
	private TaxUser[] greateExperts;
	private TaxUser[] expertRankingList;
	
}
