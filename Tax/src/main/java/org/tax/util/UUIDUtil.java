package org.tax.util;

import java.util.UUID;

public class UUIDUtil {

	public static String genUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
