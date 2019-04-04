/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 收购项目跟踪状态
 *
 * @author segen189 2015年1月9日 下午3:45:01
 */
public enum BuyLoanTraceOperation {

    /**
     * 发起项目收购
     */
    launch(1, "发起项目收购"),

    /**
     * 收购初审被否决
     */
    failFirstReview(2, "收购初审被否决"),

    /**
     * 收购招标中
     */
    buying(3, "收购招标中"),

    /**
     * 收购满标待审
     */
    fullWaitReview(4, "收购满标待审"),

    /**
     * 收购满标审核通过
     */
    passFullReview(5, "收购满标审核通过"),

    /**
     * 收购满标审核否决
     */
    failFullReview(6, "收购满标审核否决"),

    /**
     * 收购标流标
     */
    bidCanceled(7, "收购标流标"),

    /**
     * 资金划转中
     */
    moneyTransfering(8, "资金划转中"),

    /**
     * 资金划转失败
     */
    moneyTransferFail(9, "资金划转失败"),

    /**
     * 资金划转成功
     */
    moneyTransferSucceed(10, "资金划转成功");

    BuyLoanTraceOperation(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static BuyLoanTraceOperation valueOf(Integer state) {
        for (BuyLoanTraceOperation value : values()) {
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
