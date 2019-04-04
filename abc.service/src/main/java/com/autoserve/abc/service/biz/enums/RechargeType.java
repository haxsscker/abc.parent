/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 充值
 * 
 * @author J.YL 2014年11月26日 下午8:43:02
 */
public enum RechargeType {
    NOCALLBACK(0, "未收到回复"),
    SUCCESS(1, "操作成功"),
    FAILURE(2, "操作失败");
    RechargeType(int state, String des) {
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

    public static RechargeType valueOf(Integer state) {
        for (RechargeType value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
