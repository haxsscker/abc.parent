/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 收购标状态
 *
 * @author segen189 2014年11月22日 下午8:24:38
 */
public enum BuyLoanState {
    /**
     * 已删除
     */
    DELETED(-1, "已删除"),

    /**
     * 收购申请待审核
     */
    WAIT_REVIEW(1, "收购申请待审核"),

    /**
     * 收购申请审核通过
     */
    //FIRST_REVIEW_PASS(2, "收购申请审核通过"),

    /**
     * 收购申请审核未通过
     */
    FIRST_REVIEW_FAIL(3, "收购申请审核未通过"),

    /**
     * 收购中
     */
    BUYING(4, "收购中"),

    /**
     * 满标待审
     */
    FULL_WAIT_REVIEW(5, "满标待审"),

    /**
     * 满标审核通过
     */
    FULL_REVIEW_PASS(6, "满标审核通过"),

    /**
     * 满标审核未通过
     */
    FULL_REVIEW_FAIL(7, "满标审核未通过"),

    /**
     * 已流标
     */
    BID_CANCELED(8, "已流标"),

    /**
     * 划转中
     */
    MONEY_TRANSFERING(9, "划转中"),

    /**
     * 已划转
     */
    MONEY_TRANSFERED(10, "已划转");

    BuyLoanState(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static BuyLoanState valueOf(Integer state) {
        for (BuyLoanState value : values()) {
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
