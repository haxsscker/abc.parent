package com.autoserve.abc.service.biz.enums;

public enum LoanIntentState {
    /**
     * 发送状态(0：待发送 1：已发送平台 2：已发送担保) abc_loan_intent.li_loan_state
     */
    WAIT_SEND(0, "待发送"),
    SEND_PLATFORM(1, "已发送平台"),
    SEND_GUARANTEE(2, "已发送担保");

    LoanIntentState(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static LoanIntentState valueOf(Integer type) {
        for (LoanIntentState loanIntentState : values()) {
            if (type != null && loanIntentState.type == type) {
                return loanIntentState;
            }
        }
        return null;
    }

}
