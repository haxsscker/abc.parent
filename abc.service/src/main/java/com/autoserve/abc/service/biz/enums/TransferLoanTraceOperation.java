/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 转让项目跟踪状态
 *
 * @author segen189 2015年1月9日 下午3:45:01
 */
public enum TransferLoanTraceOperation {

    /**
     * 发起项目转让
     */
    launch(1, "发起项目转让"),

    /**
     * 转让初审被否决
     */
    failFirstReview(2, "转让初审被否决"),

    /**
     * 转让招标中
     */
    transfering(3, "转让招标中"),

    /**
     * 转让满标待审
     */
    fullWaitReview(4, "转让满标待审"),

    /**
     * 转让满标审核通过
     */
    passFullReview(5, "转让满标审核通过"),

    /**
     * 转让满标审核否决
     */
    failFullReview(6, "转让满标审核否决"),

    /**
     * 转让标流标
     */
    bidCanceled(7, "转让标流标"),

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

    TransferLoanTraceOperation(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static TransferLoanTraceOperation valueOf(Integer state) {
        for (TransferLoanTraceOperation value : values()) {
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
