package api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.util.DigestUtils;
import org.tax.constant.CookieConst;

/**
 * @author wyhong
 * @date 2018-7-12
 */
public class UserApiTest {

	@Test
	public void publishQuestion(){
		Map<String, String> loginInfo = new HashMap<String, String>();
		loginInfo.put("username", "wyhong");
		loginInfo.put("password", DigestUtils.md5DigestAsHex("123".getBytes()));
		Response loginResponse = null;
		try {
			loginResponse = Jsoup.connect("http://localhost:8888/Tax/api/v1/guest/login")
				.data(loginInfo)
				.method(Method.POST)
				.ignoreContentType(true)
				.execute();
			Thread.sleep(1000);
			//System.out.println(response.body());
		} catch (IOException e) {
		} catch (InterruptedException e) {
		}
		Map<String, String> questionData = new HashMap<String, String>();
		questionData.put("type", "P0001;P0002");
		questionData.put("title", "ddddddddd");
		questionData.put("content", "test4");
		try {
			Response response = Jsoup.connect("http://localhost:8888/Tax/api/v1/user/question")
				.data(questionData)
				.method(Method.POST)
				.ignoreContentType(true)
				.cookie(CookieConst.USER, loginResponse.cookie(CookieConst.USER))
				.execute();
			//System.out.println(response.body());
		} catch (IOException e) {
		}
	}
}
