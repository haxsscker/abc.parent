/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 转让标状态
 *
 * @author segen189 2014年11月22日 下午8:24:21
 */
public enum TransferLoanState {
    /**
     * 已删除
     */
    DELETED(-1, "已删除"),

    /**
     * 待审核
     */
    WAIT_REVIEW(0, "待审核"),

    /**
     * 初审已通过
     */
    FIRST_REVIEW_PASS(1, "初审已通过"),

    /**
     * 初审未通过
     */
    FIRST_REVIEW_FAIL(2, "初审未通过"),

    /**
     * 转让招标中
     */
    TRANSFERING(3, "转让招标中"),

    /**
     * 满标待审
     */
    FULL_WAIT_REVIEW(4, "满标待审"),

    /**
     * 满标审核通过
     */
    FULL_REVIEW_PASS(5, "满标审核通过"),

    /**
     * 满标审核未通过
     */
    FULL_REVIEW_FAIL(6, "满标审核未通过"),

    /**
     * 已流标
     */
    BID_CANCELED(7, "已流标"),

    /**
     * 划转中
     */
    MONEY_TRANSFERING(8, "划转中"),

    /**
     * 已划转
     */
    MONEY_TRANSFERED(9, "已划转");

    TransferLoanState(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static TransferLoanState valueOf(Integer state) {
        for (TransferLoanState value : values()) {
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
