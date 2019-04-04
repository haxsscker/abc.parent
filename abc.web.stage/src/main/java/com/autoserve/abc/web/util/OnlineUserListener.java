package com.autoserve.abc.web.util;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.autoserve.abc.web.module.screen.login.json.OperLogin;

public class OnlineUserListener implements HttpSessionListener{
	public void sessionCreated(HttpSessionEvent event) {}
	
    public void sessionDestroyed(HttpSessionEvent event) {
    	String sessionId=event.getSession().getId();  
        //当前session销毁时删除当前session绑定的用户信息  
        //同时删除当前session绑定用户的HttpSession  
    	OperLogin.USER_SESSION.remove(OperLogin.SESSIONID_USER.remove(sessionId)); 
    }
}
