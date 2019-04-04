/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 活动类型：对用户行为进行记录
 *
 * @author segen189 2014年11月21日 下午6:29:08
 */
public enum WxState {
    /**
     * 待审核
     */
	TO_AUDIT("0","待审核"),

    /**
     * 绑定
     */
	AUDIT("1","绑定");

   

    WxState(String type,String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    private final String type;
    
    private final String name;
}
