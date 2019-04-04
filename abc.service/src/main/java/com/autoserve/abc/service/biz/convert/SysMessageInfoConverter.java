package com.autoserve.abc.service.biz.convert;

import net.sf.cglib.beans.BeanCopier;

import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.service.biz.entity.SysMessageInfo;
import com.autoserve.abc.service.util.DateUtil;

public class SysMessageInfoConverter {
	public static SysMessageInfo convert(SysMessageInfoDO msgInfoDO) {
		SysMessageInfo messageInfo = new SysMessageInfo();
		BeanCopier beanCopier = BeanCopier.create(SysMessageInfoDO.class,
				SysMessageInfo.class, false);
		beanCopier.copy(msgInfoDO, messageInfo, null);
		messageInfo.setSendTime(DateUtil.formatDate(msgInfoDO.getSysMessageDate()));
		return messageInfo;
	}
}
