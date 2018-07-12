package org.tax.VO;

import java.util.Date;

import lombok.Data;

@Data
public class QuestionLive {
	private Integer id;
	private String title;
	private String authorId;
	private String authorName;
	private Date publishDate;
}
