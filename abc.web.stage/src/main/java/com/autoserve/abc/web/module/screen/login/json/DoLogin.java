package com.autoserve.abc.web.module.screen.login.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.util.StringUtil;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author DS
 * 登录
 */
public class DoLogin {

    @Autowired
    private UserService         UserService;

    @Autowired
    private DeployConfigService deployConfigService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private HttpSession         session;
    
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private HttpServletResponse response;

    public static final String  loginSecurityCodeKey = "loginSecurityCodeKey";

    public void execute(Context context, Navigator nav, ParameterParser params,
                              TurbineRunData rundata) {
        JsonBaseVO result = new JsonBaseVO();
        String userName = params.getString("userName");
        String passWord = params.getString("passWord");
        String securityCode = params.getString("securityCode");

        if (StringUtil.isBlank(userName) || StringUtil.isBlank(passWord)) {
            result.setMessage("登录名或密码错误");
            result.setSuccess(false);
            context.put("result", result);
            context.put("userName", userName);
            context.put("passWord", passWord);
            nav.forwardTo("/login/login").end();
            
        }else{
//        	boolean isResponseCorrect = imageCaptchaService.validateResponseForID(request.getSession().getId(), securityCode);
        	String securityfromSession=(String)session.getAttribute("securityCode");
                        if (securityfromSession==null || securityCode==null
                        		|| !securityfromSession.equalsIgnoreCase(Md5Encrypt.md5(securityCode))) { 
                        	if(securityfromSession==null){
                        		result.setMessage("验证码已失效，请重新获取");
                        	}else{
                        		result.setMessage("验证码错误");
                        	}                           
                            result.setSuccess(false);
                            context.put("result", result);
                            context.put("userName", userName);
                            context.put("passWord", passWord);
                            nav.forwardTo("/login/login").end();
                        }else {
                        	//用户名登录
                        	PlainResult<User> planResult=login(result, userName, passWord, nav, params,context);
			            if (!(planResult.isSuccess())) {
			            	//手机登录
			            	UserDO userDO=new UserDO();
			            	userDO.setUserPhone(userName);
			            	 PageResult<UserDO> pageResult=UserService.queryListMobile(userDO, null, null, new PageCondition());
			            	 if(pageResult.getData().size()!=0){
			            		String userName1= pageResult.getData().get(0).getUserName();
			            		 if (!(login(result, userName1, passWord, nav, params,context).isSuccess())) {
						            	//邮箱登录
						            	UserDO userDO2=new UserDO();
						            	userDO2.setUserEmail(userName);
						            	PageResult<UserDO> pageResult2= UserService.queryListEmail(userDO2, null, null, new PageCondition());
						            	if(pageResult2.getData().size()!=0){
						            		String userName2= pageResult2.getData().get(0).getUserName();
						            		PlainResult<User> planResult2=login(result, userName2, passWord, nav, params,context);
						            		if(!(planResult2.isSuccess())){
						            			 result.setMessage(planResult2.getMessage());
									             result.setSuccess(false);
									             context.put("result", result);
									             context.put("userName", userName);
						                         context.put("passWord", passWord);
									             nav.forwardTo("/login/login").end();
						            		}					            	
						            	}else{
							            	result.setMessage(planResult.getMessage());
								            result.setSuccess(false);
								            context.put("result", result);
								            context.put("userName", userName);
					                        context.put("passWord", passWord);
								            nav.forwardTo("/login/login").end();
							            }
						            }
			            	 }else{
			            		//邮箱登录
					            	UserDO userDO3=new UserDO();
					            	userDO3.setUserEmail(userName);
					            	PageResult<UserDO> pageResult3= UserService.queryListEmail(userDO3, null, null, new PageCondition());
					            	if(pageResult3.getData().size()!=0){
					            		String userName3= pageResult3.getData().get(0).getUserName();
					            		PlainResult<User> planResult3=login(result, userName3, passWord, nav, params,context);
					            		if(!planResult3.isSuccess()){
					            			 result.setMessage(planResult3.getMessage());
								             result.setSuccess(false);
								             context.put("result", result);
								             context.put("userName", userName);
					                         context.put("passWord", passWord);
								             nav.forwardTo("/login/login").end();
					            		}					            	
					            	}else{
					            		result.setMessage(planResult.getMessage());
							            result.setSuccess(false);
							            context.put("result", result);
							            context.put("userName", userName);
				                        context.put("passWord", passWord);
							            nav.forwardTo("/login/login").end();
					            	}
			            	 }
			            }			            
                  }
        }
    }
    
    /**
     * 登录
     * @param session
     * @param result
     * @param userName
     * @param passWord
     * @param nav
     * @param params
     */
    public PlainResult<User> login(JsonBaseVO result,String userName,String passWord, Navigator nav, ParameterParser params,Context context){
    	//首页
    	 String homeUrl = deployConfigService.getHomeUrl(request);
    	 PlainResult<User> findResult = UserService.login(userName, CryptUtils.md5(passWord),
                 IPUtil.getUserIpAddr(request),"PC");
         if (findResult.isSuccess()) {
        	 // 更新cookie的登录信息
//             LoginCookieTokenManager.addLoginUserCookies(findResult.getData().getUserId(), 3600, request, response);
             // 更新session的登录信息
             session.setAttribute("user", findResult.getData());
//             session.setMaxInactiveInterval(0);
             result.setMessage("登录成功");
             result.setSuccess(true);          
             // 重定向
             String redirectUrl = params.getString("redirectUrl");
             if (redirectUrl == null
                     || redirectUrl.indexOf("redirectUrl",
                             redirectUrl.indexOf("redirectUrl") + "redirectUrl".length()) > 0) {
                 nav.redirectToLocation(homeUrl);
             } else {
                 nav.redirectToLocation(redirectUrl);
             }
         }
    	return findResult;
    }
}
