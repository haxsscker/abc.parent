package com.autoserve.abc.service.biz.enums;

/**
 * 账户类别：1：投资账户 2：融资账户
 *
 * @author RJQ 2014/11/17 20:25.
 */
public enum BalanceSynAccount {
    /**融资账户*/
	QUERYBALANCE_SYNCH_ACCOUNT("QUERYBALANCE_SYNCH_ACCOUNT", "余额查询同步账户开关"),
    /**余额查询同步账户*/
    BALANCE_SYNCHACCOUNTON("1", "余额查询同步账户"),
    /**余额查询不同步账户*/
    BALANCE_SYNCHACCOUNTOFF("0", "余额查询不同步账户");

    BalanceSynAccount(String value, String des) {
        this.value = value;
        this.des = des;
    }

    public final String value;
    public final String des;
    
	public String getValue() {
		return value;
	}

	public String getDes() {
		return des;
	}
    
}
