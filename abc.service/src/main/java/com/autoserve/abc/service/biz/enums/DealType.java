/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 交易类型
 *
 * @author J.YL 2014年11月21日 下午6:12:18
 */
public enum DealType {
    /**
     * 投资
     */
    INVESTER(0, "投资"),
    /**
     * 撤投
     */
    WITHDRAWAL_INVESTER(1, "撤投"),
    /**
     * 资金划转
     */
    TRANSFER(2, "资金划转"),
    /**
     * 还款
     */
    PAYBACK(3, "还款"),
    /**
     * 充值
     */
    RECHARGE(4, "充值"),
    /**
     * 提现
     */
    TOCASH(5, "提现"),
    /**
     * 退款
     */
    REFUND(6, "退款"),
    /**
     * 收购
     */
    PURCHASE(7, "收购"),
    /**
     * 流标
     */
    ABORT_BID(8, "流标"),
    /**
     * 自主转账
     */
    AUTO_TRANSFER(9, "自主转账"),
    /**
     * 红包返还
     */
    RETURN_RED(10, "红包返还"),
    /**
     * 红包返还
     */
    MERCHANT_TRANS(11, "银行手续费");
    DealType(int type, String des) {
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

    public static DealType valueOf(Integer type) {
        for (DealType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }
}
