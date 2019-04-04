package com.autoserve.abc.service.biz.enums;
/**
 * 
 * 类MobileState.java的实现描述：手机绑定状态
 * @author ipeng 2014年12月22日 下午2:23:20
 */
public enum MobileState {

    DISABLE(0, "未绑定"),
    ENABLE(1, "已绑定");

    MobileState(int state, String des) {
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

    public static MobileState valueOf(Integer state) {
        for (MobileState mobileState : values()) {
            if (state != null && mobileState.state == state) {
                return mobileState;
            }
        }

        return null;
    }

}
