/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 类FeeType.java的实现描述：TODO 类实现描述
 * 
 * @author J.YL 2014年11月25日 下午4:04:27
 */
public enum FeeType {
    /**
     * 平台手续费
     */
    PLA_FEE(1, "平台手续费"),
    /**
     * 平台服务费
     */
    PLA_SERVE_FEE(2, "平台服务费"),
    /**
     * 担保服务费
     */
    INSURANCE_FEE(3, "担保服务费"),
    /**
     * 转让手续费
     */
    TRANSFER_FEE(4, "转让手续费"),
    /**
     * 收购手续费
     */
    PURCHASE_FEE(5, "收购手续费");
    /**
     * 
     */
    FeeType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int    type;
    public final String des;

    public static FeeType valueOf(Integer type) {
        for (FeeType feeType : values()) {
            if (type != null && feeType.type == type) {
                return feeType;
            }
        }

        return null;
    }
}
