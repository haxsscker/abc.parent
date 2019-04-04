package com.autoserve.abc.service.biz.enums;

/**
 * 类InvestSetOpenState.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月10日 下午5:09:03
 */
public enum InvestSetOpenState {
    /** 停用 **/
    STATE_DISABLE(0, "停用"),

    /** 启用 **/
    STATE_ENABLE(1, "启用");

    InvestSetOpenState(int state, String des) {
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

    public static InvestSetOpenState valueOf(Integer state) {
        for (InvestSetOpenState investSetOpenState : values()) {
            if (state != null && investSetOpenState.state == state) {
                return investSetOpenState;
            }
        }

        return null;
    }

}
