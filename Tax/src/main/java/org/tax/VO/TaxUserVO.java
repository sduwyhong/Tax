package org.tax.VO;

import java.util.Date;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-18
 */
@Data
public class TaxUserVO {

	private String id;
	private int rank;
	private String username;
	private Date last_visit;
	private long scores;
	private long answerNum;
	private String email;
}
