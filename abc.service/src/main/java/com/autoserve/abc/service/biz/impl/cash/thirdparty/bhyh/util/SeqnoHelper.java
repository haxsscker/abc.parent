package com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util;

import java.util.UUID;

public class SeqnoHelper {
	public static String getId(int num) {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
	    if (hashCodeV < 0) {//有可能是负数
	        hashCodeV = -hashCodeV;
	    }
	    // 0 代表前面补充0
	    // num 代表长度为num
	    // d 代表参数为正数型
	    String orderId=String.format("%0"+num+"d", hashCodeV);
	    return orderId;
//		Random random = new Random();
//		return StringUtils.leftPad(Long.toString(Long.parseLong(System.currentTimeMillis() + "" + random.nextInt(1000)),36), num,"0");
	}
	
	/**
     * 获得8个长度的十六进制的UUID
     * @return UUID
     */
    public static String get8UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0];
    }
    
	public static void main(String[] args) {
		System.out.println(getId(10));
	}
}
