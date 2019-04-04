/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 交易状态
 * 
 * @author J.YL 2014年11月21日 下午4:29:33
 */
public enum DealState {
    NOCALLBACK(0, "等待响应"),
    SUCCESS(1, "交易完成"),
    FAILURE(2, "交易失败");
    DealState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final int    state;
    public final String des;

    public static DealState valueOf(Integer state) {
        for (DealState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
