package org.tax.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.tax.VO.Candidate;
import org.tax.VO.MessageReplyVO;
import org.tax.VO.MessageVO;
import org.tax.VO.PasswordModification;
import org.tax.VO.UserInfo;
import org.tax.constant.CookieConst;
import org.tax.constant.FilePathConst;
import org.tax.constant.Message;
import org.tax.constant.SessionConst;
import org.tax.constant.StatusCode;
import org.tax.dao.TaxInvitationMapper;
import org.tax.dao.TaxUserProMapper;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxAnswerKey;
import org.tax.model.TaxExpert;
import org.tax.model.TaxExpertExample;
import org.tax.model.TaxExpertKey;
import org.tax.model.TaxFavourite;
import org.tax.model.TaxFavouriteAnswer;
import org.tax.model.TaxFavouriteAnswerExample;
import org.tax.model.TaxFavouriteExample;
import org.tax.model.TaxInvitation;
import org.tax.model.TaxMessage;
import org.tax.model.TaxMessageReply;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionKey;
import org.tax.model.TaxQuestionPro;
import org.tax.model.TaxShare;
import org.tax.model.TaxShareExample;
import org.tax.model.TaxShareKey;
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

	private final Result OK = new Result();
	
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
		return JSONObject.toJSONString(result);
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
		user.setImage(null);
		user.setScore(null);
		user.setPrivilege(null);
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
		//TaxUser user = getUserFromRequest(request);
		//连续改两次密码，session中的数据不是最新的
		TaxUserKey key = new TaxUserKey();
		key.setId(getUserIdFromRequest(request));
		TaxUser user = mapperFactory.getTaxUserMapper().selectByPrimaryKey(key);
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

	private String getUserIdFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(CookieConst.USER)) {
				try {
					return URLDecoder.decode(cookie.getValue(), "UTF-8").split(";")[0];
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**以后修改为从鸿哥的Factory获取Session
	 * 更改需要：
	 * 细化检验各个字段
	 * */
	@Override
	public synchronized String publishQuestion(TaxQuestion question, String invitationList, HttpServletRequest request) {
		Result result = new Result();
		TaxUser author = getUserFromRequest(request);
		//判断积分是否足够
		//session缓存中的scores可能不够新，还是从数据库获取
		long scores = mapperFactory.getTaxUserMapper().selectScoresById(author.getId());
		if(scores < question.getPrize()){
			//不够：返回充值提示信息
			result.setMessage(Message.SCORES_NOT_ENOUGH);
			result.setStatus(StatusCode.SCORES_NOT_ENOUGH);
			return JSONObject.toJSONString(result);
		}

		//Question id 自增那么不需要管直接插入即可
		//设置问题的authorId
		//2018/7/12:wyhong
		question.setAuthorId(author.getId());
		//设置问题的publishDate
		question.setPublishDate(new Date());
		//存放问题
		int flag = mapperFactory.getTaxQuestionMapper().insert(question);
		if(flag<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}

		//足够：成功发布后扣除响应悬赏积分
		mapperFactory.getTaxUserMapper().minusScores(question.getPrize(),author.getId());
		//lucene创建索引
		question.setId(mapperFactory.getTaxQuestionMapper().getLastInsertId());
		LOGGER.debug("LAST_INSERT_ID:"+question.getId());
		LuceneUtil.creatIndex(question);
		//存放邀请列表
		if(invitationList != null && !"".equals(invitationList)) {
			int questionId = question.getId();
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
		String userId  = getUserIdFromRequest(request);
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
	public String confirmSolution(int questionId, int answerId, HttpServletRequest request) {
		//给tax_answer增加status，0代表未被采纳，1代表被采纳
		//帖子发布者采纳一个回答，则此问题被解决，question和answer的status都置为1
		//make sure the operator is the publisher
		Result result = new Result();
		TaxQuestionKey key = new TaxQuestionKey();
		key.setId(questionId);
		TaxQuestion question = mapperFactory.getTaxQuestionMapper().selectByPrimaryKey(key );
		if(!getUserFromRequest(request).getId().equals(question.getAuthorId())){
			result.setStatus(StatusCode.PERMISSION_DENIED);
			result.setMessage("you are not the publisher!");
		}else{
			question.setStatus(1);
			mapperFactory.getTaxQuestionMapper().updateByPrimaryKey(question);
			mapperFactory.getTaxAnswerMapper().updateStatus(1, answerId);
			//被采纳回答者积分+该问题的悬赏分数
			//帖子发布者-该问题的悬赏分数（发布咨询时已经扣除）
			int scores = question.getPrize();
			String answerAuthorId = ""; 
			TaxAnswerKey _key = new TaxAnswerKey();
			_key.setId(answerId);
			TaxAnswer answer = mapperFactory.getTaxAnswerMapper().selectByPrimaryKey(_key);
			if(scores > 0) {
				mapperFactory.getTaxUserMapper().addScores(scores, answer.getAuthorId());
				//mapperFactory.getTaxUserMapper().minusScores(scores, question.getAuthorId());
			}
		}
		return JSONObject.toJSONString(result);
	}

	@Override
	public String collectQuestion(int questionId, HttpServletRequest request) {
		TaxQuestionKey key = new TaxQuestionKey();
		key.setId(questionId);
		TaxQuestion question = mapperFactory.getTaxQuestionMapper().selectByPrimaryKey(key);
		//更新收藏数(写个sql比较高效！)
		question.setFavourite(question.getFavourite()+1);
		mapperFactory.getTaxQuestionMapper().updateByPrimaryKey(question);
		//2018/7/12 wyhong:插入收藏实体
		TaxFavourite favourite = new TaxFavourite();
		favourite.setQuestionId(questionId);
		favourite.setUserId(getUserFromRequest(request).getId());
		mapperFactory.getTaxFavouriteMapper().insert(favourite);
		return JSON.toJSONString(OK);
	}

	/**鸿哥说写编辑头像，图片上传*/
	@Override
	public String modifyAvatar(String userId, MultipartFile multipartFile) {
		File dir = new File(FilePathConst.AVATAR_DIR);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String filePath =  FilePathConst.AVATAR_DIR + multipartFile.getOriginalFilename();
		try {
			multipartFile.transferTo(new File(filePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//update user image
		mapperFactory.getTaxUserMapper().updateAvatarAddress(filePath, userId);
		return JSONObject.toJSONString(OK);
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

	@Override
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String userId = null;
		for(Cookie cookie:cookies) {
			if(CookieConst.USER.equals(cookie.getName())){
				cookie.setMaxAge(0);
				cookie.setPath(CookieConst.PATH);//necessary
				try {
					userId = URLDecoder.decode(cookie.getValue(),"UTF-8").split(";")[0];
					LOGGER.debug("logout userId:"+userId);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				response.addCookie(cookie);
			}
		}
		SessionControl.getInstance().rmSession(userId);
		return JSONObject.toJSONString(OK);
	}

	@Override
	public String collectAnswer(int answerId, int questionId,
			HttpServletRequest request) {
		//不可以收藏自己的帖子
		Result result = new Result();
		TaxAnswerKey key = new TaxAnswerKey();
		key.setId(answerId);
		TaxAnswer answer = mapperFactory.getTaxAnswerMapper().selectByPrimaryKey(key );
		String operatorId = getUserIdFromRequest(request);
		if(answer.getAuthorId().equals(operatorId)) {
			result.setStatus(StatusCode.PERMISSION_DENIED);
			result.setMessage("you can't star your own answers, narcissism");
		}else{
			answer.setFavourite(answer.getFavourite() + 1);
			mapperFactory.getTaxAnswerMapper().updateByPrimaryKey(answer);
			TaxFavouriteAnswer record = new TaxFavouriteAnswer();
			record.setQuestionId(questionId);
			record.setAnswerId(answerId);
			record.setUserId(operatorId);
			mapperFactory.getTaxFavouriteAnswerMapper().insert(record);
		}
		return JSONObject.toJSONString(result);
	}

	@Override
	public String likeAnswer(int answerId) {
		//点赞，加一就完事儿了
		mapperFactory.getTaxAnswerMapper().updateLike(answerId);
		return JSONObject.toJSONString(OK);
	}

	@Override
	public String sendMessage(TaxMessage message, HttpServletRequest request) {
		message.setSenderId(getUserIdFromRequest(request));
		mapperFactory.getTaxMessageMapper().insert(message);
		return JSONObject.toJSONString(OK);
	}

	@Override
	public String cancelCollectQuestion(int questionId,
			HttpServletRequest request) {
		TaxFavouriteExample example = new TaxFavouriteExample();
		String userId = getUserIdFromRequest(request);
		example.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(userId );
		int updateResult = mapperFactory.getTaxFavouriteMapper().deleteByExample(example);
		Result result = new Result();
		if(updateResult == 0){
			result.setStatus(StatusCode.INVALID_PARAMS);
			result.setMessage("you haven't star this question!");
		}else{
			mapperFactory.getTaxQuestionMapper().minusFavourite(questionId);
		}
		return JSONObject.toJSONString(result);
	}

	@Override
	public String checkFavouriteQuestion(int questionId,
			HttpServletRequest request) {
		TaxFavouriteExample example = new TaxFavouriteExample();
		example.createCriteria().andQuestionIdEqualTo(questionId).andUserIdEqualTo(getUserIdFromRequest(request));
		Result result = new Result();
		if(mapperFactory.getTaxFavouriteMapper().countByExample(example) > 0) result.setResult(true); 
		else result.setResult(false);
		return JSONObject.toJSONString(result);
	}

	@Override
	public String checkFavouriteAnswer(int answerId, HttpServletRequest request) {
		TaxFavouriteAnswerExample example = new TaxFavouriteAnswerExample();
		example.createCriteria().andAnswerIdEqualTo(answerId).andUserIdEqualTo(getUserIdFromRequest(request));
		Result result = new Result();
		if(mapperFactory.getTaxFavouriteAnswerMapper().countByExample(example) > 0) result.setResult(true); 
		else result.setResult(false);
		return JSONObject.toJSONString(result);
	}

	@Override
	public String cancelCollectAnswer(int answerId, HttpServletRequest request) {
		TaxFavouriteAnswerExample example = new TaxFavouriteAnswerExample();
		String userId = getUserIdFromRequest(request);
		example.createCriteria().andAnswerIdEqualTo(answerId).andUserIdEqualTo(userId );
		int updateResult = mapperFactory.getTaxFavouriteAnswerMapper().deleteByExample(example);
		Result result = new Result();
		if(updateResult == 0){
			result.setStatus(StatusCode.INVALID_PARAMS);
			result.setMessage("you haven't star this answer!");
		}else{
			//回答收藏数-1
			mapperFactory.getTaxAnswerMapper().minusFavourite(answerId);
		}
		
		return JSONObject.toJSONString(result);
	}

	@Override
	public String replyMessage(TaxMessageReply reply) {
		reply.setPublishDate(new Date());
		mapperFactory.getTaxMessagereplyMapper().insert(reply);
		mapperFactory.getTaxMessageMapper().updateStatus(reply.getMessageId());
		return JSONObject.toJSONString(OK);
	}

	@Override
	public String getMessageDetail(int messageId) {
		Result result = new Result();
		MessageVO mvo = mapperFactory.getTaxMessageMapper().selectVOById(messageId);
		List<MessageReplyVO> rvos = mapperFactory.getTaxMessagereplyMapper().selectByMessage(messageId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", mvo);
		map.put("reply", rvos);
		result.setResult(map);
		return JSONObject.toJSONString(result);
	}

	@Override
	public String publishQuestionByWyhong(TaxQuestion question,
			String invitationList, HttpServletRequest request) {
		Result result = new Result();
		TaxUser author = getUserFromRequest(request);
		//判断积分是否足够
		//session缓存中的scores可能不够新，还是从数据库获取
		long scores = mapperFactory.getTaxUserMapper().selectScoresById(author.getId());
		if(scores < question.getPrize()){
			//不够：返回充值提示信息
			result.setMessage(Message.SCORES_NOT_ENOUGH);
			result.setStatus(StatusCode.SCORES_NOT_ENOUGH);
			return JSONObject.toJSONString(result);
		}
		//设置问题的authorId
		question.setAuthorId(author.getId());
		//设置问题的date
		question.setPublishDate(new Date());
		//存放问题
		//int flag = mapperFactory.getTaxQuestionMapper().insert(question);
		LOGGER.debug("***********插入前咨询id："+question.getId());
		int flag = mapperFactory.getTaxQuestionMapper().insertSelective(question);
		LOGGER.debug("***********插入后咨询id："+question.getId());
		//足够：成功发布后扣除响应悬赏积分
		mapperFactory.getTaxUserMapper().minusScores(question.getPrize(),author.getId());
		//构建分类查询表
		//question.setId(mapperFactory.getTaxQuestionMapper().getLastInsertId());
		LOGGER.debug("******************last insert id:"+question.getId());
		if(!question.getType().equals("-")) {
			String[] proArr = question.getType().split(";");
			for(String proId : proArr){
				TaxQuestionPro record = new TaxQuestionPro();
				record.setProId(Integer.parseInt(proId));
				record.setQuestionId(question.getId());
				mapperFactory.getTaxQuestionProMapper().insert(record );
			}
		}
		//存放邀请列表
		if(invitationList != null && !"".equals(invitationList)) {
			int questionId = question.getId();
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

	@Override
	public String publishShareExp(TaxShare record) {
		//authorId前端给
		record.setPublishDate(new Date());
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxShareMapper().insert(record)));
	}

	@Override
	public String publishProInterpret(TaxExpert record) {
		record.setPublishDate(new Date());
		return JSONObject.toJSONString(new Result(mapperFactory.getTaxExpertMapper().insert(record)));
	}

}
