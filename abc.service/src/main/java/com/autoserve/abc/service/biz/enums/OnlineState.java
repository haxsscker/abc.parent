package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：在线状态
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum OnlineState {

    STATE_NOT_ONLINE(0, "离线"),
    STATE_ONLINE(1, "在线");

    OnlineState(int state, String des) {
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
