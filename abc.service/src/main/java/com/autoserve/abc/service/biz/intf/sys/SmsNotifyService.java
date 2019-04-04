package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.dataobject.SmsNotifyDO;


public interface SmsNotifyService {
	
	/**
	 * 将短信插入数据库，等待定时器扫描发送
	 * @param smsNotifyDO
	 */
	void insert(SmsNotifyDO smsNotifyDO);
	

}
