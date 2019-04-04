package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：(邮箱，银行卡，手机等绑定状态)
 * 
 * @author RJQ 2014/11/17 20:47.
 */
public enum BindingState {

    NOT_BOUND(0, "未绑定"),
    BOUND(1, "已绑定");

    BindingState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final int    state;
    public final String des;

    public static BindingState valueOf(Integer state) {
        for (BindingState bindingState : values()) {
            if (state != null && bindingState.state == state) {
                return bindingState;
            }
        }

        return null;
    }
}
