package com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginHttpPost extends HttpPost{
	static private final String loginUrl = "https://your.uopeople.edu/Account/LoginToPortal";
	
	public LoginHttpPost(String email, String password) throws UnsupportedEncodingException {
		super(LoginHttpPost.loginUrl);
        
		/** set form data **/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        this.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        /** set headers **/
        this.setHeader("origin", "https://your.uopeople.edu");
        this.setHeader("referer", "https://your.uopeople.edu/");
        this.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
//        this.setHeader("host", "www.1point3acres.com");

	}

	
}
