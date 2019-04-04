package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：自动投标状态
 *
 * @author RJQ 2014/11/17 20:30.
 */
public enum AutoInvestState {

    DISABLE(0, "未开启"),
    ENABLE(1, "已开启");

    AutoInvestState(int state, String des) {
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
}
