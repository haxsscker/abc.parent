package com.autoserve.abc.service.biz.enums;

public enum IntentState {
    /**
     * 意向状态(此字段是只针对于“信贷审核和担保审核”环节查询条件中“审核状态”的问题 0：待本人审核 1：已退回 2：已否决 3：待发送
     * 4：待担保审核 5：待平台审核 6：待发布 7：招标中 8：满标待审 9：已流标 10：待划转 11：还款中 12：已结清)
     * abc_loan_intent.li_intent_state
     */
    WAIT_REVIEW(0, "待本人审核"),
    BACK(1, "已退回"),
    NO(2, "已否决"),
    WAIT_SEND(3, "待发送"),
    GUARANTEE_REVIEW(4, "待担保审核"),
    PLATFORM_REVIEW(5, "待平台审核"),
    WAIT_PUBLISH(6, "待发布"),
    TENDERING(7, "招标中"),
    FULL_REVIEW(8, "满标待审"),
    PASS(9, "已流标"),
    TRANSFERRED(10, "待划转"),
    REPAYMENT(11, "还款中"),
    SETTLE(12, "已结清");

    IntentState(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static IntentState valueOf(Integer type) {
        for (IntentState intentState : values()) {
            if (type != null && intentState.type == type) {
                return intentState;
            }
        }
        return null;
    }

}
