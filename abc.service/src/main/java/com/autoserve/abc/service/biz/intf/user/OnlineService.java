package com.autoserve.abc.service.biz.intf.user;

import java.util.Date;

import com.autoserve.abc.dao.dataobject.OnlineDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.result.PlainResult;

public interface OnlineService {
	
	/**
	 * 获取某用户的某天的在线信息（登录时长和积分信息）
	 * @param user
	 * @param dayDate
	 * @return
	 */
	public PlainResult<OnlineDO> getOnlineInfo(User user, Date dayDate);
	
	/**
	 * 增加在线时长
	 * @param onlineDO
	 * @param addedMin 增加的时长
	 * @return
	 */
	public PlainResult<OnlineDO> addOnlineLength(OnlineDO onlineDO, Integer interval);
	
}
