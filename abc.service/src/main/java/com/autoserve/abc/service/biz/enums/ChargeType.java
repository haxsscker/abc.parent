package com.autoserve.abc.service.biz.enums;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-25,16:43
 */
public enum ChargeType {
    /**
     * 按“每笔”方式收费
     */
    BY_DEAL(1),

    /**
     * 按“比例”方式收费
     */
    BY_RATIO(2);

    public final int type;

    ChargeType(int type) {
        this.type = type;
    }

    public static ChargeType valueOf(Integer type) {
        for (ChargeType chargeType : values()) {
            if (type != null && chargeType.type == type) {
                return chargeType;
            }
        }

        return null;
    }
}
