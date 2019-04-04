package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：认证状态
 *
 * @author RJQ 2014/11/17 20:37.
 */
public enum AuthenticateState {

    NOT_AUTHENTICATED(0, "未认证"),
    AUTHENTICATED(1, "已认证");

    AuthenticateState(int state, String des) {
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
