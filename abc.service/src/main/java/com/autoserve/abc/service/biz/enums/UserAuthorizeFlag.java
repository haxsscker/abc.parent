package com.autoserve.abc.service.biz.enums;

/**
 * 是否开启自动转账授权 0 未开启 1 已开启
 * 
 * @author liuwei 2015年1月28日 上午10:09:27
 */
public enum UserAuthorizeFlag {
    DISABLE(0, "未開啟"),
    ENABLE(1, "已開啟");

    UserAuthorizeFlag(int state, String des) {
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

    public static UserAuthorizeFlag valueOf(Integer state) {
        for (UserAuthorizeFlag userAuthorizeFlag : values()) {
            if (state != null && userAuthorizeFlag.state == state) {
                return userAuthorizeFlag;
            }
        }

        return null;
    }
}
