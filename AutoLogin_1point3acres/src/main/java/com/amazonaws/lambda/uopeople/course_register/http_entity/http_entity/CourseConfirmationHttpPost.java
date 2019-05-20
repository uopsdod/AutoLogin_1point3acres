package com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CourseConfirmationHttpPost extends HttpPost{
	static private final String loginUrl = "https://mycourses.uopeople.edu/RegisterForCourses/ConfirmRegistration";

	public CourseConfirmationHttpPost(List<String> cookiesStrings) throws UnsupportedEncodingException {
		super(CourseConfirmationHttpPost.loginUrl);
        
		/** set form data **/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CourseId", "872de4da-f918-e211-bfc1-00155d00cc02"));
        params.add(new BasicNameValuePair("CurrentTermId", "f306c752-b77d-e711-811b-e0071b6a82f1"));
        params.add(new BasicNameValuePair("TermId", "21c5c291-b97d-e711-811b-e0071b6a82f1"));
        this.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        /** set headers **/
//        this.setHeader("User-Agent", "PostmanRuntime/7.13.0");
//        this.setHeader("Accept", "*/*");
//        this.setHeader("Cache-Control", "no-cache");
//        this.setHeader("Postman-Token", "5b354ab7-1d7c-4837-a5f9-3806b7cd3912");
//        this.setHeader("Host", "mycourses.uopeople.edu");
////        this.setHeader("cookie", "__cfduid=d2c1d194efb3b86518e2af97fbcf980031558364809; IsAuthenticatedCookies=b8981d7c-4837-e811-8157-e0071b6ac291; ASP.NET_SessionId=hrp2esugs4yp5uzb0yo4hepn");
//        this.setHeader("accept-encoding", "gzip, deflate");
////        this.setHeader("content-length", "173");
//        this.setHeader("Connection", "keep-alive");

//        __cfduid=d2c1d194efb3b86518e2af97fbcf980031558364809; IsAuthenticatedCookies=b8981d7c-4837-e811-8157-e0071b6ac291; ASP.NET_SessionId=hrp2esugs4yp5uzb0yo4hepn
//        String cookieHeaderValue = "";
//        for (String cookieStr : cookiesStrings) {
//            cookieHeaderValue += cookieStr + ";";
//        }
//        System.out.println("MyCookie value: " + cookieHeaderValue);
//        this.setHeader("cookie", cookieHeaderValue);

	}
	
//	// Lazy initialization holder class idiom for static fields
//	static private class SingletonHolder {
//		static public HttpPost loginHttpPost = new LoginHttpPost();
//	}
//	static public HttpPost getInstance() { return SingletonHolder.loginHttpPost; }
	
}
