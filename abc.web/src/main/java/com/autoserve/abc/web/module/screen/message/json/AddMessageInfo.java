package com.autoserve.abc.web.module.screen.message.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.Jpush.JpushUtils;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AddMessageInfo {
	private static final Logger logger = LoggerFactory
			.getLogger(AddMessageInfo.class);
	@Autowired
	private SysMessageInfoService messageInfoService;
	@Resource
    private UserService userService;
	
	public JsonBaseVO execute(@Params SysMessageInfoDO messageDO,@Param("sysToUser") String toUserList) {
		JsonBaseVO vo = new JsonBaseVO();
		if("0".equals(toUserList)){//发送给所有人
			LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
			messageDO.setSysUserId(emp.getEmpId());
			messageDO.setSysUserName(emp.getEmpName());
			messageDO.setSysToUserName("所有人");
			this.messageInfoService.createMessageForRibao(messageDO);
			/**消息推送给app**/
			JpushUtils.sendPush(messageDO.getSysMessageContent());
			return vo;
		}
		String[] toUserIdList = toUserList.split(",");
		for(String userId:toUserIdList){
			LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
			messageDO.setSysUserId(emp.getEmpId());
			messageDO.setSysUserName(emp.getEmpName());
			messageDO.setSysToUser(Integer.parseInt(userId));
			this.messageInfoService.createMessage(messageDO);
			PlainResult<UserDO> result = userService.findById(Integer.valueOf(userId));
			if(result.isSuccess()){
				/**消息推送给app**/
				try{
					JpushUtils.sendPush_alias(result.getData().getUserName(),messageDO.getSysMessageContent());
		    	}catch(Exception e){
		    		logger.error(e.getMessage());
		    	}
			}
		}
		return vo;
	}
}
