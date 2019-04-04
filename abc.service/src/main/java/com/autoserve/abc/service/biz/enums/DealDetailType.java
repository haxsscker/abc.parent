/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 交易详情类型
 * 
 * @author J.YL 2014年11月27日 下午12:05:16
 */
public enum DealDetailType {
    /**
     * 投资金额
     */
    INVESTE_MONEY(0, "投资金额"),
    /**
     * 撤投金额
     */
    WITHDRAWAL_INVESTER_MONEY(1, "撤投金额"),
    /**
     * 还款本金
     */
    PAYBACK_CAPITAL(2, "还款本金"),
    /**
     * 还款利息
     */
    PAYBACK_INTEREST(3, "还款利息"),
    /**
     * 追加利息 
     */
    ADDITIONAL_INTEREST(30, "追加利息"),
    /**
     * 超期罚金（其实就是罚息）
     */
    PAYBACK_OVERDUE_FINE(4, "超期罚金"),
    /**
     * 平台服务费
     */
    PLA_SERVE_FEE(5, "平台服务费"),
    /**
     * 充值金额
     */
    RECHARGE_MONEY(6, "充值金额"),
    /**
     * 提现金额
     */
    TOCASH_MONEY(7, "提现金额"),
    /**
     * 退款金额
     */
    REFUND_MONEY(8, "退款金额"),
    /**
     * 划转金额
     */
    APPROPRIATE_MONEY(9, "划转金额"),
    /**
     * 平台手续费
     */
    PLA_FEE(11, "平台手续费"),
    /**
     * 担保服务费
     */
    INSURANCE_FEE(12, "担保服务费"),
    /**
     * 转让金额
     */
    DEBT_TRANSFER_MONEY(13, "转让金额"),
    /**
     * 转让手续费
     */
    DEBT_TRANSFER_FEE(14, "转让手续费"),
    /**
     * 收购金额
     */
    PURCHASE_MONEY(15, "收购金额"),
    /**
     * 收购手续费
     */
    PURCHASE_FEE(16, "收购手续费"),
    /**
     * 流标解冻金额
     */
    ABORT_BID_MONEY(17, "流标金额"),
    /**
     * 流标退回金额
     */
    ABORT_BID_BACK_MONEY(18, "流标退回金额"),
    /**
     * 流标金额
     */
    SECONDARY(19, "二次分配"),
    /**
     * 红包金额
     */
    RED_MONEY(16, "红包金额"),
    /**
     * 自主转账
     */
    AUTO_TRANSFER(17, "自主转账"),
    /**
     * 违约罚金  夏同同/2016-04-10
     */
    PAYBACK_BREACH_FINE(20, "违约罚金"),
    /**
     * 银行手续费  zhouyu/2018-07-07
     */
    MERCHANT_TRANS(21, "银行手续费");

    DealDetailType(int type, String des) {
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

    public static DealDetailType valueOf(Integer type) {
        for (DealDetailType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }
}
