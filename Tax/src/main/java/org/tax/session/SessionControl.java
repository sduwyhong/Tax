package org.tax.session;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author wyhong
 * @date 2018-4-20
 */
public class SessionControl {
	
	private static final Logger LOGGER = Logger.getLogger(SessionControl.class);
	
	private Map<String, MySession> sessionMap = new ConcurrentHashMap<String, MySession>();
	@Getter
	private List<String> validSessionId = new Vector<String>();
	
	private static SessionControl sessionControl = new SessionControl();
	
	private SessionControl(){};
	
	public static SessionControl getInstance(){
		return sessionControl;
	}
	
	public void addSession(String userId, MySession session) {
		if(sessionMap.containsKey(userId)){
			//session已存在
			MySession _pre = sessionMap.get(userId);
			LOGGER.info("_pre:"+_pre.getId());
			LOGGER.info("session:"+session.getId());
			if(!session.getId().equals(_pre.getId())) {
				//模拟invalidate()
				validSessionId.remove(_pre.getId());
			}
		}
		validSessionId.add(session.getId());
		sessionMap.put(userId, session);
	}
	
	public void rmSession(String userId) {
		sessionMap.remove(userId);
	}
	
	public MySession getSession(String userId) {
		return sessionMap.get(userId);
	}
	
	public int getNumOnline(){
		return validSessionId.size();
	}

	public boolean exists(String userId, String sessionId) {
		
		return sessionMap.containsKey(userId) && sessionMap.get(userId).getId().equals(sessionId);
	}
}
