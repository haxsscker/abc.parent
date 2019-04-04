/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;

public interface SmsNotifyDao extends BaseDao<SmsNotifyDO, Integer> {
	
	/**
	 * 查询需要发送的短信记录
	 * @param repeatCount 超过多少次就不发送了
	 * @return
	 */
	public List<SmsNotifyDO> selectUnSendedSms(@Param("repeatCount") Integer repeatCount);
}
