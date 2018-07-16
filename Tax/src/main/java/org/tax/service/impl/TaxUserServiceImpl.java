package org.tax.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartRequest;
import org.tax.VO.Candidate;
import org.tax.VO.PasswordModification;
import org.tax.VO.UserInfo;
import org.tax.constant.CookieConst;
import org.tax.constant.Message;
import org.tax.constant.SessionConst;
import org.tax.constant.StatusCode;
import org.tax.dao.TaxInvitationMapper;
import org.tax.dao.TaxUserProMapper;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxFavourite;
import org.tax.model.TaxInvitation;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionExample;
import org.tax.model.TaxUser;
import org.tax.model.TaxUserExample;
import org.tax.model.TaxUserKey;
import org.tax.model.TaxUserPro;
import org.tax.model.TaxUserProExample;
import org.tax.result.Result;
import org.tax.service.TaxUserService;
import org.tax.session.MySession;
import org.tax.session.SessionControl;
import org.tax.util.FormatUtil;
import org.tax.util.LuceneUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public class TaxUserServiceImpl implements TaxUserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaxGuestServiceImpl.class);

	@Autowired
	private MapperFactory mapperFactory;

	@Override
	public String getUserByPro(String proId) {
		String[] proIds = proId.split(";");
		List<Integer> values = new ArrayList<Integer>();
		for (int i = 0; i < proIds.length; i++) {
			values.add(Integer.parseInt(proIds[i]));
		}
		TaxUserProExample example = new TaxUserProExample();
		example.setDistinct(true);
		example.createCriteria().andProIdIn(values);
		List<Candidate> list = mapperFactory.getTaxUserProMapper().selectByExample(example);
		Result result = new Result();
		result.setResult(list);
		return JSONObject.toJSONString(list);
	}

	@Override
	public String updateInfo(TaxUser user, HttpServletRequest request) {
		Result result = new Result();
		//检查是否用户在登陆状态(由拦截器搞)
		//检查用户名 密码是否正确?????????????
//		TaxUserExample exampleOfUser = new TaxUserExample();
//		exampleOfUser.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
//		List<TaxUser> selectUser = mapperFactory.getTaxUserMapper().selectByExample(exampleOfUser);
//		if(selectUser==null || selectUser.size()==0){
//			result.setMessage(Message.INVALID_PARAMS);
//			result.setStatus(StatusCode.INVALID_PARAMS);
//			return JSON.toJSONString(result);
//		}
		//修改基本信息
		String userId = getUserFromRequest(request).getId();
		TaxUserExample exampleOfUser = new TaxUserExample();
		exampleOfUser.createCriteria().andIdEqualTo(userId);
		int flag=mapperFactory.getTaxUserMapper().updateByExampleSelective(user, exampleOfUser);
		if(flag<=0){
			result.setMessage(Message.UPDATE_FALIUER);
			result.setStatus(StatusCode.UPDATE_FALIUER);
			return JSON.toJSONString(result);
		}
		//添加专业分类
		String[] proIds = user.getProList().split(";");
		for (int i = 0; i < proIds.length; i++) {
			TaxUserProMapper taxUserProMapper = mapperFactory.getTaxUserProMapper();
			TaxUserPro record = new TaxUserPro();
			record.setProId(Integer.parseInt(proIds[i]));
			record.setUserId(userId);
			taxUserProMapper.insert(record);
		}
		//avatar单独接口处理
		//更新完之后再更新一下session里的user
		MySession session = SessionControl.getInstance().getSession(userId);
		TaxUserKey key = new TaxUserKey();
		key.setId(userId);
		session.setAttribute(SessionConst.USER, mapperFactory.getTaxUserMapper().selectByPrimaryKey(key));
		return JSON.toJSONString(result);
	}

	/**以后修改为从鸿哥的Factory获取Session*/
	@Override
	public String modifyPassword(PasswordModification info, HttpServletRequest request) {
		Result result = new Result();
		//检查是否用户在登陆状态(由拦截器搞)
		//检查新旧密码是否一致
		if(info==null || info.getPassword()==null || info.getNewPassword()==null){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}

		/***************************************************************/
		//测试时候才打开
		//		TaxUserKey dubiUserKey = new TaxUserKey();
		//		dubiUserKey.setId("a9da429220a64a12a34264cd971acdf7");
		//		TaxUser dubi = mapperFactory.getTaxUserMapper().selectByPrimaryKey(dubiUserKey);
		//		TaxUser user = dubi;
		//		PasswordModification passwordInfo = new PasswordModification();
		//		passwordInfo.setPassword(dubi.getPassword());
		//		passwordInfo.setNewPassword("newpassowrd");
		//		info = passwordInfo;
		//		passwordInfo.setNewPassword("");//非法修改
		/***************************************************************/

		//从Session取出用户,确定旧密码是否正确
		//		TaxUser user = (TaxUser)request.getSession().getAttribute(SessionConst.USER);
		//2018/7/12:wyhong
		TaxUser user = getUserFromRequest(request);
		if(!user.getPassword().equals(info.getPassword())){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		else if(info.getPassword().equals(info.getNewPassword())){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		else if(!FormatUtil.rexCheckPassword(info.getNewPassword())){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		else{
			//更新密码
			user.setPassword(info.getNewPassword());
			mapperFactory.getTaxUserMapper().updateByPrimaryKey(user);
		}
		return JSON.toJSONString(result);
	}

	/**以后修改为从鸿哥的Factory获取Session
	 * 更改需要：
	 * 细化检验各个字段
	 * */
	@Override
	public synchronized String publishQuestion(TaxQuestion question, String invitationList, HttpServletRequest request) {
		Result result = new Result();
		//Question id 自增那么不需要管直接插入即可
		//设置问题的authorId
		//2018/7/12:wyhong
		TaxUser author = getUserFromRequest(request);
		question.setAuthorId(author.getId());
		//设置问题的publishDate
		question.setPublishDate(new Date());
		//存放问题
		int flag = mapperFactory.getTaxQuestionMapper().insert(question);
		if(flag<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		//lucene创建索引
		LuceneUtil.creatIndex(question);
		//存放邀请列表
		if(invitationList != null && !"".equals(invitationList)) {
			int questionId = mapperFactory.getTaxQuestionMapper().getLastInsertId();
			String[] invitations = invitationList.split(";");
			TaxInvitationMapper taxInvitationMapper = mapperFactory.getTaxInvitationMapper();
			for (int i = 0; i < invitations.length; i++) {
				TaxInvitation record = new TaxInvitation();
				record.setUserId(invitations[i]);
				record.setQuestionId(questionId);
				taxInvitationMapper.insert(record);
			}
		}
		return JSON.toJSONString(result);
	}

	private TaxUser getUserFromRequest(HttpServletRequest request) {
		String userId = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(CookieConst.USER)) {
				try {
					userId = URLDecoder.decode(cookie.getValue(), "UTF-8").split(";")[0];
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		LOGGER.debug("userId:"+userId);
		MySession session = SessionControl.getInstance().getSession(userId);
		LOGGER.debug("session num:"+SessionControl.getInstance().getNumOnline());
		LOGGER.debug("*****************sessionId in user services:"+session.getId()+";userId:"+userId);
		return (TaxUser) session.getAttribute(SessionConst.USER);
	}

	/**以后修改为从鸿哥的Factory获取Session
	 * 更改需要：
	 * 细化检验各个字段
	 * */
	@Override
	public String publishAnswer(TaxAnswer answer, HttpServletRequest request) {
		Result result = new Result();
		//Question id 自增那么不需要管直接插入即可
		//设置问题的authorId
		//		TaxUser author = (TaxUser) request.getSession().getAttribute(SessionConst.USER);
		//2018/7/12:wyhong
		TaxUser author = getUserFromRequest(request);
		answer.setAuthorId(author.getId());
		//设置问题的publishDate
		answer.setPublishDate(new Date());
		//存放问题
		int flag = mapperFactory.getTaxAnswerMapper().insert(answer);
		if(flag<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String confirmSolution(int questionId) {
		Result result = new Result();
		TaxQuestionExample exampleOfQuestion = new TaxQuestionExample();
		exampleOfQuestion.createCriteria().andIdEqualTo(questionId);
		List<TaxQuestion> questionList = mapperFactory.getTaxQuestionMapper().selectByExample(exampleOfQuestion);
		if(questionList.size()<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		else{
			TaxQuestion question = questionList.get(0);
			//确认解答
			question.setStatus(1);
			int flag=mapperFactory.getTaxQuestionMapper().updateByPrimaryKey(question);
			if(flag<=0){
				result.setMessage(Message.INVALID_PARAMS);
				result.setStatus(StatusCode.INVALID_PARAMS);
			}
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String collect(int questionId, HttpServletRequest request) {
		Result result = new Result();
		TaxQuestionExample exampleOfQuestion = new TaxQuestionExample();
		exampleOfQuestion.createCriteria().andIdEqualTo(questionId);
		List<TaxQuestion> questionList = mapperFactory.getTaxQuestionMapper().selectByExample(exampleOfQuestion);
		if(questionList.size()<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		else{
			TaxQuestion question = questionList.get(0);
			//更新收藏数
			question.setFavourite(question.getFavourite()+1);
			int flag=mapperFactory.getTaxQuestionMapper().updateByPrimaryKey(question);
			if(flag<=0){
				result.setMessage(Message.INVALID_PARAMS);
				result.setStatus(StatusCode.INVALID_PARAMS);
			}
			//2018/7/12 wyhong:插入收藏实体
			TaxFavourite favourite = new TaxFavourite();
			favourite.setQuestionId(questionId);
			favourite.setUserId(getUserFromRequest(request).getId());
			mapperFactory.getTaxFavouriteMapper().insert(favourite);
		}
		return JSON.toJSONString(result);
	}

	/**鸿哥说写编辑头像，图片上传*/
	@Override
	public String modifyAvatar(String userId,
			MultipartRequest multipartRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo(HttpServletRequest request) {
		TaxUser user = getUserFromRequest(request);
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(user.getUsername());
		userInfo.setTelephone(user.getTelephone());
		userInfo.setEmail(user.getEmail());
		userInfo.setPro_list(user.getProList());
		userInfo.setImage(user.getImage());
		Result result = new Result();
		result.setResult(userInfo);
		return JSONObject.toJSONString(result);
	}

}
