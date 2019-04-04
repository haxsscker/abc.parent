/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 充值交易状态
 * 
 * @author J.YL 2014年12月1日 下午5:12:41
 */
public enum RechargeState {
    PROCESSING(0, "充值中"),
    SUCCESS(1, "充值成功"),
    FAILURE(2, "充值失败");
    RechargeState(int state, String des) {
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
}
