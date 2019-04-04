/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 类FundPayType.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/04
 */
public enum FundState {
	IS_RELEASE(1, "已发布"),
	IS_NOT_RELEASE(0, "未发布");
    
    FundState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    private final int    state;
    private final String des;
    
    public static FundState valueOf(Integer state) {
        for (FundState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
