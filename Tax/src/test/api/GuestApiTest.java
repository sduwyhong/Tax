package api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * @author wyhong
 * @date 2018-7-12
 */
public class GuestApiTest {

	public void register(){
		Map<String, String> userData = new HashMap<String, String>();
		userData.put("username", "wyhong");
		userData.put("email", "285944978@qq.com");
		userData.put("telephone", "17864154784");
		userData.put("password", DigestUtils.md5DigestAsHex("123".getBytes()));
		try {
			Response response = Jsoup.connect("http://localhost:8888/Tax/api/v1/guest/register")
				.data(userData)
				.method(Method.POST)
				.ignoreContentType(true)
				.execute();
			//System.out.println(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login(){
		Map<String, String> loginInfo = new HashMap<String, String>();
		loginInfo.put("username", "wyhong");
		loginInfo.put("password", DigestUtils.md5DigestAsHex("123".getBytes()));
		try {
			Response response = Jsoup.connect("http://localhost:8888/Tax/api/v1/guest/login")
				.data(loginInfo)
				.method(Method.POST)
				.ignoreContentType(true)
				.execute();
			//System.out.println(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getQuestions(){
		try {
			Response response = Jsoup.connect("http://localhost:8888/Tax/api/v1/guest/question/1")
				.method(Method.GET)
				.ignoreContentType(true)
				.execute();
			System.out.println(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
