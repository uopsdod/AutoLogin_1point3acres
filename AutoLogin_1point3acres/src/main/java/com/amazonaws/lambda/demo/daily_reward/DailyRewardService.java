package com.amazonaws.lambda.demo.daily_reward;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizHttpPost;
import com.amazonaws.lambda.demo.daily_reward.http_entity.LoginHttpPost;
import com.amazonaws.lambda.demo.daily_reward.http_entity.SignInHttpPost;
import com.amazonaws.lambda.demo.util.DailyRewardUtil;

public class DailyRewardService {
	
	private org.apache.http.client.HttpClient client;
	
	public DailyRewardService() {
		this.client = HttpClientBuilder.create().build();
	}
	
	public boolean login(String username, String password) {
		DailyRewardUtil.getLogger().log(Level.INFO, "login starts");
		boolean isLoginDone = false;
		try {
			HttpResponse loginResp = this.client.execute(new LoginHttpPost(username, password));
			String loginRespContent = EntityUtils.toString(loginResp.getEntity());
			
			if (loginRespContent.contains("登录失败")
					|| loginRespContent.contains("errorhandle_")
					|| loginRespContent.contains("密码错误次数过多")) {
				DailyRewardUtil.getLogger().log(Level.INFO, "login failed");
			}else {
				DailyRewardUtil.getLogger().log(Level.INFO, "login succeeded");
				isLoginDone = true;
			}
		} catch (IOException e) {
			DailyRewardUtil.getLogger().log(Level.INFO, e);
		}
		
		DailyRewardUtil.getLogger().log(Level.INFO, "login ends");
		return isLoginDone;
	}
	
	public boolean dailySignIn(String formhash) {
		DailyRewardUtil.getLogger().log(Level.INFO, "dailySignIn starts");
		boolean isDailySignInDone = false;
		try {
			HttpPost signInHttpPost = SignInHttpPost.getNewInstance(this.client, formhash);
			if (null != signInHttpPost) {
				HttpResponse signInResp;
					signInResp = client.execute(signInHttpPost);
				String signInRespContent = EntityUtils.toString(signInResp.getEntity());
				// TODO: add success condition description
				boolean success = true;
				if (success) {
					isDailySignInDone = true;
					DailyRewardUtil.getLogger().log(Level.INFO, "dailySignIn succeeded");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DailyRewardUtil.getLogger().log(Level.INFO, "dailySignIn ends");
		return isDailySignInDone;
	}
	
	public String getDailySignInFormHash() throws Exception {
		return SignInHttpPost.getFormHash(this.client);
	}
	
	public String dailyQuiz(String ans, String formhash) {
		DailyRewardUtil.getLogger().log(Level.INFO, "dailyQuiz starts");
		String dailyQuizStatus = "NOT_EXECUTED";
		try {
			// TODO: add condition to check whether quiz questions are in the database. If not, skip this part.
			
			HttpPost dailyQuizHttpPost = DailyQuizHttpPost.getNewInstance(this.client, ans, formhash);
			if (null != dailyQuizHttpPost) {
				HttpResponse signInResp = client.execute(dailyQuizHttpPost);
				String dailyQuizRespContent = EntityUtils.toString(signInResp.getEntity());	
				// TODO: add success condition description
				boolean success = true;
				if (success) {
					dailyQuizStatus = "SUCCEEDED";
					DailyRewardUtil.getLogger().log(Level.INFO, "dailyQuiz succeeded");
				}else {
					dailyQuizStatus = "FAILED";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DailyRewardUtil.getLogger().log(Level.INFO, "dailyQuiz ends");
		return dailyQuizStatus;
	}
	
	public String findDailyQuizAns() throws Exception {
		return DailyQuizHttpPost.getAns(this.client);
	}
	
	public String findDailyQuizFormhash() throws Exception {
		return DailyQuizHttpPost.getFormHash(this.client);
	}
	
	
	
}
