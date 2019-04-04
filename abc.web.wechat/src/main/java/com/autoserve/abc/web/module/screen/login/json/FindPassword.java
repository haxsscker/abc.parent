package com.autoserve.abc.web.module.screen.login.json;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 忘记密码
 */
public class FindPassword {

    @Resource
    private UserService      userService;
    @Resource
    private HttpSession      session;
    @Resource
    private InviteService    inviteService;
    @Autowired
    private SysConfigService sysConfigService;
    

    public JsonBaseVO execute(@Params User user, Context context, ParameterParser params, Navigator nav) {
    	PageResult<UserDO> pageresult = null;
    	JsonBaseVO result = new JsonBaseVO();
    	UserDO  userdo = new UserDO();
        String securityCode = (String) session.getAttribute("securityCode");
        System.out.println(securityCode);
        String userPhone = params.getString("userPhone");
        String userPwd = params.getString("userPwd");
        System.out.println(userPhone+":"+userPwd);
        String Verification = params.getString("Verification");
        if (securityCode == null || "".equals(securityCode)) {
    		result.setMessage("验证码已失效 请重新获取");
            result.setSuccess(false);
            nav.forwardTo("wangjimima").end();
            return result;
        } else if (!securityCode.equals(Verification)) {
    		result.setMessage("验证码错误");
            result.setSuccess(false);
            return result;
        }
    		userdo.setUserPhone(userPhone);
    		pageresult =  userService.queryListMobile(userdo, null, null, new PageCondition());
    	
	    	List<UserDO> userDoList = pageresult.getData();
	    	if(userDoList.size()>0 && userDoList!=null){
	    		userdo = userDoList.get(0);
		    	String password = CryptUtils.md5(userPwd);
		    	userdo.setUserPwd(password);
		    	userService.modifyUserSelective(userdo);
	    		
	    	}else{
	    		result.setMessage("该用户不存在 请重新输入");
                result.setSuccess(false);
                return result;
	    	}
	    	return result;
    
    }	   
        
        

    

}
