/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 还款计划的状态
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum PayState {
    /**
     * 未激活<br>
     * 待满标资金划转成功时，未激活状态转为未还清状态
     */
    INACTIVED(-1, "未激活"),

    /**
     * 未还清<br>
     * 前台用户未还清本期还款计划
     */
    UNCLEAR(0, "未还清"),

    /**
     * 付款中
     */
    PAYING(1, "付款中"),

    /**
     * 已还清
     */
    CLEAR(2, "已还清"),
    /**
     * 被取消
     */
    CANCELED(3, "被取消");

    PayState(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static PayState valueOf(Integer state) {
        for (PayState value : values()) {
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
