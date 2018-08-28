package com.amazonaws.lambda.demo.daily_reward;

import java.io.IOException;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.lambda.demo.daily_reward.http_entity.SignInHttpPost;
import com.amazonaws.lambda.demo.util.DailyRewardUtil;

/**
 * test daily sign in
 */
@RunWith(MockitoJUnitRunner.class)
public class DailySignInTest {

    @Before
    public void setUp() throws IOException {
    	
    }

    @Test
    public void dailySignIn() {
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        DailyRewardService autoLoginService = new DailyRewardService();
    	boolean isLogined = autoLoginService.login(username, password);
    	
    	Assert.assertEquals(isLogined, true);
    	
		String dailySignInFormHash = "";
		try {
			dailySignInFormHash = autoLoginService.getDailySignInFormHash();
			boolean isDailySignInDone = false;
			if (!dailySignInFormHash.isEmpty()) {
				isDailySignInDone = autoLoginService.dailySignIn(dailySignInFormHash);
				Assert.assertEquals(isDailySignInDone, true);
			}
			Assert.assertEquals(isDailySignInDone, false);
		} catch (Exception e) {
			if (dailySignInFormHash.isEmpty()) {
				DailyRewardUtil.getLogger().log(Level.WARNING, e);
    			Assert.assertEquals(e.getMessage(), SignInHttpPost.ERROR_GETFORMHASH);
    		}
		}
		
    }
    
}
