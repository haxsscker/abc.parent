package com.autoserve.abc.web.module.screen.login;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.CryptUtils;

public class Getpassword4
{
	 	@Autowired
	    private HttpSession session;
	 	@Resource
	 	private UserService userService;
	    public void execute(Context context, ParameterParser params) {
	    	UserDO  userdo = new UserDO();
	    	PageResult<UserDO> result = null;
	    	String emailOrPhone = params.getString("emailOrPhone");
	    	String type = params.getString("type");
	    	String flag=params.getString("flag");
	    	if("1".equals(type))
	    	{
		    	 userdo.setUserEmail(emailOrPhone);
		    	 result=  userService.queryListEmail(userdo, null, null, new PageCondition());
	    	}
	    	if("2".equals(type))
	    	{
	    		 userdo.setUserPhone(emailOrPhone);
	    		 result=  userService.queryListMobile(userdo, null, null, new PageCondition());
	    	}
		    	List<UserDO> userDoList = result.getData();
		    	if(userDoList!=null && userDoList.size()>0)
		    	userdo = userDoList.get(0);
		    	
		    	String password = CryptUtils.md5(params.getString("password"));
		    	if(flag!=null && "1".equals(flag)){
		    		userdo.setUserPwd(password);
		    	}else if(flag!=null && "2".equals(flag)){
		    		userdo.setUserDealPwd(password);
		    	}
		    	
		    	userService.modifyUserSelective(userdo);
		    	//重新存入session中
		    	session.setAttribute("user", UserConverter.toUser(userdo));
		    	context.put("flag",flag);
	    	}	   
}

