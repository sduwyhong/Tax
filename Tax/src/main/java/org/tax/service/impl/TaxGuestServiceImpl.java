package org.tax.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tax.VO.LoginInfo;
import org.tax.VO.PageInfo;
import org.tax.VO.QuestionBrief;
import org.tax.VO.QuestionLive;
import org.tax.VO.ShareExpertDetail;
import org.tax.constant.CookieConst;
import org.tax.constant.Message;
import org.tax.constant.PageConst;
import org.tax.constant.SeperatorConst;
import org.tax.constant.SessionConst;
import org.tax.constant.StatusCode;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxAnswer;
import org.tax.model.TaxAnswerExample;
import org.tax.model.TaxExpert;
import org.tax.model.TaxExpertExample;
import org.tax.model.TaxPro;
import org.tax.model.TaxProKey;
import org.tax.model.TaxQuestion;
import org.tax.model.TaxQuestionExample;
import org.tax.model.TaxQuestionKey;
import org.tax.model.TaxShare;
import org.tax.model.TaxShareExample;
import org.tax.model.TaxUser;
import org.tax.model.TaxUserExample;
import org.tax.model.TaxUserKey;
import org.tax.result.Result;
import org.tax.service.TaxGuestService;
import org.tax.session.MySession;
import org.tax.session.SessionControl;
import org.tax.util.LuceneUtil;
import org.tax.util.UUIDUtil;
import org.tax.validateCode.ValidateCodeControl;

import cn.dsna.util.images.ValidateCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wyhong
 * @date 2018-7-7
 */
public class TaxGuestServiceImpl extends BaseServiceImpl<TaxUser> implements
		TaxGuestService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaxGuestServiceImpl.class);

	@Autowired
	private MapperFactory mapperFactory;

	@Override
	public String validateUsername(String username) {
		TaxUserExample example = new TaxUserExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TaxUser> list = mapperFactory.getTaxUserMapper().selectByExample(example);
		Result result = new Result();
		if(list.size() > 0){
			result.setStatus(StatusCode.DUPLICATE_USERNAME);
			result.setMessage(Message.DUPLICATE_USERNAME);
			return JSONObject.toJSONString(result);
		}
		return JSONObject.toJSONString(result);
	}
	
	@Override
	public String validateTelephone(String telephone) {
		TaxUserExample example = new TaxUserExample();
		example.createCriteria().andTelephoneEqualTo(telephone);
		List<TaxUser> list = mapperFactory.getTaxUserMapper().selectByExample(example);
		Result result = new Result();
		if(list.size() > 0){
			result.setStatus(StatusCode.DUPLICATE_USERNAME);
			result.setMessage(Message.DUPLICATE_USERNAME);
			return JSONObject.toJSONString(result);
		}
		return JSONObject.toJSONString(result);
	}
	
	@Override
	public String register(TaxUser user, String code, HttpServletRequest request) {
		Result result = new Result();
		/**
		 * 未加验证码 
		 * 未加验证:用户邮箱格式
		 * */
		//verifycode
		String token  = getTokenFromCookie(request);
		if(token != null && code != null && !ValidateCodeControl.getInstance().verify(token, code)) {
			result.setStatus(StatusCode.INVALID_VERIFYCODE);
			result.setMessage(Message.INVALID_VERIFYCODE);
			return JSONObject.toJSONString(result);
		}
		//validation part
		Validator validator = new Validator();
		List<ConstraintViolation> list = validator.validate(user);
		if(list.size() > 0) {
			result.setStatus(StatusCode.INVALID_PARAMS);
			result.setMessage(Message.INVALID_PARAMS);
			List<String> errors = new ArrayList<String>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ConstraintViolation constraintViolation = (ConstraintViolation) iterator
						.next();
				errors.add(constraintViolation.getMessage());
			}
			result.setResult(errors);
			return JSON.toJSONString(result);
		}
//		if (user.getUsername() == null) {
//			// 用户名为空s
//			result.setMessage(Message.INVALID_USERNAME_OR_PASSWORD);
//			result.setStatus(StatusCode.INVALID_USERNAME_OR_PASSWORD);
//			return JSON.toJSONString(result);
//		}
		//make sure the username hasn't been used yet
		TaxUserExample exampleOfUser = new TaxUserExample();
		exampleOfUser.createCriteria().andUsernameEqualTo(user.getUsername());
		if (mapperFactory.getTaxUserMapper().selectByExample(exampleOfUser)
				.size() > 0) {
			// 用户名已存在
			result.setMessage(Message.DUPLICATE_USERNAME);
			result.setStatus(StatusCode.DUPLICATE_USERNAME);
			return JSON.toJSONString(result);
		}
