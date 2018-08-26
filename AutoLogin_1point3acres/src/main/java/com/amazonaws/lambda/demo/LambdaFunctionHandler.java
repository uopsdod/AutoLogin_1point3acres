package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;

import com.amazonaws.lambda.demo.daily_reward.DailyRewardInput;
import com.amazonaws.lambda.demo.daily_reward.DailyRewardService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;



public class LambdaFunctionHandler implements RequestHandler<DailyRewardInput, String> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

    public LambdaFunctionHandler() {}

//    // Test purpose only.
//    LambdaFunctionHandler(AmazonS3 s3) {
//        this.s3 = s3;
//    }

    @Override
    public String handleRequest(DailyRewardInput input, Context context) {
    	context.getLogger().log("handleRequest starts");
    	context.getLogger().log("Received input: " + input);
        context.getLogger().log("Received context: " + context);
        
        String res = null;
        
        res = "123test";
        
        /** Get Daily Reward **/
		execute(input.getUsername(), input.getPassword()); // correct
        
        context.getLogger().log("handleRequest ends");
        return res; 
    }
    
	public void execute(String username, String password) {
		
    	/** login **/
		String dailySignInFormHash = "";
		String findDailyQuizAns = "";
		String findDailyQuizFormhash = "";
		
		DailyRewardService autoLoginService = new DailyRewardService();
		boolean isLogined = autoLoginService.login(username, password);
		
		try {
	    	if (isLogined) {
	    		/** Daily Sign in **/
	    		dailySignInFormHash = autoLoginService.getDailySignInFormHash();
	    		if (!dailySignInFormHash.isEmpty()) {
	    			boolean isDailySignInDone = autoLoginService.dailySignIn(dailySignInFormHash);
	    		}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		try {
			if (isLogined) {
				/** Daily Quiz **/
				findDailyQuizAns = autoLoginService.findDailyQuizAns();
				if (!findDailyQuizAns.isEmpty()) {
					findDailyQuizFormhash = autoLoginService.findDailyQuizFormhash();
					
					if (!findDailyQuizFormhash.isEmpty()) {
						boolean isDailyQuizDone = autoLoginService.dailyQuiz(findDailyQuizAns, findDailyQuizFormhash);
						Assert.assertEquals(isDailyQuizDone, true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		/** get login page **/
//		HttpGet httpGet = new HttpGet("http://www.1point3acres.com/bbs/");
//		HttpResponse loginRespGet = client.execute(httpGet);
//		String resStrGet = EntityUtils.toString(loginRespGet.getEntity());
			
		System.out.println("test");
    
	}
    
}