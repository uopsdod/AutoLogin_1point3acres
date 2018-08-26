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
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.amazonaws.lambda.demo.util.DailyRewardUtil;

public class DailyQuizHttpPost extends HttpPost{
	static private final String DAILY_QUIZ_URL = "http://www.1point3acres.com/bbs/plugin.php?id=ahome_dayquestion:pop";
	static private final String DAILY_QUIZ_PAGE_URL = "http://www.1point3acres.com/bbs/plugin.php?id=ahome_dayquestion:pop";
	static private String PAYLOAD_FORMAT = 
			"------WebKitFormBoundarybDMuZYRqRgOKYQWW\r\n" + 
			"Content-Disposition: form-data; name=\"formhash\"\r\n" + 
			"\r\n" + 
			"formhash_to_replace\r\n" + 
			"------WebKitFormBoundarybDMuZYRqRgOKYQWW\r\n" + 
			"Content-Disposition: form-data; name=\"answer\"\r\n" + 
			"\r\n" + 
			"answer_to_replace\r\n" + 
			"------WebKitFormBoundarybDMuZYRqRgOKYQWW\r\n" + 
			"Content-Disposition: form-data; name=\"submit\"\r\n" + 
			"\r\n" + 
			"true\r\n" + 
			"------WebKitFormBoundarybDMuZYRqRgOKYQWW--";
	
	private DailyQuizHttpPost(String answer, String formhash) throws UnsupportedEncodingException {
		super(DailyQuizHttpPost.DAILY_QUIZ_URL);
        
		/** set form data **/
		PAYLOAD_FORMAT = PAYLOAD_FORMAT.replace("answer_to_replace", answer);
        PAYLOAD_FORMAT = PAYLOAD_FORMAT.replace("formhash_to_replace", formhash);
//        StringEntity se = new StringEntity(payLoadLogin);
//        this.setEntity(new StringEntity(PAYLOAD_FORMAT, "UTF-8"));
        this.setEntity(new StringEntity(PAYLOAD_FORMAT));
        
        /** set headers **/
        this.setHeader("Origin", "http://www.1point3acres.com");
        this.setHeader("Referer", "http://www.1point3acres.com/bbs/");
        this.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        this.setHeader("Host", "www.1point3acres.com");
        this.setHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarybDMuZYRqRgOKYQWW"); // important header
	}
	
	static public HttpPost getNewInstance(org.apache.http.client.HttpClient client, String ans, String formhash) {
		HttpPost signInHttpPost = null;
		try {
			signInHttpPost = new DailyQuizHttpPost(ans, formhash); // ex. "3"
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signInHttpPost;

	}
	
	static public String getFormHash(org.apache.http.client.HttpClient client) throws Exception {
		DailyRewardUtil.getLogger().log(Level.INFO, "DailyQuiz getFormHash starts");
		String formhash = "";
		String resStrGetSignin = getDailyQuizPage(client);
		
		if (resStrGetSignin.contains("已经参加过")
			|| resStrGetSignin.contains("您今天已经参加过答题")) {
			throw new Exception("DailyQuiz getFormHash You've answered the question today");
		}else {
			Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\"(.*?)\">");
			Matcher matcher = pattern.matcher(resStrGetSignin);

			if (matcher.find()) {
				formhash = matcher.group(1);
				System.out.println(formhash);
			}				
		}
		DailyRewardUtil.getLogger().log(Level.INFO, "DailyQuiz getFormHash ends");
		return formhash;	
	}
	
	static public String getAns(org.apache.http.client.HttpClient client) throws Exception {
		DailyRewardUtil.getLogger().log(Level.INFO, "getAns starts");
		String ans = "";
		String resStrGetSignin = getDailyQuizPage(client);
		
		// TODO: add logic here 
		boolean found = false;
		if (found) {
			
		}else {
			throw new Exception("no question-ans information in database");
		}
		DailyRewardUtil.getLogger().log(Level.INFO, "getAns ends");
		return ans;
	}
	
	static private String getDailyQuizPage(org.apache.http.client.HttpClient client) {
		String resStrGetSignin = null;
		try {
			HttpGet httpGetSignIn = new HttpGet(DailyQuizHttpPost.DAILY_QUIZ_PAGE_URL);
			HttpResponse SignInRespGet = client.execute(httpGetSignIn);
			resStrGetSignin = EntityUtils.toString(SignInRespGet.getEntity());		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resStrGetSignin;
	}
}

