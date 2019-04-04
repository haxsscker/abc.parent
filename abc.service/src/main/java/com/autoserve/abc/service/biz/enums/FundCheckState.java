/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 类FundCheckState.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/08
 */
public enum FundCheckState {
	CONFIRMED(1, "已确认"),
	IS_GIVE_UP(2, "已放弃");
    
    FundCheckState(int checkState, String des) {
        this.checkState = checkState;
        this.des = des;
    }

    public int getCheckState() {
        return checkState;
    }

    public String getDes() {
        return des;
    }

    private final int    checkState;
    private final String des;
    
    public static FundCheckState valueOf(Integer checkState) {
        for (FundCheckState value : values()) {
            if (checkState != null && value.checkState == checkState) {
                return value;
            }
        }
        return null;
    }
}
