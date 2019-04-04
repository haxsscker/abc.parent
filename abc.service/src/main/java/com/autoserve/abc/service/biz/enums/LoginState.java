package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：登录状态
 *
 * @author RJQ 2014/11/17 20:22.
 */
public enum LoginState {

    DELETED(-1, "已被删除"),
    LOGIN_FAIL(0, "登录失败"),
    LOGIN_SUCCESS(1, "登录成功"),
    LOGOUT(2, "已经登出");

    LoginState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static LoginState valueOf(Integer state) {
        for (LoginState loginState : values()) {
            if (state != null && state.equals(loginState.getState())) {
                return loginState;
            }
        }

        return null;
    }

    public final int    state;
    public final String des;
}
