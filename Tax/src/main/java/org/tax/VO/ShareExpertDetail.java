package org.tax.VO;

import java.util.Date;

import lombok.Data;

import org.tax.model.TaxQuestion;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class ShareExpertDetail {
	/**这里不是返回一个Question吧*/
	//private TaxQuestion question;
	private String authorName;
	private String title;
	private Integer click;
	private Integer favourite;
	private Date publishDate;
}