//		else if(!FormatUtil.rexCheckPassword(user.getPassword())){
//			//密码不符合格式
//			result.setMessage(Message.PASSWORD_INVALID_FORMAT);
//			result.setStatus(StatusCode.PASSWORD_INVALID_FORMAT);
//			return JSON.toJSONString(result);
//		}
		try {
			// 添加注册时间字段(不知道是否需要)
			user.setId(UUIDUtil.genUUID());
			user.setLastVisit(new Date());
			mapperFactory.getTaxUserMapper().insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			//invaild params 有歧义
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
		}
		return JSON.toJSONString(result);
	}

	@Override
	public String login(LoginInfo loginInfo, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		/**
		 * 未添加功能： 验证码
		 * */
		String token  = getTokenFromCookie(request);
		String code = loginInfo.getVerifyCode();
		if(token != null && code != null && !ValidateCodeControl.getInstance().verify(token, code)) {
			result.setStatus(StatusCode.INVALID_VERIFYCODE);
			result.setMessage(Message.INVALID_VERIFYCODE);
			return JSONObject.toJSONString(result);
		}
		
		if(loginInfo==null || loginInfo.getUsername()==null || loginInfo.getPassword()==null){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		/**
		 * 判断用户名和密码是否正确(用户名是唯一的)
		 * */
		TaxUserExample example = new TaxUserExample();
		example.createCriteria().andUsernameEqualTo(loginInfo.getUsername())
				.andPasswordEqualTo(loginInfo.getPassword());
		List<TaxUser> list = mapperFactory.getTaxUserMapper().selectByExample(
				example);
		if (list == null || list.size() == 0) {
			// 用户名或密码不对
			result.setMessage(Message.INVALID_USERNAME_OR_PASSWORD);
			result.setStatus(StatusCode.INVALID_USERNAME_OR_PASSWORD);
			return JSON.toJSONString(result);
		} else {
			TaxUser user = list.get(0);
			// 更新最新登陆时间
			user.setLastVisit(new Date());
			/** 标志是否记住密码 */
			boolean flag = false;
			//2018/7/12 wyhong
			//id;username;password
			Cookie newCookie = null;
			try {
				newCookie = new Cookie(CookieConst.USER,
						URLEncoder.encode(user.getId()+";"
								+loginInfo.getUsername() + ";"
								+ loginInfo.getPassword(),"UTF-8"));
				newCookie.setPath(CookieConst.PATH);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 默认cookie 30min后失效
			int maxAge = 60 * 30;
			if (flag) {
				// 设置cookie 一个月后失效
				maxAge = 30 * 24 * 60 * 60;
			}
			newCookie.setMaxAge(maxAge);
			newCookie.setPath(CookieConst.PATH);
			response.addCookie(newCookie);
			// get 跨域session
			//2018/7/12 wyhong
			MySession session = new MySession(UUIDUtil.genUUID());
			SessionControl.getInstance().addSession(user.getId(), session);
			session.setAttribute(SessionConst.USER, user);
			LOGGER.debug("*****************sessionId in login section:"+session.getId()+";userId:"+user.getId());
			//test getting a session
			LOGGER.debug("session num:"+SessionControl.getInstance().getNumOnline());
			LOGGER.debug("get session:"+SessionControl.getInstance().getSession(user.getId()).getId());
			return JSON.toJSONString(result);
		}
	}

	public String getTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if("token".equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 这里假定索引库与数据库是能保持一致的
	 * 这里若page<=0 或者 page>totalPage LuceneUtil会抛出异常
	 * 捕获后返回invalid params异常
	 * */
	@Override
	public String search(String keyword, String type, int page) {
		try {
//			From OnlineShop:
//			5.js以GET方式提交中文参数到后台出现乱码?????
//			原因：get方式提交的参数编码，只支持iso-8859-1编码，而且出现“?”也表明编码为iso-8859-1。
//			解决：以iso-8859-1编码为原始字节，再以utf-8解码即可
			keyword = keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		LOGGER.debug("keyword:"+keyword+";type:"+type);
		// 每个页的搜索栏，根据关键字搜索问题
		Result result = new Result();
		if(keyword!=null && type!=null){//待删
			try {
				List<TaxQuestion> questionLuceneList = LuceneUtil.search(keyword, type,
						page, PageConst.NUM_PER_PAGE);
				List<TaxQuestion> questionList = new ArrayList<TaxQuestion>();
				for(TaxQuestion questionLucene:questionLuceneList){
					TaxQuestionKey questionKey = new TaxQuestionKey();
					//7/17 20:37
					questionKey.setId(questionLucene.getId());
					TaxQuestion question = mapperFactory.getTaxQuestionMapper().selectByPrimaryKey(questionKey);
					questionList.add(question);
				}
				//List<QuestionBrief> questionBriefList = getQuestionBriefList(questionLuceneList);
				List<QuestionBrief> questionBriefList = getQuestionBriefList(questionList);
				//设置PageInfo
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCurrentPage(page);
				pageInfo.setCurrentCount(questionBriefList.size());
				// 要计算一下
				TaxQuestionExample exampleOfQuestion = new TaxQuestionExample();
				//???????????????
				long totalCount = mapperFactory.getTaxQuestionMapper().countByExample(exampleOfQuestion);
				long totalPage = totalCount / PageConst.NUM_PER_PAGE
						+ ((totalCount % PageConst.NUM_PER_PAGE == 0) ? 0 : 1);
				// 设置pageInfo
				pageInfo.setList(questionBriefList);
				// 设置result
				result.setResult(pageInfo);
				return JSON.toJSONString(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		result.setMessage(Message.INVALID_PARAMS);
		result.setStatus(StatusCode.INVALID_PARAMS);
		return JSON.toJSONString(result);
	}

	/**
	 * 这里默认页码传入是从下标为1开始处理的 并且页码都是合法的 返回的是QuestionBrief的
	 * */
	@Override
	public String getByCondition(String type, int page) {
		Result result = new Result();
		if(type==null || page<=0){
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		// 最新、热门、悬赏然后排序（降序）显示即可
		// type=latest|hot|reward
		TaxQuestionExample exampleOfQuestion = new TaxQuestionExample();
		// 根据传入的type设置查询的orderByClause
		if (type.equalsIgnoreCase("latest")) {
			exampleOfQuestion.setOrderByClause("publish_date DESC");
		} else if (type.equalsIgnoreCase("hot")) {
			exampleOfQuestion.setOrderByClause("click DESC");
		} else if (type.equalsIgnoreCase("reward")) {
			exampleOfQuestion.setOrderByClause("prize DESC");
		} else {
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		// 封装PageBean
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage((long) page);
		pageInfo.setCurrentCount(PageConst.NUM_PER_PAGE);
		// 要计算一下
		long totalCount = mapperFactory.getTaxQuestionMapper().countByExample(exampleOfQuestion);
		long totalPage = totalCount / PageConst.NUM_PER_PAGE
				+ ((totalCount % PageConst.NUM_PER_PAGE == 0) ? 0 : 1);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setTotalCount(totalCount);
		// 若请求的页面不合法，抛出异常
		if (page < 1 || page > totalPage) {
			try {
				throw new Exception("UNKNOWN ERROR: invalid query page");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		else if((page==totalPage) && (totalCount%PageConst.NUM_PER_PAGE!=0)){
			pageInfo.setCurrentCount((long) totalCount%PageConst.NUM_PER_PAGE);
		}
		// 设置查询sql的limitClause
		Long offset = (pageInfo.getCurrentPage() - 1) * PageConst.NUM_PER_PAGE;
		Long limit = pageInfo.getCurrentCount();
		StringBuilder limitClauseSB = new StringBuilder();
		limitClauseSB.append(offset).append(",").append(limit);
		exampleOfQuestion.setLimitClause(limitClauseSB.toString());
		// 先获取请求页面对应的问题列表
		List<TaxQuestion> questionList = mapperFactory.getTaxQuestionMapper().selectByExample(exampleOfQuestion);
		// 根据请求页面的问题列表，包装成问题简介列表，然后放到pageBean中
		List<QuestionBrief> questionBriefList = getQuestionBriefList(questionList);
		// 设置pageInfo
		pageInfo.setList(questionBriefList);
		// 设置result
		result.setResult(pageInfo);
		return JSON.toJSONString(result);
	}

	/** 答题专区 用户动态 类似调用getByCondition("latest")即可完成 封装成QuestionLive返回即可 */
	@Override
	public String getQuestions(int page) {
		// 答题专区用户动态
		Result result = new Result();
		TaxQuestionExample exampleOfQuestion = new TaxQuestionExample();
		//由新到旧排序 再按点击排序 再按照收藏排序
		exampleOfQuestion.setOrderByClause("publish_date DESC, click DESC, favourite DESC");
		// 封装PageBean
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage((long) page);
		pageInfo.setCurrentCount(PageConst.NUM_PER_PAGE);
		// 要计算一下
		long totalCount = mapperFactory.getTaxQuestionMapper().countByExample(
				exampleOfQuestion);
		long totalPage = totalCount / PageConst.NUM_PER_PAGE
				+ ((totalCount % PageConst.NUM_PER_PAGE == 0) ? 0 : 1);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setTotalCount(totalCount);
		// 若请求的页面不合法，抛出异常
		if (page < 1 || page > totalPage) {
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		else if((page==totalPage) && (totalCount%PageConst.NUM_PER_PAGE!=0)){
			pageInfo.setCurrentCount((long) totalCount%PageConst.NUM_PER_PAGE);
		}
		// 设置查询sql的limitClause
		Long offset = (pageInfo.getCurrentPage() - 1) * PageConst.NUM_PER_PAGE;
		Long limit = pageInfo.getCurrentCount();
		StringBuilder limitClauseSB = new StringBuilder();
		limitClauseSB.append(offset).append(",").append(limit);
		exampleOfQuestion.setLimitClause(limitClauseSB.toString());
		// 先获取请求页面对应的问题列表
		List<TaxQuestion> questionList = mapperFactory.getTaxQuestionMapper()
				.selectByExample(exampleOfQuestion);
		List<QuestionLive> questionLiveList = getQuestionLiveList(questionList);
		// 设置pageInfo
		pageInfo.setList(questionLiveList);
		// 设置result
		result.setResult(pageInfo);
		return JSON.toJSONString(result);
	}

	/** 经验分享 */
	@Override
	public String getShares(int page) {
		Result result = new Result();
		TaxShareExample exampleOfShare = new TaxShareExample();
		// 封装PageBean
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage((long) page);
		pageInfo.setCurrentCount(PageConst.NUM_PER_PAGE);
		// 要计算一下
		long totalCount = mapperFactory.getTaxShareMapper().countByExample(
				exampleOfShare);
		long totalPage = totalCount / PageConst.NUM_PER_PAGE
				+ ((totalCount % PageConst.NUM_PER_PAGE == 0) ? 0 : 1);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setTotalCount(totalCount);
		// 若请求的页面不合法，抛出异常
		if (page < 1 || page > totalPage) {
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		else if((page==totalPage) && (totalCount%PageConst.NUM_PER_PAGE!=0)){
			pageInfo.setCurrentCount((long) totalCount%PageConst.NUM_PER_PAGE);
		}
		// 根据分页信息获取经验分享列表(默认 先按照点击降序 再按照收藏降序 再按照时间)
		List<TaxShare> shareList = null;
		exampleOfShare.setOrderByClause("click DESC, favourite DESC, publish_date DESC");
		// 设置查询sql的limitClause
		Long offset = (pageInfo.getCurrentPage() - 1) * PageConst.NUM_PER_PAGE;
		Long limit = pageInfo.getCurrentCount();
		StringBuilder limitClauseSB = new StringBuilder();
		limitClauseSB.append(offset).append(",").append(limit);
		exampleOfShare.setLimitClause(limitClauseSB.toString());
		shareList = mapperFactory.getTaxShareMapper().selectByExample(
				exampleOfShare);
		// 构造ShareExpertDetail List
		List<ShareExpertDetail> shareExpertDetailList = new ArrayList<ShareExpertDetail>();
		for (TaxShare share : shareList) {
			ShareExpertDetail shareExpertDetail = new ShareExpertDetail();
			// 根据share中的authorId去TaxUser表找出authorName对象
			TaxUserKey userKey = new TaxUserKey();
			userKey.setId(share.getAuthorId());
			TaxUser author = mapperFactory.getTaxUserMapper()
					.selectByPrimaryKey(userKey);
			shareExpertDetail.setAuthorName(author.getUsername());
			// 根据share中的authorId找出question对象
			shareExpertDetail.setTitle(share.getTitle());
			shareExpertDetail.setClick(share.getClick());
			shareExpertDetail.setFavourite(share.getFavourite());
			shareExpertDetail.setPublishDate(share.getPublishDate());
			// 加入队列
			shareExpertDetailList.add(shareExpertDetail);
		}
		// 设置pageInfo
		pageInfo.setList(shareExpertDetailList);
		// 设置result
		result.setResult(pageInfo);
		return JSON.toJSONString(result);
	}

	/** 专业精读 */
	@Override
	public String getArticlesOfExperts(int page) {
		Result result = new Result();
		TaxExpertExample exampleOfExpert = new TaxExpertExample();
		// 封装PageBean
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage((long) page);
		pageInfo.setCurrentCount(PageConst.NUM_PER_PAGE);
		// 要计算一下
		long totalCount = mapperFactory.getTaxExpertMapper().countByExample(
				exampleOfExpert);
		long totalPage = totalCount / PageConst.NUM_PER_PAGE
				+ ((totalCount % PageConst.NUM_PER_PAGE == 0) ? 0 : 1);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setTotalCount(totalCount);
		// 若请求的页面不合法，抛出异常
		if (page < 1 || page > totalPage) {
			result.setMessage(Message.INVALID_PARAMS);
			result.setStatus(StatusCode.INVALID_PARAMS);
			return JSON.toJSONString(result);
		}
		else if((page==totalPage) && (totalCount%PageConst.NUM_PER_PAGE!=0)){
			pageInfo.setCurrentCount((long) totalCount%PageConst.NUM_PER_PAGE);
		}
		// 根据分页信息获取专家经验分享列表(默认 先按照点击降序 再按照收藏降序)
		List<TaxExpert> expertList = null;
		exampleOfExpert.setOrderByClause("click DESC, favourite DESC, publish_date DESC");
		// 设置查询sql的limitClause
		Long offset = (pageInfo.getCurrentPage() - 1) * PageConst.NUM_PER_PAGE;
		Long limit = pageInfo.getCurrentCount();
		StringBuilder limitClauseSB = new StringBuilder();
		limitClauseSB.append(offset).append(",").append(limit);
		exampleOfExpert.setLimitClause(limitClauseSB.toString());
		expertList = mapperFactory.getTaxExpertMapper().selectByExample(
				exampleOfExpert);
		// 构造ShareExpertDetail List
		List<ShareExpertDetail> shareExpertDetailList = new ArrayList<ShareExpertDetail>();
		for (TaxExpert expert : expertList) {
			ShareExpertDetail shareExpertDetail = new ShareExpertDetail();
			// 根据share中的authorId去TaxUser表找出authorName对象
			TaxUserKey userKey = new TaxUserKey();
			userKey.setId(expert.getAuthorId());
			TaxUser author = mapperFactory.getTaxUserMapper()
					.selectByPrimaryKey(userKey);
			shareExpertDetail.setAuthorName(author.getUsername());
			// 根据share中的authorId找出question对象
			shareExpertDetail.setTitle(expert.getTitle());
			shareExpertDetail.setClick(expert.getClick());
			shareExpertDetail.setFavourite(expert.getFavourite());
			// 加入队列
			shareExpertDetailList.add(shareExpertDetail);
		}
		// 设置pageInfo
		pageInfo.setList(shareExpertDetailList);
		// 设置result
		result.setResult(pageInfo);
		return JSON.toJSONString(result);
	}

	private List<QuestionBrief> getQuestionBriefList(
			List<TaxQuestion> questionList) {
		List<QuestionBrief> questionBriefList = new ArrayList<QuestionBrief>();
		for (TaxQuestion question : questionList) {
			QuestionBrief qb = new QuestionBrief();
			qb.setId(question.getId());// id
			qb.setTitle(question.getTitle());// 标题
			qb.setClick(question.getClick());// 浏览
			qb.setFavourite(question.getFavourite());// 收藏
			qb.setStatus(question.getStatus());
			qb.setReward(question.getPrize());
			//分类名称
			String[] questionTypeNameList = getQuestionTypeNameList(question
					.getType());
			StringBuilder questionBriefTypeSB = new StringBuilder();
			for (int i = 0; i < questionTypeNameList.length; i++) {
				if (i == questionTypeNameList.length - 1)
					questionBriefTypeSB.append(questionTypeNameList[i]);
				else
					questionBriefTypeSB.append(questionTypeNameList[i]).append(
							SeperatorConst.QUESTION_BRIEF_TYPE_SEPERATOR);
			}
			qb.setType(questionBriefTypeSB.toString());// 种类
			qb.setPublishDate(question.getPublishDate());// 发布日期
			/** 从answer表中查询时该qid的总数 */
			TaxAnswerExample exampleOfAnswer = new TaxAnswerExample();
			/** question id 有点小问题应该为Integer */
			exampleOfAnswer.createCriteria().andQuestionIdEqualTo(
					question.getId());
			Long totalAnswerNumOfQuestion = mapperFactory.getTaxAnswerMapper()
					.countByExample(exampleOfAnswer);
			qb.setTotalAnswerNum(totalAnswerNumOfQuestion);
			// 加入队列
			questionBriefList.add(qb);
		}
		return questionBriefList;
	}

	private String[] getQuestionTypeNameList(String questionTypeStr) {
		/** 这里直接返回种类列表的"1;2;3;5"->"名称1;名称2;名称3;名称5" */
		String[] questionTypeIdStrList = questionTypeStr
				.split(SeperatorConst.QUESTION_TYPE_SEPERATOR);
		String[] questionTypeNameList = new String[questionTypeIdStrList.length];
		for (int i = 0; i < questionTypeIdStrList.length; i++) {
			try {
				Integer typeId = Integer.parseInt(questionTypeIdStrList[i]);
				TaxProKey proKey = new TaxProKey();
				proKey.setId(typeId);
				TaxPro pro = mapperFactory.getTaxProMapper()
						.selectByPrimaryKey(proKey);
				// id唯一
				questionTypeNameList[i] = pro.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return questionTypeNameList;
	}

	private List<QuestionLive> getQuestionLiveList(
			List<TaxQuestion> questionList) {
		List<QuestionLive> questionLiveList = new ArrayList<QuestionLive>();
		for (TaxQuestion question : questionList) {
			QuestionLive questionLive = new QuestionLive();
			questionLive.setId(question.getId());
			questionLive.setTitle(question.getTitle());
			questionLive.setPublishDate(question.getPublishDate());
			questionLive.setAuthorId(question.getAuthorId());
			// 根据authorId查出authorName
			TaxUserKey userKey = new TaxUserKey();
			userKey.setId(question.getAuthorId());
			TaxUser author = mapperFactory.getTaxUserMapper()
					.selectByPrimaryKey(userKey);
			questionLive.setAuthorName(author.getUsername());
			// 加入队列
			questionLiveList.add(questionLive);
		}
		return questionLiveList;
	}

	@Override
	public void verifyCode(HttpServletRequest request,
			HttpServletResponse response) {
		ValidateCode validateCode = new ValidateCode(80, 45, 4, 0);
		String token = ValidateCodeControl.getInstance().addCode(validateCode.getCode());
		try {
			response.addCookie(new Cookie("token", token));
			validateCode.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getQuestion(int questionId) {
		Result result = new Result();
		TaxQuestionKey key = new TaxQuestionKey();
		key.setId(questionId);
		//浏览数+1
		mapperFactory.getTaxQuestionMapper().click(questionId);
		result.setResult(mapperFactory.getTaxQuestionMapper().selectByPrimaryKey(key));
		return JSONObject.toJSONString(result);
	}

	@Override
	public String getAnswer(int questionId, int page) {
		TaxAnswerExample example = new TaxAnswerExample();
		example.createCriteria().andQuestionIdEqualTo(questionId);
		long totalCount = mapperFactory.getTaxAnswerMapper().countByExample(example);
		long totalPage = totalCount%PageConst.NUM_PER_PAGE == 0 ? totalCount/PageConst.NUM_PER_PAGE : totalCount/PageConst.NUM_PER_PAGE + 1; 
		int offset = PageConst.NUM_PER_PAGE*(page - 1);
		example.setLimitClause(offset+","+PageConst.NUM_PER_PAGE);
		//点赞数、收藏数由点赞、收藏接口维护
		List<TaxAnswer> list = mapperFactory.getTaxAnswerMapper().selectByExample(example);
		long currentPageCount = list.size();
		
		PageInfo pageInfo = new PageInfo<TaxAnswer>();
		pageInfo.setCurrentCount(currentPageCount);
		pageInfo.setCurrentPage(page);
		pageInfo.setTotalCount(totalCount);
		pageInfo.setTotalPage(totalPage);
		pageInfo.setList(list);
		
		Result result = new Result();
		result.setResult(pageInfo);
		return JSONObject.toJSONString(result);
	}


}
