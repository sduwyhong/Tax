package org.tax.VO;

import java.util.Date;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-18
 */
@Data
public class InvitationVO {
	//问题标题、日期、被邀请人、邀请人
	private Integer questionId;
	private String title;
	private Date publishDate;
	private String userReceived;
	private String userSent;
}
