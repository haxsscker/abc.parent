package com.autoserve.abc.service.util;


import java.util.UUID;

import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.SeqnoHelper;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-29,15:02
 */
public class UuidUtil {
	private static String partnerId = ConfigHelper.getConfig("merchantId");
	
    public static String generateUuid() {
    	//随机产生一个[0,10)的数字
        return partnerId+SeqnoHelper.getId(16);
    }
}
