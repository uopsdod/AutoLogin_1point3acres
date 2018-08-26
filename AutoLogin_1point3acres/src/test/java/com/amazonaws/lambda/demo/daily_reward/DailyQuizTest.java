package com.amazonaws.lambda.demo.daily_reward;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.lambda.demo.daily_reward.DailyRewardInput;
import com.amazonaws.lambda.demo.daily_reward.DailyRewardService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

/**
 * test daily quiz
 */
@RunWith(MockitoJUnitRunner.class)
public class DailyQuizTest {

    @Before
    public void setUp() throws IOException {
    	
    }

    @Test
    public void dailyQuiz() throws Exception {
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        DailyRewardService autoLoginService = new DailyRewardService();
    	boolean isLogined = autoLoginService.login(username, password);
    	
    	Assert.assertEquals(isLogined, true);
    	
    	String dailyQuizStatus = "NOT_EXECUTED";
    	String findDailyQuizAns = "";
    	String findDailyQuizFormhash = "";
    	try {
	    	findDailyQuizAns = autoLoginService.findDailyQuizAns();
			if (!findDailyQuizAns.isEmpty()) {
				findDailyQuizFormhash = autoLoginService.findDailyQuizFormhash();
				if (!findDailyQuizFormhash.isEmpty()) {
					dailyQuizStatus = autoLoginService.dailyQuiz(findDailyQuizAns, findDailyQuizFormhash);
					Assert.assertTrue(dailyQuizStatus.equals("SUCCEEDED") 
									|| dailyQuizStatus.equals("FAILED"));
				}
			}
			Assert.assertEquals(dailyQuizStatus, "NOT_EXECUTED");
    	} catch (Exception e) {
    		if (findDailyQuizAns.isEmpty()) {
    			Assert.assertEquals(e.getMessage(), "no question-ans information in database");
    		}else if (findDailyQuizFormhash.isEmpty()) {
    			Assert.assertEquals(e.getMessage(), "You've answered the question today");
    		}
    	}			
    }
    
//    @Test
//    public void findDailyQuizFormhash(){
//        String username = System.getProperty("username");
//        String password = System.getProperty("password");
//        DailyRewardService autoLoginService = new DailyRewardService();
//    	boolean isLogined = autoLoginService.login(username, password);
//    	
//    	Assert.assertEquals(isLogined, true);
//    	
//    	try {
//			String findDailyQuizFormhash = autoLoginService.findDailyQuizFormhash();
//			Assert.assertNotNull(findDailyQuizFormhash);
//    	} catch (Exception e) {
//    		Assert.assertEquals(e.getMessage(), "You've answered the question today");
//    	}
//    }
//    
//    @Test
//    public void findDailyQuizAns(){
//        String username = System.getProperty("username");
//        String password = System.getProperty("password");
//        DailyRewardService autoLoginService = new DailyRewardService();
//    	boolean isLogined = autoLoginService.login(username, password);
//    	
//    	Assert.assertEquals(isLogined, true);
//    	
//    	try {
//			String findDailyQuizAns = autoLoginService.findDailyQuizAns();
//			Assert.assertNotNull(findDailyQuizAns);
//    	} catch (Exception e) {
//    		Assert.assertEquals(e.getMessage(), "no question-ans information in database");
//    	}
//    }   
    
}
