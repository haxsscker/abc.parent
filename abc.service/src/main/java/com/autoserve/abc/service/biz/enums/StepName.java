package com.autoserve.abc.service.biz.enums;


public enum StepName {
    /**
     * 发送环节(0：意向审核 1：项目初审 2：信贷审核 3：担保审核 4：满标审核) abc_loan_intent.li_step_name
     */
    INTENT_REVIEW(0, "意向审核"),
    PRO_REVIEW(1, "项目初审"),
    CREDIT_REVIEW(2, "信贷审核"),
    GUARANTEE_REVIEW(3, "担保审核"),
    FULL_REVIEW(4, "满标审核");

    StepName(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static StepName valueOf(Integer type) {
        for (StepName loanIntentState : values()) {
            if (type != null && loanIntentState.type == type) {
                return loanIntentState;
            }
        }
        return null;
    }

}
