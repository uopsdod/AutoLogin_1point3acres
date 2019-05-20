package com.amazonaws.lambda.uopeople;

import java.util.List;
import java.util.logging.Level;

import com.amazonaws.lambda.uopeople.course_register.http_entity.CourseRegistrationService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.amazonaws.lambda.demo.daily_reward.DailyRewardService;
import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizAnsContainer;
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
public class UoPeopleLambdaHandler implements RequestHandler<Object, String> {

    public UoPeopleLambdaHandler() {}

    @Override
    public String handleRequest(Object input, Context context) {
    	DailyRewardUtil.setLogger(new MyLambdaLogger(context.getLogger()));
    	DailyRewardUtil.getLogger().log(Level.INFO, "UoPeopleLambdaHandler starts");
    	DailyRewardUtil.getLogger().log(Level.INFO, "input: " + ReflectionToStringBuilder.toString(input));
    	DailyRewardUtil.getLogger().log(Level.INFO, "input: " + ReflectionToStringBuilder.toString(context));
        
        /** Course Registration **/
//    	GetParametersByPathResult getResult = ParameterUtil.getParametersByPath(ParameterUtil.PathBuilder.root(ParameterUtil.Table.USER.getName()).build());
//    	List<Parameter> parameters = getResult.getParameters();
//    	for (Parameter param : parameters) {
//    		String user = param.getName();
//    		String value = param.getValue();
//    		JsonParser parser = new JsonParser();
//    		JsonObject credential = parser.parse(value).getAsJsonObject();
//    		String username = credential.get("username").getAsString();
//    		String password = credential.get("password").getAsString();

			String username = "uopsdod@gmail.com";
			String password = "J06102wsx";

    		DailyRewardUtil.getLogger().log(Level.INFO, "username: " + username);
    		DailyRewardUtil.getLogger().log(Level.INFO, "password: " + "********");
    		
    		execute(username, password);
//    	}
		
		DailyRewardUtil.getLogger().log(Level.INFO, "Lambda function ends");
		
        // Return will include the log stream name so you can look 
        // up the log later.
        return String.format("Execution finished. Log stream = %s", context.getLogStreamName());
    }

    public static void main(String[] args){
		UoPeopleLambdaHandler uoPeopleLambdaHandler = new UoPeopleLambdaHandler();
		uoPeopleLambdaHandler.execute("uopsdod@gmail.com", "J06102wsx");
	}
    
	public void execute(String email, String password) {
		
		boolean isLogined = false;
		boolean isDailySignInDone = false;
		DailyQuizHttpPost.RESULT dailyQuizStatus = DailyQuizHttpPost.RESULT.NOT_EXECUTED;
		
    	/** login **/

		CourseRegistrationService service = new CourseRegistrationService();

		isLogined = service.login(email, password);
		
		try {
	    	if (isLogined) {
				service.courseConfirm();
			}
		} catch (Exception e) {
			DailyRewardUtil.getLogger().log(Level.WARNING, e);
		}
		
		String result = String.format("Final result: isLoginDone: %b, isDailySignInDone: %b, DailyQuizStatus: %s%n", isLogined, isDailySignInDone, dailyQuizStatus.name());
		DailyRewardUtil.getLogger().log(Level.INFO, result);

			
	}
    
}