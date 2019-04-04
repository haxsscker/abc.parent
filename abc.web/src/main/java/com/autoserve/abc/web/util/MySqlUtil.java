package com.autoserve.abc.web.util;

public class MySqlUtil {
	/**
	 * 转义mysql字符串
	 * @param str
	 * @return
	 */
	public static String transferString(String str){
		if(str!=null){
			str = str.replaceAll("_", "\\\\_");//将_转义成\_，防止like查询_匹配一个字符
		}
		return str;
	}
	public static void main(String[] args) {
		System.out.println(MySqlUtil.transferString(null));
	}
}
