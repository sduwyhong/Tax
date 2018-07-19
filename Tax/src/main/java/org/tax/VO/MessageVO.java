package org.tax.VO;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-18
 */
@Data
public class MessageVO {
	//私信内容、接收人、发出人
	private String id;
	private String content;
	private String sender;
	private String receiver;
}
