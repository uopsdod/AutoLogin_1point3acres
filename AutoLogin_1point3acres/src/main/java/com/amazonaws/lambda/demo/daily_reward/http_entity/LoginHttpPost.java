package com.amazonaws.lambda.demo.daily_reward.http_entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class LoginHttpPost extends HttpPost{
	static private final String loginUrl = "http://www.1point3acres.com/bbs/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
	
	public LoginHttpPost(String username, String password) throws UnsupportedEncodingException {
		super(LoginHttpPost.loginUrl);
        
		/** set form data **/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("quickforward", "yes"));
        params.add(new BasicNameValuePair("quickforward", "ls"));
        this.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        /** set headers **/
        this.setHeader("origin", "http://www.1point3acres.com");
        this.setHeader("referer", "http://www.1point3acres.com/bbs/");
        this.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        this.setHeader("host", "www.1point3acres.com");

	}
	
//	// Lazy initialization holder class idiom for static fields
//	static private class SingletonHolder {
//		static public HttpPost loginHttpPost = new LoginHttpPost();
//	}
//	static public HttpPost getInstance() { return SingletonHolder.loginHttpPost; }
	
}
