/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 收益计划的状态
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum IncomePlanState {
    /**
     * 已删除
     */
    DELETED(-2, "已删除"),

    /**
     * 未激活
     */
    INACTIVED(-1, "未激活"),

    /**
     * 待收益
     */
    GOING(0, "待收益"),

    /**
     * 付款中
     */
    PAYING(1, "付款中"),

    /**
     * 已结清
     */
    CLEARED(2, "已结清"),

    /**
     * 已被转让
     */
    TRANSFERED(3, "已被转让"),

    /**
     * 已被收购
     */
    BUYED(4, "已被收购"),
    /**
     * 被取消
     */
    CANCELED(5, "被取消");

    IncomePlanState(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static IncomePlanState valueOf(Integer state) {
        for (IncomePlanState value : values()) {
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
