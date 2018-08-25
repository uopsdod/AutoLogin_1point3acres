package com.amazonaws.lambda.demo.daily_reward.http_entity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
	
	private DailyQuizHttpPost(String formhash, String answer) throws UnsupportedEncodingException {
		super(DailyQuizHttpPost.DAILY_QUIZ_URL);
        
		/** set form data **/
        PAYLOAD_FORMAT = PAYLOAD_FORMAT.replace("formhash_to_replace", formhash);
        PAYLOAD_FORMAT = PAYLOAD_FORMAT.replace("answer_to_replace", answer);
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
	
	static public HttpPost getNewInstance(org.apache.http.client.HttpClient client) {
		HttpPost signInHttpPost = null;
		try {
			String formhash = getFormHash(client);
			if (!formhash.isEmpty()) {
				signInHttpPost = new DailyQuizHttpPost(formhash, "3");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signInHttpPost;

	}
	
	static private String getFormHash(org.apache.http.client.HttpClient client) {
		String formhash = "";
		try {
			HttpGet httpGetSignIn = new HttpGet(DailyQuizHttpPost.DAILY_QUIZ_PAGE_URL);
			HttpResponse SignInRespGet = client.execute(httpGetSignIn);
			String resStrGetSignin = EntityUtils.toString(SignInRespGet.getEntity());
			
			if (resStrGetSignin.contains("已经参加过")
				|| resStrGetSignin.contains("您今天已经参加过答题")) {
				System.out.println("您今天已经参加过答题");
			}else {
				Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"formhash\" value=\"(.*?)\">");
				Matcher matcher = pattern.matcher(resStrGetSignin);

				if (matcher.find()) {
					formhash = matcher.group(1);
					System.out.println(formhash);
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return formhash;	
	}
	
}

