package com.autoserve.abc.service.biz.enums;

public enum GovType {
    /**
     * 发送对象类型(0：平台 1：小贷机构 2：担保机构) abc_loan_intent.li_gov_type
     */
    PLATFORM(0, "平台"),
    SMALL_LOAN(1, "小贷机构"),
    GUARANTEE_ORG(2, "担保机构");

    GovType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public final int    type;
    public final String des;

    public static GovType valueOf(Integer type) {
        for (GovType govType : values()) {
            if (type != null && govType.type == type) {
                return govType;
            }
        }
        return null;
    }

}
