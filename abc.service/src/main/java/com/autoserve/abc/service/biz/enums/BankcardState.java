package com.autoserve.abc.service.biz.enums;
/**
 * 
 * 类BankcardState.java的实现描述：绑卡状态的枚举
 * @author lipeng 2014年12月27日 下午7:18:40
 */
public enum BankcardState {

    DISABLE(0, "未绑定"),
    ENABLE(1, "已绑定");

    BankcardState(int state, String des) {
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

    public static BankcardState valueOf(Integer state) {
        for (BankcardState bankcardState : values()) {
            if (state != null && bankcardState.state == state) {
                return bankcardState;
            }
        }

        return null;
    }

}
