package com.amazonaws.lambda.uopeople.course_register.http_entity;

import com.amazonaws.lambda.demo.util.DailyRewardUtil;
import com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity.CourseConfirmationHttpPost;
import com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity.GetHPUpdatesHttpGet;
import com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity.LoginHttpPost;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CourseRegistrationService {

	private org.apache.http.client.HttpClient client;
	private Header[] cookies;
	private List<String> cookiesStrings = new ArrayList<>();

	public CourseRegistrationService() {
//		BasicCookieStore cookieStore = new BasicCookieStore();
//		BasicClientCookie cookie = null;
//		cookie = new BasicClientCookie("__cfduid", "decd8edcacf4e3dfc39fa90546dba86e91558372273");
//		cookie.setDomain("uopeople.edu"); // .uopeople.edu
//		cookie.setPath("/");
		this.client = HttpClientBuilder.create()
				.setRedirectStrategy(new LaxRedirectStrategy())
//				.setRedirectStrategy(new DefaultRedirectStrategy() {
//			/** Redirectable methods. */
//			private String[] REDIRECT_METHODS = new String[] {
//					HttpGet.METHOD_NAME, HttpPost.METHOD_NAME, HttpHead.METHOD_NAME
//			};
//
//			@Override
//			protected boolean isRedirectable(String method) {
//				for (String m : REDIRECT_METHODS) {
//					if (m.equalsIgnoreCase(method)) {
//						return true;
//					}
//				}
//				return false;
//			}
//		})
				.build();

	}
	
	public boolean login(String username, String password) {
		DailyRewardUtil.getLogger().log(Level.INFO, "login starts");
		boolean isLoginDone = false;
		try {
			HttpResponse loginResp = this.client.execute(new LoginHttpPost(username, password));
			cookies = loginResp.getHeaders("Set-Cookie");
			for (int i = 0; i < cookies.length; i++) {
				DailyRewardUtil.getLogger().log(Level.INFO, "cookies header: " + cookies[i]);
				String cookieStr = cookies[i].getValue().substring(0, cookies[i].getValue().indexOf(';'));
				DailyRewardUtil.getLogger().log(Level.INFO, "cookies header - value: " + cookieStr);
				if (!cookiesStrings.contains(cookieStr))
					cookiesStrings.add(cookieStr);
			}
			Header[] allHeaders = loginResp.getAllHeaders();
//			for (int i = 0; i < allHeaders.length; i++) {
//				DailyRewardUtil.getLogger().log(Level.INFO, "header: " + allHeaders[i]);
//			}

			String loginRespContent = EntityUtils.toString(loginResp.getEntity());
			DailyRewardUtil.getLogger().log(Level.INFO, "response: " + loginRespContent);

			if (loginRespContent.contains("/Services/UserApps")) {
				isLoginDone = true;
			}

		} catch (IOException e) {
			DailyRewardUtil.getLogger().log(Level.INFO, e);
		}
		
		DailyRewardUtil.getLogger().log(Level.INFO, "login ends");
		return isLoginDone;
	}

	public void getHPUpdate(){
		try{
			HttpResponse courseConfimResp = this.client.execute(new GetHPUpdatesHttpGet());
			DailyRewardUtil.getLogger().log(Level.INFO, "response: " + courseConfimResp);
			String courseConfrimContent = EntityUtils.toString(courseConfimResp.getEntity());
			DailyRewardUtil.getLogger().log(Level.INFO, "response: " + courseConfrimContent);

		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public boolean courseConfirm() {
		DailyRewardUtil.getLogger().log(Level.INFO, "courseConfirm starts");
		boolean isLoginDone = false;



		try {

			BasicCookieStore cookieStore = new BasicCookieStore();
			// add cookie info
//			for (String cookieStr: cookiesStrings) {
//				String[] parts = cookieStr.split("=");
//				String key = parts[0];
//				String val = parts[1];
//				DailyRewardUtil.getLogger().log(Level.INFO, "key-val: " + key + " ---- " + val);
//				// add cookie one by one
//				BasicClientCookie cookie = new BasicClientCookie(key, val);
//				cookie.setDomain("uopeople.edu"); // .uopeople.edu
//				cookie.setPath("/");
//				cookieStore.addCookie(cookie);
//			}
//			BasicClientCookie cookie = null;
//			cookie = new BasicClientCookie("ASP.NET_SessionId", "y0gcdpeocj1sxwlf2spkev40");
//			cookie.setDomain("your.uopeople.edu\n"); // .uopeople.edu
//			cookie.setPath("/");
//			cookie = new BasicClientCookie("__cfduid", "df32df183169bad47ded402619cb902f81558379068");
//			cookie.setDomain("uopeople.edu"); // .uopeople.edu
//			cookie.setPath("/");
//			cookieStore.addCookie(cookie);
//			cookie = new BasicClientCookie("IsAuthenticatedCookies", "b8981d7c-4837-e811-8157-e0071b6ac291");
//			cookie.setDomain("uopeople.edu"); // .uopeople.edu
//			cookie.setPath("/");
//			cookieStore.addCookie(cookie);
//			cookie = new BasicClientCookie("ASP.NET_SessionId", "gerzahyfxnut2sxuhcsvlqce");
//			cookie.setDomain("mycourses.uopeople.edu"); // .uopeople.edu
//			cookie.setPath("/");
//			cookieStore.addCookie(cookie);

			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			// localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore); // before 4.3
//			response = instance.execute(request, localContext);

			HttpResponse courseConfimResp = this.client.execute(new CourseConfirmationHttpPost(cookiesStrings), localContext);

			DailyRewardUtil.getLogger().log(Level.INFO, "response: " + courseConfimResp);
			Header[] locations = courseConfimResp.getHeaders("Location");
//			String location = EntityUtils.toString();
//			DailyRewardUtil.getLogger().log(Level.INFO, "response - location: " + locations[0].getValue().toString());
			String courseConfrimContent = EntityUtils.toString(courseConfimResp.getEntity());
			DailyRewardUtil.getLogger().log(Level.INFO, "response - entity: " + courseConfrimContent);

//			String redirectUrl = "https://mycourses.uopeople.edu" + locations[0].getValue().toString();
//			DailyRewardUtil.getLogger().log(Level.INFO, "redirectUrl: " + redirectUrl);
//			HttpResponse redirectResult = this.client.execute(new HttpGet(redirectUrl));
//			DailyRewardUtil.getLogger().log(Level.INFO, "redirectResult: " + redirectResult);
//			String redirectResultContent = EntityUtils.toString(redirectResult.getEntity());
//			DailyRewardUtil.getLogger().log(Level.INFO, "redirectResult - entity: " + redirectResultContent);

		} catch (IOException e) {
			DailyRewardUtil.getLogger().log(Level.INFO, e);
		}

		DailyRewardUtil.getLogger().log(Level.INFO, "courseConfirm ends");
		return isLoginDone;
	}

}
