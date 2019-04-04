package com.autoserve.abc.service.biz.enums;

/**
 * 账户类别：1：投资账户 2：融资账户
 *
 * @author RJQ 2014/11/17 20:25.
 */
public enum AccountCategory {
	/**投资账户*/
    INVESTACCOUNT(1, "投资账户"),
    /**融资账户*/
    LOANACCOUNT(2, "融资账户"),
    /**担保账户*/
    GUARANTEEACCOUNT(3, "担保账户");
    AccountCategory(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public static AccountCategory valueOf(Integer type) {
        for (AccountCategory value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public final int    type;
    public final String des;
}
