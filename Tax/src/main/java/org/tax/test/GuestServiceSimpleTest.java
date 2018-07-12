package org.tax.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tax.VO.LoginInfo;
import org.tax.model.TaxUser;
import org.tax.service.TaxGuestService;
import org.tax.service.impl.TaxGuestServiceImpl;
import org.tax.util.LuceneUtil;
import org.tax.util.UUIDUtil;

public class GuestServiceSimpleTest {
	
	TaxGuestService guestService;
	
	@Before
	public void init(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-db.xml","classpath:spring/applicationContext-service.xml");
		guestService = (TaxGuestService) context.getBean("taxGuestService");
	}
	
	
	
	@Test
	public void testRegister(){
		/**
		 * 不能为空：用户名 邮箱 电话 密码 最后登陆
		 * 不能重复：用户名
		 * */
		//构造一个合法的User
		TaxUser u1 = new TaxUser();
		u1.setId(UUIDUtil.genUUID());
		//u1.setUsername("dubi");
		//u1.setUsername("dubi_another");
		u1.setUsername("dubi_another_2");
		u1.setEmail("349612430@qq.com");
		u1.setTelephone("18022267373");
		u1.setPassword("");//测试不合法用户密码
		u1.setLastVisit(new Date());
		u1.setImage("default img");
		String resultJsonStr = guestService.register(u1);
		System.out.println(resultJsonStr);
	}
	
	@Test
	public void testLogin(){
		/**这里仅仅先测试关于数据库的逻辑 关于session cookie那些先注释了*/
		//LoginInfo loginInfo = new LoginInfo();
		//loginInfo.setUsername("dubi");
		//loginInfo.setUsername("wrong_username");
		//loginInfo.setUsername(null);
		//loginInfo.setPassword("password");
		//loginInfo.setPassword("wrong_password");
		//loginInfo.setPassword("");
		//loginInfo.setPassword(null);
		//String resultJsonStr = guestService.login(loginInfo, null, null);
		String resultJsonStr = guestService.login(null, null, null);
		System.out.println(resultJsonStr);
	}
	
	@Test
	public void testInitIndexLib(){
		LuceneUtil.initIndexLib();
	}
	
	@Test
	public void testSearch(){
		//使用要设置一下LuceneUtil的那个index路径
		/**无效类*/
		//String resultJsonStr = guestService.search(null, "3;100", 1);
		//String resultJsonStr = guestService.search("江泽民", null, 1);
		//String resultJsonStr = guestService.search("江泽民", "4;100", 1);
		//String resultJsonStr = guestService.search("江泽民", "3;100", 100);
		String resultJsonStr = guestService.search("江泽民", "3;100", 0);
		/**有效类*/
		//String resultJsonStr = guestService.search("江泽民", "3;100", 1);
		
		System.out.println(resultJsonStr);
	}
	
	@Test
	public void testGetByCondition(){
		/**无效类*/
		//String resultJsonStr = guestService.getByCondition(null, 2);
		//String resultJsonStr = guestService.getByCondition("latest", 0);
		//String resultJsonStr = guestService.getByCondition("latest", 3);
		//String resultJsonStr = guestService.getByCondition("wrong_type", 1);
		/**有效类*/
		//String resultJsonStr = guestService.getByCondition("latest", 2);
		//String resultJsonStr = guestService.getByCondition("latest", 1);
		//String resultJsonStr = guestService.getByCondition("hot", 1);
		//String resultJsonStr = guestService.getByCondition("hot", 2);
		//String resultJsonStr = guestService.getByCondition("latest", 1);
		//String resultJsonStr = guestService.getByCondition("reward", 2);
		
		//System.out.println(resultJsonStr);
	}
	
	@Test
	public void testGetQuestions(){
		/**无效类*/
		//String resultJsonStr = guestService.getQuestions(0);
		//String resultJsonStr = guestService.getQuestions(3);
		/**有效类*/
		//String resultJsonStr = guestService.getQuestions(1);
		//String resultJsonStr = guestService.getQuestions(2);
		//System.out.println(resultJsonStr);
	}
	
	@Test
	public void testGetShares(){
		/**无效类*/
		//String resultJsonStr = guestService.getShares(0);
		//String resultJsonStr = guestService.getShares(3);
		/**有效类*/
		//String resultJsonStr = guestService.getShares(1);
		//String resultJsonStr = guestService.getShares(2);
		//System.out.println(resultJsonStr);
	}
	
	@Test
	public void testGetArticlesOfExperts(){
		/**无效类*/
		//String resultJsonStr = guestService.getArticlesOfExperts(0);
		//String resultJsonStr = guestService.getShares(3);
		/**有效类*/
		//String resultJsonStr = guestService.getShares(1);
		//String resultJsonStr = guestService.getShares(2);
		//System.out.println(resultJsonStr);
	}
}
