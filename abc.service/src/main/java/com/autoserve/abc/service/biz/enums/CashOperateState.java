/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 资金操作状态 未收到回复0 成功为200 失败为500
 * 
 * @author J.YL 2014年11月20日 下午3:32:05
 */
public enum CashOperateState {
    NOCALLBACK(0, "未收到回复"),
    SUCCESS(200, "操作成功"),
    FAILURE(500, "操作失败");
    CashOperateState(int state, String des) {
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

    public static CashOperateState valueOf(Integer state) {
        for (CashOperateState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
