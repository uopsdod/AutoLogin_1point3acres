package com.amazonaws.lambda.demo.daily_reward;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizHttpPost;
import com.amazonaws.lambda.demo.daily_reward.http_entity.LoginHttpPost;
import com.amazonaws.lambda.demo.daily_reward.http_entity.SignInHttpPost;

public class DailyRewardService {
	
	private org.apache.http.client.HttpClient client;
	
	public DailyRewardService() {
		this.client = HttpClientBuilder.create().build();
	}
	
	public boolean login(String username, String password) {
		boolean isLoginDone = false;
		try {
			HttpResponse loginResp = this.client.execute(new LoginHttpPost(username, password));
			String loginRespContent = EntityUtils.toString(loginResp.getEntity());
			
			if (loginRespContent.contains("登录失败")
					|| loginRespContent.contains("errorhandle_")) {
				System.out.println("login failed");
			}else {
				System.out.println("login succeeded");
				isLoginDone = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isLoginDone;
	}
	
	public boolean dailySignIn() {
		boolean isDailySignInDone = false;
		try {
			HttpPost signInHttpPost = SignInHttpPost.getNewInstance(this.client);
			if (null != signInHttpPost) {
				HttpResponse signInResp;
					signInResp = client.execute(signInHttpPost);
				String signInRespContent = EntityUtils.toString(signInResp.getEntity());
				// TODO: add success condition description
				boolean success = true;
				if (success) {
					isDailySignInDone = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isDailySignInDone;
	}
	
	public boolean dailyQuiz() {
		boolean isDailyQuizDone = false;
		try {
			// TODO: add condition to check whether quiz questions are in the database. If not, skip this part.
			
			HttpPost dailyQuizHttpPost = DailyQuizHttpPost.getNewInstance(this.client);
			if (null != dailyQuizHttpPost) {
				HttpResponse signInResp = client.execute(dailyQuizHttpPost);
				String dailyQuizRespContent = EntityUtils.toString(signInResp.getEntity());	
				// TODO: add success condition description
				boolean success = true;
				if (success) {
					isDailyQuizDone = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isDailyQuizDone;
	}
	
	
	
}
