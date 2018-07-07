package org.tax.VO;

import lombok.Data;

import org.tax.model.TaxQuestion;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class ShareExpertDetail {
	
	private TaxQuestion question;
	private String authorName;
}
