package com.autoserve.abc.service.biz.enums;

public enum CheckState {
    /**
     * 发送对象类型(0：平台 1：小贷机构 2：担保机构) abc_loan_intent.li_gov_type
     */
    WAIT_REVIEW(0, "待审核"),
    YES(1, "同意"),
    BACK(2, "退回"),
    NO(3, "否决");

    CheckState(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static CheckState valueOf(Integer type) {
        for (CheckState checkState : values()) {
            if (type != null && checkState.type == type) {
                return checkState;
            }
        }
        return null;
    }

}
