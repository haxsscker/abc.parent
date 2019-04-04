package com.autoserve.abc.web.module.screen.register.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class SendInviterPhone {
    @Resource
    private UserService      userService;
    
	public JsonBaseVO execute(Context context, ParameterParser params) {
    	JsonBaseVO result = new JsonBaseVO();
		String inviterPhone = params.getString("inviterPhone");

            UserDO userDOCode = new UserDO();
            userDOCode.setUserPhone(inviterPhone);
            PageResult<UserDO> pageResult = userService.queryList(userDOCode, new PageCondition());
            if (pageResult.getTotalCount() > 0) {
            	result.setSuccess(true);
            	result.setMessage("成功");
            }else{
            	result.setSuccess(false);
                result.setMessage("该邀请码手机号不是本系统用户");
            }
		
            return result;
	}
}
