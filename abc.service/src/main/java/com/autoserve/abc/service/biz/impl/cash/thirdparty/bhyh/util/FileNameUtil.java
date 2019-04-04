package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class FileNameUtil {
	/**
	 * 生成文件名
	 * @param key
	 * @param suffix
	 * @return
	 */
	public static String getFileName(String key,String suffix,String merBillNo){
		StringBuffer fileName =new StringBuffer();
        fileName.append(ConfigHelper.getConfig("merchantId")).append("_").append(FormatHelper.formatDate(new Date(), "yyyyMMdd"))
        .append("_").append(key).append("_");
        if(StringUtils.isNotEmpty(merBillNo)){
        	fileName.append(merBillNo);
        }else{
        	fileName.append(SeqnoHelper.getId(10));
        }
        fileName.append(".").append(suffix);
        return fileName.toString();
	}
	/**
	 * 文件名fileNameStr加后缀suffix
	 * @param fileNameStr
	 * @param suffix
	 * @return
	 */
	public static String getFileNameBySuffix(String fileNameStr,String suffix){
		StringBuffer fileName =new StringBuffer();
        fileName.append(fileNameStr).append(".").append(suffix);
        return fileName.toString();
	}
}
