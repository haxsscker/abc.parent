/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 提现交易状态
 * 
 * @author J.YL 2014年12月1日 下午4:25:30
 */
public enum ToCashState {
	BEFORE(-1, "提现提交"),
    PROCESSING(0, "提现中"),
    SUCCESS(1, "提现成功"),
    FAILURE(2, "提现失败");
    ToCashState(int state, String des) {
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
