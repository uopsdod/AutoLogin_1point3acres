package com.amazonaws.lambda.demo.daily_reward;

import java.io.IOException;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.lambda.demo.daily_reward.http_entity.DailyQuizHttpPost;
import com.amazonaws.lambda.demo.util.DailyRewardUtil;

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
    	
    	DailyQuizHttpPost.RESULT dailyQuizStatus = DailyQuizHttpPost.RESULT.NOT_EXECUTED;
    	String findDailyQuizAns = "";
    	String findDailyQuizFormhash = "";
    	try {
	    	findDailyQuizAns = autoLoginService.findDailyQuizAns();
			if (!findDailyQuizAns.isEmpty()) {
				findDailyQuizFormhash = autoLoginService.findDailyQuizFormhash();
				if (!findDailyQuizFormhash.isEmpty()) {
					dailyQuizStatus = autoLoginService.dailyQuiz(findDailyQuizAns, findDailyQuizFormhash);
					Assert.assertTrue(dailyQuizStatus.equals(DailyQuizHttpPost.RESULT.SUCCEEDED) 
									|| dailyQuizStatus.equals(DailyQuizHttpPost.RESULT.FAILED));
				}
			}
			Assert.assertEquals(dailyQuizStatus, DailyQuizHttpPost.RESULT.NOT_EXECUTED);
    	} catch (Exception e) {
    		DailyRewardUtil.getLogger().log(Level.WARNING, e);
    		if (findDailyQuizAns.isEmpty()) {
    			Assert.assertEquals(e.getMessage(), DailyQuizHttpPost.DAILY_QUIZ_ERROR_ANS);
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
