/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 资金划转类型
 * 
 * @author segen189 2014年11月23日 下午3:57:51
 */
public enum FullTransferType {
    /**
     * 普通标满标资金划转
     */
    COMMON_LOAN_FULL_TRANSFER(1),

    /**
     * 转让标满标资金划转
     */
    TRANSF_LOAN_FULL_TRANSFER(3),

    /**
     * 收购标满标资金划转
     */
    BUY_LOAN_FULL_TRANSFER(5);

    FullTransferType(int type) {
        this.type = type;
    }

    public static FullTransferType valueOf(Integer type) {
        for (FullTransferType value : values()) {
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
