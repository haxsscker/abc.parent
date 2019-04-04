/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 资金操作类型
 * 
 * @author J.YL 2014年11月22日 下午3:21:51
 */
public enum CashOperateType {
    TRANSFER(0, "转账"),
    REFUND(1, "退费"),
    FREEZE(2, "冻结"),
    UNFREEZE(3, "解冻"),
    RECHARGE(4, "充值"),
    TOCASH(5, "提现"),
    BACKMONEY(6, "流标");
    CashOperateType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int    type;
    public final String des;

    public static CashOperateType valueOf(Integer type) {
        for (CashOperateType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }
}
