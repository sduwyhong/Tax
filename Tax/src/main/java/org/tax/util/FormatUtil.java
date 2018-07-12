package org.tax.util;

public class FormatUtil {
	public static boolean rexCheckPassword(String input) {
		// 6-20 位，字母、数字、字符
		// String reg =
		// "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
		String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$";
		return input.matches(regStr);
	}
}
