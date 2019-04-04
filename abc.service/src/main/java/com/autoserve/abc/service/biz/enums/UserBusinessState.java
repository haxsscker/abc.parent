package com.autoserve.abc.service.biz.enums;

/**
 * @author RJQ 2015/1/4 17:15.
 */
public enum UserBusinessState {

    REGISTERED(1, "注册成功"),

    ACCOUNT_OPENED(2, "账户已开户"),

    RECHARGED(3, "已充值"),

    INVESTED(4, "已投资"),

    BUSINESSSTATE(5, "是否查询已开户的用户"),//用于查询 若是以开户用户XML中判断状态不等于1
    
    ACCOUNT_OPENING(6, "账户开户中"),
    
    ACCOUNT_FAILURE(7, "开户失败");

    UserBusinessState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static UserBusinessState valueOf(Integer state) {
        for (UserBusinessState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public final int    state;
    public final String des;
}
