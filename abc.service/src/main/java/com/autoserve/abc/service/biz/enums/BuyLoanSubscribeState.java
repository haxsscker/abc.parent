/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 收购标认购的状态
 *
 * @author segen189 2014年12月15日 下午2:41:13
 */
public enum BuyLoanSubscribeState {
    /**
     * 已删除
     */
    DELETED(-1),

    /**
     * 待认购
     */
    WAITING(1),

    /**
     * 认购中
     */
    SUBSCRIBING(2),

    /**
     * 认购成功
     */
    SUBSCRIBE_PASS(3),

    /**
     * 拒绝认购
     */
    DENIED(5),

    /**
     * 暂时忽略
     */
    IGNORED(6);

    BuyLoanSubscribeState(int state) {
        this.state = state;
    }

    public static BuyLoanSubscribeState valueOf(Integer state) {
        for (BuyLoanSubscribeState value : values()) {
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
