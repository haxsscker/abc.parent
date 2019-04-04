package com.autoserve.abc.service.biz.impl.sys;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.dao.intf.SmsNotifyDao;
import com.autoserve.abc.service.biz.intf.sys.SmsNotifyService;

@Service
public class SmsNotifyServiceImpl implements SmsNotifyService{
	@Autowired
	private SmsNotifyDao smsNotifyDao;

	@Override
	public void insert(SmsNotifyDO smsNotifyDO) {
		smsNotifyDO.setCreateTime(new Date());
		smsNotifyDO.setSendStatus(0);
		smsNotifyDO.setSendCount(0);
		smsNotifyDao.insert(smsNotifyDO);
	}
}
