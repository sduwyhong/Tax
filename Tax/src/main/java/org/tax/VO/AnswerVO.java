package org.tax.VO;

import java.util.Date;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-18
 */
@Data
public class AnswerVO {
	//问题标题、回答内容、问题浏览数、回答点赞数
	private Integer questionId;
	private Date publishDate;
	private String title;
	private String content;
	private long click;
	private long likes;
}
