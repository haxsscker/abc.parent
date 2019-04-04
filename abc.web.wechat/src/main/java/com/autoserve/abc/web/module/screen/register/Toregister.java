package com.autoserve.abc.web.module.screen.register;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;


/**
 * 注册
 */
public class Toregister {
	@Resource
	private UserService userService;
	@Resource
	private HttpSession session;
	
   public void execute(Context context, ParameterParser params) {
//	   Integer uuid=params.getInt("InvitationId");
//		   	if(uuid!=null&&!"".equals(uuid)&&uuid!=0){
//				PlainResult<UserDO> mangeruser = userService.findById(uuid);
//					if(mangeruser.getData()!=null){
//						context.put("mangeruser", mangeruser.getData());
//						context.put("uuid", uuid);
//					}
//			}
	   String InvitationId=params.getString("InvitationId");
	   if(InvitationId!=null && !"".equals(InvitationId)){
		   context.put("InvitationId", InvitationId);
	   }
		   	
    }

}
