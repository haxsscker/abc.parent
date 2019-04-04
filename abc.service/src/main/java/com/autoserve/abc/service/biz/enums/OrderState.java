/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 订单状态<br>
 * 订单在其生命周期中的各个状态
 *
 * @author segen189 2014年11月20日 下午11:14:54
 */
public enum OrderState {
    /**
     * 已删除
     */
    DELETED(-1),

    /**
     * 未支付
     */
    UNPAID(0),

    /**
     * 支付失败
     */
    FAIL_PAID(1),

    /**
     * 支付成功
     */
    PAID(2);

    OrderState(int state) {
        this.state = state;
    }

    public static OrderState valueOf(Integer state) {
        for (OrderState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    private final int state;
}
