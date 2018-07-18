package org.tax.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.tax.constant.CookieConst;
import org.tax.constant.MediaType;
import org.tax.constant.Message;
import org.tax.constant.SessionConst;
import org.tax.constant.StatusCode;
import org.tax.dao.TaxUserMapper;
import org.tax.model.TaxUserKey;
import org.tax.result.Result;
import org.tax.session.MySession;
import org.tax.session.SessionControl;
import org.tax.util.UUIDUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wyhong
 * @date 2018-7-12
 */
public class LoginInterceptor implements HandlerInterceptor {

	static final Logger LOGGER = Logger.getLogger(LoginInterceptor.class); 

	@Autowired
	TaxUserMapper taxUserMapper;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(CookieConst.USER)){
					//客户端有cookie，后台没session对象，则这里判断一下要不要补一个session
					String userId = URLDecoder.decode(cookie.getValue(),"UTF-8").split(";")[0];
					LOGGER.debug("userId :"+userId);
					MySession session = SessionControl.getInstance().getSession(userId);
					if(session == null){
						session = new MySession(UUIDUtil.genUUID());
						TaxUserKey key = new TaxUserKey();
						key.setId(userId);
						session.setAttribute(SessionConst.USER, taxUserMapper.selectByPrimaryKey(key));
						SessionControl.getInstance().addSession(userId, session);
					}
					return true;
				}
			}
		}
		Result result = new Result();
		result.setStatus(StatusCode.PERMISSION_DENIED);
		result.setMessage(Message.PERMISSION_DENIED);
		response.getWriter().write(JSONObject.toJSONString(result));
		response.setContentType(MediaType.JSON);
		return false;
	}

}
