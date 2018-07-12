package org.tax.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tax.VO.PasswordModification;
import org.tax.factory.MapperFactory;
import org.tax.model.TaxUser;
import org.tax.model.TaxUserKey;
import org.tax.service.TaxUserService;

public class UserServiceSimpleTest {
	
	TaxUserService userService;
	MapperFactory mapperFactory;
	
	@Before
	public void init(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-db.xml","classpath:spring/applicationContext-service.xml");
		userService = (TaxUserService) context.getBean("taxUserService");
		mapperFactory = (MapperFactory) context.getBean("mapperFactory");
	}
	
	@Test
	public void testUpdateInfo(){
		TaxUserKey dubiUserKey = new TaxUserKey();
		dubiUserKey.setId("a9da429220a64a12a34264cd971acdf7");
		TaxUser dubi = mapperFactory.getTaxUserMapper().selectByPrimaryKey(dubiUserKey);	
		dubi.setImage("another img");
		dubi.setProList("9;99;999");
		String resultJsonStr = userService.updateInfo(dubi);
		System.out.println(resultJsonStr);
	}
	
	@Test
	public void testConfirmSolution(){
		//String resultJsonStr = userService.confirmSolution(4);//有效
		String resultJsonStr = userService.confirmSolution(100);//无效qid
		System.out.println(resultJsonStr);
	}
	
	@Test
	public void testCollect(){
		//String resultJsonStr = userService.collect(4);//有效
		String resultJsonStr = userService.collect(100);//无效qid
		System.out.println(resultJsonStr);
	}
	
	/**其他要从session取出user的暂时先没有写测试 懒得手动注入 等下查查其他测试工具*/
	
	/**这里先直接在TaxUserServiceImple中设置user来测试*/
	@Test
	public void testModifiedPassword(){
		String resultJsonStr = userService.modifyPassword(null, null);
		System.out.println(resultJsonStr);
		//直接人手注入taxUserServiceImpl中测试发现正确存入
	}
	
	
}
