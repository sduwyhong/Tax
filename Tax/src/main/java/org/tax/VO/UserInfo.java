package org.tax.VO;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class UserInfo {

	private String id;
	private String username;
	private long score;
	private String type;
	private String telephone;
	private String email;
	private String pro_list;
	private String image;
}
