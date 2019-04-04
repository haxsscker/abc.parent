package com.autoserve.abc.web.module.screen.login.json;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.autoserve.abc.dao.dataobject.SessionRecordDO;
import com.autoserve.abc.dao.intf.SessionRecordDao;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author DS 登录
 */
public class OperLogin {

	@Autowired
	private UserService UserService;

	@Autowired
	private DeployConfigService deployConfigService;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SessionRecordDao sessionRecordDao;
	
	@Autowired
	private HttpSession session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;

	public static final String loginSecurityCodeKey = "loginSecurityCodeKey";

    /** 
	* 用户和Session绑定关系 
	*/  
	public static final Map<String, HttpSession> USER_SESSION=new HashMap<String, HttpSession>();  
	
	/** 
	* seeionId和用户的绑定关系 
	*/  
	public static final Map<String, String> SESSIONID_USER=new HashMap<String, String>(); 
	
	public JsonBaseVO execute(Context context, Navigator nav,
			ParameterParser params, TurbineRunData rundata) {
		JsonBaseVO result = new JsonBaseVO();

		String userName = params.getString("userName");
		String passWord = params.getString("passWord");
		//去除图形验证改为滑动验证
		String securityCode = params.getString("securityCode");

//		String securityfromSession = (String) session.getAttribute("securityCode");
		//区分发送短信的图形验证码
		String securityfromSession = (String) session.getAttribute("loginSecurityCode");
		boolean flag = securityfromSession == null || securityCode == null
				|| !securityfromSession.equalsIgnoreCase(Md5Encrypt.md5(securityCode));
		
//		 flag = false;
		if (flag) {
			if (securityfromSession == null) {
				result.setMessage("验证码已失效，请重新获取");
			} else {
				result.setMessage("验证码错误");
			}
			result.setSuccess(false);
			return result;
		}

		PlainResult<User> findResult = UserService.login(userName, CryptUtils.md5(passWord),
				IPUtil.getUserIpAddr(request),"PC");
		if (findResult.isSuccess()) {
			HttpSession session=request.getSession();  
            //处理用户登录(保持同一时间同一账号只能在一处登录)  
            userLoginHandle(request,findResult.getData());  
            //添加用户与HttpSession的绑定  
            USER_SESSION.put(userName.trim(), session);  
            //添加sessionId和用户的绑定  
            SESSIONID_USER.put(session.getId(), userName);  
            
            /**保存用户当前sessionid start**/
            SessionRecordDO sessionRecordDo = new SessionRecordDO();
            sessionRecordDo.setSessionId(session.getId());
            sessionRecordDo.setUserName(userName);
            sessionRecordDao.insert(sessionRecordDo);
            /**保存用户当前sessionid end**/
            
            session.setAttribute("userName", userName);  
            session.removeAttribute("userMsg");	
			
			session.setAttribute("user", findResult.getData());
			result.setMessage("登录成功");
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMessage(findResult.getMessage());
		}
		return result;
	}
	/** 
	 * 用户登录时的处理 
	 * 处理一个账号同时只有一个地方登录的关键 
	 * @param user 
	 */ 
	private void userLoginHandle(HttpServletRequest request,User user){  
	    //删除当前sessionId绑定的用户，用户--HttpSession  
		String userName=request.getParameter("userName");
		HttpSession nowSession=USER_SESSION.remove(userName);  
	    if(nowSession!=null){  
	        SESSIONID_USER.remove(nowSession.getId());  
	        nowSession.removeAttribute("userName");  
	        nowSession.removeAttribute("user");  
	        nowSession.setAttribute("userMsg", "您的账号已经在另一处登录了,你被迫下线!");  
	    }
	    HttpSession session=USER_SESSION.remove(user.getUserName());  
	    if(session!=null){  
	        SESSIONID_USER.remove(session.getId());  
	        session.removeAttribute("userName");  
	        session.removeAttribute("user");  
	        session.setAttribute("userMsg", "您的账号已经在另一处登录了,你被迫下线!");  
	    }
	    HttpSession session1=USER_SESSION.remove(user.getUserPhone());  
	    if(session1!=null){  
	        SESSIONID_USER.remove(session1.getId());  
	        session1.removeAttribute("userName");  
	        session1.removeAttribute("user");  
	        session1.setAttribute("userMsg", "您的账号已经在另一处登录了,你被迫下线!");  
	    }
	    HttpSession session2=USER_SESSION.remove(user.getUserEmail());  
	    if(session2!=null){  
	        SESSIONID_USER.remove(session2.getId());  
	        session2.removeAttribute("userName");  
	        session2.removeAttribute("user");  
	        session2.setAttribute("userMsg", "您的账号已经在另一处登录了,你被迫下线!");  
	    }
	    /**删除该用户在其他终端的sessionid start**/
        sessionRecordDao.deleteByUserName(user.getUserName());
        sessionRecordDao.deleteByUserName(user.getUserPhone());
        sessionRecordDao.deleteByUserName(user.getUserEmail());
        /**删除该用户在其他终端的sessionid end**/
	} 
	/** 
	 * 用户登录时的处理 
	 * 处理一个账号同时只有一个地方登录的关键 
	 * @param request 
	 */  
	/*public void userLoginHandle(HttpServletRequest request){  
	    //当前登录的用户  
	    String userName=request.getParameter("userName"); 
	    //当前sessionId  
	    String sessionId=request.getSession().getId();  
	    //删除当前sessionId绑定的用户，用户--HttpSession  
	    USER_SESSION.remove(SESSIONID_USER.remove(sessionId));  
	    //删除当前登录用户绑定的HttpSession  
	    HttpSession session=USER_SESSION.remove(userName);  
	    if(session!=null){  
	        SESSIONID_USER.remove(session.getId());  
	        session.removeAttribute("userName");  
	        session.removeAttribute("user");  
	        session.setAttribute("userMsg", "您的账号已经在另一处登录了,你被迫下线!");  
	    }
	    
	    *//**删除该用户在其他终端的sessionid start**//*
        sessionRecordDao.deleteByUserName(userName);
        *//**删除该用户在其他终端的sessionid end**//*
	} */ 
}
