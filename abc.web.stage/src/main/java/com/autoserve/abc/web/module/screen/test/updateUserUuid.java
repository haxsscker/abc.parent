package com.autoserve.abc.web.module.screen.test;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;

public class updateUserUuid {
	@Resource
	private UserService userService;
	public BaseResult execute(Context context, ParameterParser params) {
		BaseResult result=new BaseResult();
		ListResult<UserDO> listResult=userService.queryList(new UserDO());
		for(UserDO userDO:listResult.getData()){
			if(userDO.getUserUuid()==null || "".equals(userDO.getUserUuid())){
				userDO.setUserUuid(InnerSeqNo.getInstance().getUniqueNo());
				result=userService.modifyUserSelective(userDO);
				if(!result.isSuccess()){
					break;
				}
			}			
		}
		return result;
	}
}
