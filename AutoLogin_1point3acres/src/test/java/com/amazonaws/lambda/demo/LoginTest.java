package com.amazonaws.lambda.demo;

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
 * test successful login 
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Before
    public void setUp() throws IOException {
    	
    }

    @Test
    public void loginSuccess() {
        String username = System.getProperty("username");
        String password = System.getProperty("password");
        DailyRewardService autoLoginService = new DailyRewardService();
    	boolean isLogined = autoLoginService.login(username, password);
    	
    	Assert.assertEquals(isLogined, true);
    }
    
    @Test
    public void loginFailure() {
        String username = System.getProperty("username");
        String password = "WrongPassword";
        DailyRewardService autoLoginService = new DailyRewardService();
    	boolean isLogined = autoLoginService.login(username, password);
    	
    	Assert.assertEquals(isLogined, false);
    }
    
}
