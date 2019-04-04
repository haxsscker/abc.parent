package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.util.Date;

public class AuthorizeUtil {
	/**
	 * 授权是否到期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String isAuthorize(Date startDate, Date endDate){
    	Date now=new Date();
    	if(FormatHelper.compareToDate(now, startDate)<0){//未到期
			return "未生效";
		}else if(FormatHelper.compareToDate(now, endDate)>0){//已过期
			return "已过期";
		}else{//有效
			return "有效";
		}
    }
}
