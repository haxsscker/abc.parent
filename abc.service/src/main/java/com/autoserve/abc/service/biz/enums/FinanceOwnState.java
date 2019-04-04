package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：房产/汽车等资产拥有状况
 *
 * @author RJQ 2014/11/18 9:37.
 */
public enum FinanceOwnState {

    NOT_OWN(0, "不拥有"),
    OWN(1, "拥有");

    FinanceOwnState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final int state;
    public final String des;

    public static FinanceOwnState valueOf(Integer state) {
        for (FinanceOwnState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
