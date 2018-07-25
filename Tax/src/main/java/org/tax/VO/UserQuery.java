package org.tax.VO;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-25
 */
@Data
public class UserQuery {

	private String username;
	private String telephone;
	private String email;
	private long offset;
	private long num;
}
