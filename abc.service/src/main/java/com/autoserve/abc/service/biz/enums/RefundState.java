/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 退款交易状态
 * 
 * @author J.YL 2014年12月2日 下午2:01:55
 */
public enum RefundState {
    PROCESSING(0, "退款中"),
    SUCCESS(1, "退款成功"),
    FAILURE(2, "退款失败");
    RefundState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public static RefundState valueOf(Integer state) {
        for (RefundState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
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
