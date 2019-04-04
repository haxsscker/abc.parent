/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 活动类型：对用户行为进行记录
 *
 * @author segen189 2014年11月21日 下午6:29:08
 */
public enum ActivityType {
    /**
     * 投资普通标
     */
    INVEST_COMMON_LOAN(1),

    /**
     * 投资转让标
     */
    INVEST_TRANSFER_LOAN(2),

    /**
     * 投资收购标
     */
    INVEST_BUY_LOAN(3),

    /**
     * 发起转让标
     */
    TRANSFER(4),

    /**
     * 发起收购标
     */
    BUY(4),

    /**
     * 兑换红包
     */
    RED_ENVELOPE(6);

    ActivityType(int type) {
        this.type = type;
    }

    public static ActivityType valueOf(Integer type) {
        for (ActivityType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    private final int type;
}
