package org.tax.validateCode;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.tax.util.UUIDUtil;

/**
 * @author wyhong
 * @date 2018-7-17
 */
public class ValidateCodeControl {

	static final Logger LOGGER = Logger.getLogger(ValidateCodeControl.class); 
	
	private Map<String, String> codeMap;
	
	private ValidateCodeControl(){
		new Thread(new CheckTime()).start();
		codeMap = new ConcurrentHashMap<String, String>();
	}
	
	private static ValidateCodeControl codeControl = new ValidateCodeControl();
	
	public static ValidateCodeControl getInstance(){
		return codeControl;
	}
	
	public String addCode(String code) {
		String key = UUIDUtil.genUUID();
		LOGGER.debug("token:"+key+";code:"+code);
		codeMap.put(key, code+";"+new Date().getTime());
		return key;
	}
	
	public boolean verify(String key, String code) {
		boolean flag = true;
		String[] codeInfo = codeMap.get(key).split(";");
		String correctCode = codeInfo[0];
		long createdTime = Long.parseLong(codeInfo[1]);
		LOGGER.debug("correct:"+correctCode+";input:"+code);
		if(correctCode == null || new Date().getTime() - createdTime > 60 * 1000L ||  (code != null && !correctCode.equalsIgnoreCase(code))) {
			flag = false;
		}
		codeMap.remove(key);
		return flag;
	}
	
	public void checkTime() {
		Set<String> keySet = codeMap.keySet();
		long now = new Date().getTime();
		for(String key : keySet) {
			long createdTime = Long.parseLong(codeMap.get(key).split(";")[1]);
			if(now - createdTime > 60 * 1000L) {
				codeMap.remove(key);
			}
		}
	}
}

class CheckTime implements Runnable{

	@Override
	public void run() {
		while(true) {
			ValidateCodeControl.getInstance().checkTime();
			try {
				Thread.sleep(60 * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
