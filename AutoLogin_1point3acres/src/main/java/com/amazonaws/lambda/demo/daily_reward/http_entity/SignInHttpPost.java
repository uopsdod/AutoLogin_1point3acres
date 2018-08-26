package com.amazonaws.lambda.demo.daily_reward.http_entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.amazonaws.lambda.demo.util.DailyRewardUtil;

public class SignInHttpPost extends HttpPost{
	static private final String SIGNIN_URL = "http://www.1point3acres.com/bbs/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=1&inajax=1";
	static private final String SIGNIN_PAGE_URL = "http://www.1point3acres.com/bbs/dsu_paulsign-sign.html";
	
	static public final String ERROR_GETFORMHASH = "SignIn getFormHash You've already signed in today";
	
	private SignInHttpPost(String formhash) throws UnsupportedEncodingException {
		super(SignInHttpPost.SIGNIN_URL);
        
		/** set form data **/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("formhash", formhash)); // attention: if formhash is not found, you will get a '未定義操作' error message 
        params.add(new BasicNameValuePair("qdxq", "fd")); // 表情: 奮鬥
        params.add(new BasicNameValuePair("qdmode", "2")); // 快速選擇
        params.add(new BasicNameValuePair("todaysay", ""));
        params.add(new BasicNameValuePair("fastreply", "3")); // 今天我刷題了
        this.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        /** set headers **/
        this.setHeader("Origin", "http://www.1point3acres.com");
        this.setHeader("Referer", "http://www.1point3acres.com/bbs/dsu_paulsign-sign.html");
        this.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        this.setHeader("Host", "www.1point3acres.com");
        
	}
	
	/**
	 * TODO this logic is still somehow weird, give it a second thought later 
	 * @param client
	 * @return
	 */
	static public HttpPost getNewInstance(org.apache.http.client.HttpClient client, String formhash) {
		HttpPost signInHttpPost = null;
		try {
			if (!formhash.isEmpty()) {
				signInHttpPost = new SignInHttpPost(formhash);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signInHttpPost;

	}
	
	static public String getFormHash(org.apache.http.client.HttpClient client) throws Exception {
		DailyRewardUtil.getLogger().log(Level.INFO, "SignIn getFormHash starts");
		String resStrGetSignin = "";
		String formhash = "";
		try {
			HttpGet httpGetSignIn = new HttpGet(SignInHttpPost.SIGNIN_PAGE_URL);
			HttpResponse SignInRespGet = client.execute(httpGetSignIn);
			resStrGetSignin = EntityUtils.toString(SignInRespGet.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
		}			
			
		if (resStrGetSignin.contains("已经签到")
			|| resStrGetSignin.contains("签到时间还未开始")) {
			throw new Exception(SignInHttpPost.ERROR_GETFORMHASH);
		}else {
			Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\"(.*?)\">");
			Matcher matcher = pattern.matcher(resStrGetSignin);

			if (matcher.find()) {
				formhash = matcher.group(1);
				System.out.println(formhash);
			}				
		}
		DailyRewardUtil.getLogger().log(Level.INFO, "SignIn getFormHash ends");
		return formhash;	
	}
	
}

