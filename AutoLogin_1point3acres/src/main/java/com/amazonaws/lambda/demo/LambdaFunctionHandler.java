package com.amazonaws.lambda.demo;

import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.amazonaws.lambda.demo.daily_reward.DailyRewardService;
import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizHttpPost;
import com.amazonaws.lambda.demo.util.DailyRewardUtil;
import com.amazonaws.lambda.demo.util.MyLambdaLogger;
import com.amazonaws.lambda.demo.util.ParameterUtil;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * TODO it's time to work on tempalte stuff
 * @author sam
 *
 */
public class LambdaFunctionHandler implements RequestHandler<Object, String> {

    public LambdaFunctionHandler() {}

    @Override
    public String handleRequest(Object input, Context context) {
    	DailyRewardUtil.setLogger(new MyLambdaLogger(context.getLogger()));
    	DailyRewardUtil.getLogger().log(Level.INFO, "Lambda function starts");
    	DailyRewardUtil.getLogger().log(Level.INFO, "input DailyRewardInput: " + ReflectionToStringBuilder.toString(input));
    	DailyRewardUtil.getLogger().log(Level.INFO, "input Context: " + ReflectionToStringBuilder.toString(context));
        
        /** Get Daily Reward **/
    	GetParametersByPathResult getResult = ParameterUtil.getParametersByPath(ParameterUtil.PathBuilder.root(ParameterUtil.Table.USER.getName()).build());
    	List<Parameter> parameters = getResult.getParameters();
    	for (Parameter param : parameters) {
    		String user = param.getName();
    		String value = param.getValue();
    		JsonParser parser = new JsonParser();
    		JsonObject credential = parser.parse(value).getAsJsonObject();
    		String username = credential.get("username").getAsString();
    		String password = credential.get("password").getAsString();
    		
    		DailyRewardUtil.getLogger().log(Level.INFO, "username: " + username);
    		DailyRewardUtil.getLogger().log(Level.INFO, "password: " + password);
    		
    		execute(username, password);
    	}
		
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
				findDailyQuizAns = autoLoginService.findDailyQuizAns();
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