package org.tax.result;

import lombok.Data;

/**
 * @author wyhong
 * @date 2018-7-7
 */
@Data
public class Result {

	private String status = "200";
	private String message = "OK";
	private Object result;
	
}
