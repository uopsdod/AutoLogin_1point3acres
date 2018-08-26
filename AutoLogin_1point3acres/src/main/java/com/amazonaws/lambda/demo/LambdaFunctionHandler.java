package com.amazonaws.lambda.demo;

import java.util.logging.Level;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.amazonaws.lambda.demo.daily_reward.DailyRewardInput;
import com.amazonaws.lambda.demo.daily_reward.DailyRewardService;
import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizAnsContainer;
import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizHttpPost;
import com.amazonaws.lambda.demo.util.BaseLogger;
import com.amazonaws.lambda.demo.util.DailyRewardUtil;
import com.amazonaws.lambda.demo.util.MyLambdaLogger;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * TODO it's time to work on tempalte stuff
 * @author sam
 *
 */
public class LambdaFunctionHandler implements RequestHandler<DailyRewardInput, String> {

    public LambdaFunctionHandler() {}

    @Override
    public String handleRequest(DailyRewardInput input, Context context) {
    	DailyRewardUtil.setLogger(new MyLambdaLogger(context.getLogger()));
    	DailyRewardUtil.getLogger().log(Level.INFO, "Lambda function starts");
    	DailyRewardUtil.getLogger().log(Level.INFO, "input DailyRewardInput: " + ReflectionToStringBuilder.toString(input));
    	DailyRewardUtil.getLogger().log(Level.INFO, "input Context: " + ReflectionToStringBuilder.toString(context));
        
        /** Get Daily Reward **/
		execute(input.getUsername(), input.getPassword()); // correct
        
		
		DailyRewardUtil.getLogger().log(Level.INFO, "Lambda function ends");
		
        // Return will include the log stream name so you can look 
        // up the log later.
        return String.format("Execution finished. Log stream = %s", context.getLogStreamName());
    }
    
	public void execute(String username, String password) {
		
		boolean isLogined = false;
		boolean isDailySignInDone = false;
		DailyQuizHttpPost.RESULT dailyQuizStatus = DailyQuizHttpPost.RESULT.NOT_EXECUTED;
		
    	/** login **/
		String dailySignInFormHash = "";
		String findDailyQuizAns = "";
		String findDailyQuizFormhash = "";
		
		DailyRewardService autoLoginService = new DailyRewardService();
		isLogined = autoLoginService.login(username, password);
		
		try {
	    	if (isLogined) {
	    		/** Daily Sign in **/
	    		dailySignInFormHash = autoLoginService.getDailySignInFormHash();
	    		if (!dailySignInFormHash.isEmpty()) {
	    			isDailySignInDone = autoLoginService.dailySignIn(dailySignInFormHash);
	    		}
			}
		} catch (Exception e) {
			DailyRewardUtil.getLogger().log(Level.WARNING, e);
		}			
		
		try {
			if (isLogined) {
				/** Daily Quiz **/
				DailyQuizAnsContainer dailyQuizAnsContainer = new DailyQuizAnsContainer();
				findDailyQuizAns = autoLoginService.findDailyQuizAns(dailyQuizAnsContainer);
				if (!findDailyQuizAns.isEmpty()) {
					findDailyQuizFormhash = autoLoginService.findDailyQuizFormhash();
					
					if (!findDailyQuizFormhash.isEmpty()) {
						dailyQuizStatus = autoLoginService.dailyQuiz(findDailyQuizAns, findDailyQuizFormhash);
					}
				}
			}
		} catch (Exception e) {
			DailyRewardUtil.getLogger().log(Level.WARNING, e);
		}			
		
		String result = String.format("Final result: isLoginDone: %b, isDailySignInDone: %b, DailyQuizStatus: %s%n", isLogined, isDailySignInDone, dailyQuizStatus.name());
		DailyRewardUtil.getLogger().log(Level.INFO, result);
		
		/** get login page **/
//		HttpGet httpGet = new HttpGet("http://www.1point3acres.com/bbs/");
//		HttpResponse loginRespGet = client.execute(httpGet);
//		String resStrGet = EntityUtils.toString(loginRespGet.getEntity());
			
	}
    
}