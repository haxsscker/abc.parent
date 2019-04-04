package com.autoserve.abc.service.biz.enums;

/**
 * 类RsState.java的实现描述： 红包使用状态 : 0:失效 1:未使用，2：已使用 abc_red_send.rs_state
 * 
 * @author lipeng 2014年12月26日 下午8:10:24
 */
public enum RsState {
    /**
     * 已过期
     */
    FAILURE(0, "已过期"),
    /**
     * 未使用
     */
    WITHOUT_USE(1, "未使用"),
    /**
     * 已使用
     */
    USE(2, "已使用");

    RsState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public final int    state;
    public final String des;

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static RsState valueOf(Integer state) {
        for (RsState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
