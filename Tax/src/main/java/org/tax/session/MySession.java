package org.tax.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wyhong
 * @date 2018-4-22
 */
public class MySession {

	private String sessionId;
	
	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	
	public MySession(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public void setAttribute(String key, Object obj) {
		attributes.put(key, obj);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public String getId() {
		return sessionId;
	}
}
