/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 标类型
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum BidType {
    /**
     * 普通标
     */
    COMMON_LOAN(0),

    /**
     * 转让标
     */
    TRANSFER_LOAN(1),

    /**
     * 收购标
     */
    BUY_LOAN(2);

    BidType(int type) {
        this.type = type;
    }

    public static BidType valueOf(Integer type) {
        for (BidType value : values()) {
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
