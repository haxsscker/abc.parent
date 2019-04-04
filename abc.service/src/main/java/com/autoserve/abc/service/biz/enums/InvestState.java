/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 投资活动的状态
 *
 * @author segen189 2014年11月21日 下午6:05:24
 */
public enum InvestState {
    /**
     * 已删除
     */
    DELETED(-1, "已删除"),

    /**
     * 未支付
     */
    UNPAID(0, "未支付"),

    /**
     * 支付失败
     */
    FAIL_PAID(1, "支付失败"),

    /**
     * 支付成功
     */
    PAID(2, "支付成功"),

    /**
     * 已撤资
     */
    WITHDRAWED(3, "已撤资"),

    /**
     * 待收益
     */
    EARNING(4, "待收益"),

    /**
     * 被转让
     */
    TRANSFERED(5, "被转让"),

    /**
     * 被收购
     */
    BUYED(6, "被收购"),

    /**
     * 收益完成
     */
    EARN_COMPLETED(7, "收益完成"),

    /**
     * 被流标
     */
    CANCELED(8, "被流标");

    InvestState(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static InvestState valueOf(Integer state) {
        for (InvestState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    state;
    public final String prompt;
}
