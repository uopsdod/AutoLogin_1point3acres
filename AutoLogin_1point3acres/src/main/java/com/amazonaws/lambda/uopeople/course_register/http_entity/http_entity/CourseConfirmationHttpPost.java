package com.amazonaws.lambda.uopeople.course_register.http_entity.http_entity;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CourseConfirmationHttpPost extends HttpPost{
//	static private final String loginUrl = "https://mycourses.uopeople.edu/RegisterForCourses/ConfirmRegistration";

    static private final String loginUrl = "https://mycourses.uopeople.edu/RegisterForCourses/ConfirmRegistration";

    public  CourseConfirmationHttpPost(){

    }


	public CourseConfirmationHttpPost(List<String> cookiesStrings) throws UnsupportedEncodingException {
		super(CourseConfirmationHttpPost.loginUrl);
        
		/** set form data **/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CourseId", "872de4da-f918-e211-bfc1-00155d00cc02"));
        params.add(new BasicNameValuePair("CurrentTermId", "f306c752-b77d-e711-811b-e0071b6a82f1"));
        params.add(new BasicNameValuePair("TermId", "21c5c291-b97d-e711-811b-e0071b6a82f1"));
        this.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        /** set headers **/
//        this.setHeader("accept", "application/json, text/javascript, */*; q=0.01");
//        this.setHeader("accept-encoding", "gzip, deflate, br");
//        this.setHeader("accept-language", "zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7");
//        this.setHeader("content-type", "application/json; charset=UTF-8");
        String cookieValue = "";
        for (String cookieString:cookiesStrings){
            if (cookieString.contains("__cfduid")) continue;
            cookieValue += (cookieString + "; ");
        }
        System.out.println("cookieValue: " + cookieValue);

//        this.setHeader("cookie",cookieValue);

        // TODO: still cannot set the cookie dynamically
        // 1. try some other ways to set cookie
        // 2. finish the logout part ? 
        this.setHeader("cookie",
//                "__cfduid=df9f2d8679e54976c61f0a8423354980d1558275766; " +
                        "ASP.NET_SessionId=hfwdj4hsz0zvwecs5mj4tovm; " +
                        "IsAuthenticatedCookies=b8981d7c-4837-e811-8157-e0071b6ac291; "
        );

//        this.setHeader("cookie",
//                "__cfduid=df9f2d8679e54976c61f0a8423354980d1558275766; " +
////                "_gcl_au=1.1.1130373694.1558275768; " +
////                "_ga=GA1.2.1934493237.1558275768; " +
////                "_gid=GA1.2.1126965419.1558275768; " +
////                "_mkto_trk=id:972-VUZ-580&token:_mch-uopeople.edu-1558275768535-50396; " +
////                "__ft_referrer=https://www.google.com/; " +
////                "trwv.uid=uopeople-1558275769119-c6cdd4a2%3A1; " +
////                "LPVID=E0M2IwNTNjMjNkNDE4NDNh; " +
//                "ASP.NET_SessionId=hfwdj4hsz0zvwecs5mj4tovm; " +
//                "IsAuthenticatedCookies=b8981d7c-4837-e811-8157-e0071b6ac291; "
////                "__lt_referrer=direct; " +
////                "LPSID-41223025=CSVSHU6MTDKOzOSTDSQ4rw"
//                );
//        this.setHeader("origin", "https://mycourses.uopeople.edu\n");
////        this.setHeader("cookie", "__cfduid=d2c1d194efb3b86518e2af97fbcf980031558364809; IsAuthenticatedCookies=b8981d7c-4837-e811-8157-e0071b6ac291; ASP.NET_SessionId=hrp2esugs4yp5uzb0yo4hepn");
//        this.setHeader("referer", "https://mycourses.uopeople.edu/RegisterForCourses/LoadRegisterForCourses");
////        this.setHeader("content-length", "173");
//        this.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
//        this.setHeader("x-requested-with", "XMLHttpRequest"); // !!!!

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
