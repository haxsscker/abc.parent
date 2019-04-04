package com.autoserve.abc.service.biz.enums;

public enum LastStep {
    /**
     * 上一环节名称(0：意向审核 2：信贷审核 3：担保审核) abc_loan_intent.li_last_step
     */
    INTENT_REVIEW(0, "意向审核"),
    CREDIT_REVIEW(1, "信贷审核"),
    GUARANTEE_REVIEW(2, "担保审核");

    LastStep(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static LastStep valueOf(Integer type) {
        for (LastStep lastStep : values()) {
            if (type != null && lastStep.type == type) {
                return lastStep;
            }
        }
        return null;
    }

}
