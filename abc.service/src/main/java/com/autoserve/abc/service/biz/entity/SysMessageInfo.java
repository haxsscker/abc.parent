package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;

public class SysMessageInfo extends SysMessageInfoDO {
	private String sendTime;

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
}
