package com.autoserve.abc.service.util;

import java.util.UUID;


/**
 * 时间处理工具类
 * 
 * @author pp 2014-11-28 上午11:31:45
 */
public class IDHelper {
	public static String getId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }
	public static void main(String[] args) {
		System.out.println(getId());
	}
}
