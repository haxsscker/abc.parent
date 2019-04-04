package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：机构提供担保状态
 *
 * @author RJQ 2014/11/17 20:12.
 */
public enum GovProvideGuarState {

    NOT_PROVIDE(0, "不提供"),
    PROVIDE(1, "提供");

    GovProvideGuarState(int state, String des) {
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
