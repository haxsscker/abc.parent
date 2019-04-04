package com.autoserve.abc.service.biz.enums;

/**
 * 申请额度类型 1：借款信用额度 2：投资担保额度 3：借款担保额度
 *
 * @author RJQ 2015/1/6 16:23.
 */
public enum CreditType {
    LOAN_CREDIT(1, "借款信用额度"),
    INVEST_GUARANTEE_AMOUNT(2, "投资担保额度"),
    LOAN_GUARANTEE_AMOUNT(3, "借款担保额度");

    CreditType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public static CreditType valueOf(Integer type) {
        for (CreditType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public final int type;
    public final String des;
}
