package org.tax.util;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.tax.constant.JSONConst;

/**
 * @author wyhong
 * @date 2018-3-28
 */
public class JSONUtil {
	
	static Map<String, String> jsonMap;
	//cache
	public static String success;
	public static String failure;
	
	static{
		success = JSONObject.toJSONString(new HashMap<String, String>().put(JSONConst.MESSAGE, JSONConst.SUCCESS));
		failure = JSONObject.toJSONString(new HashMap<String, String>().put(JSONConst.MESSAGE, JSONConst.FAILURE));
	}
	
	public static String createCustom(String message, String status) {
		jsonMap = new HashMap<String, String>();
		jsonMap.put(message, status);
		return JSONObject.toJSONString(jsonMap);
	}
	
}
