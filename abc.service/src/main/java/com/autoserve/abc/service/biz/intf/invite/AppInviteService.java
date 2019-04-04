package com.autoserve.abc.service.biz.intf.invite;

import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.result.PlainResult;
/**
 * app邀请业务接口
 * @author zhanghuimin    20161117
 *
 */
public interface AppInviteService {
	
	/**
     * app端邀请好友接口
     * @param userId
     * @param userType
     * @return
     */
    public PlainResult<String> generateAppInviteUrl(Integer userId, InviteUserType userType);

}
