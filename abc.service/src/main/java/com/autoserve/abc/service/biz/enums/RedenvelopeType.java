package com.autoserve.abc.service.biz.enums;

/**
 * 红包类型 1：注册送红包 2：投资返送红包 3：活动派送红包 4：推荐送红包 5：积分兑换红包 6：项目奖励红包
 * 
 * @author fangrui 2014年12月25日 下午3:08:26
 */
public enum RedenvelopeType {
    REGISTOR_RED(1, "注册送红包"),
    INVESTOR_RED(2, "投资返送红包"),
    PERSON_RED(3, "活动派送红包"),
    INVIT_RED(4, "推荐送红包"),
    SCORE_RED(5, "积分兑换红包"),
    PROJECT_RED(6, "项目奖励红包"),
    INVIT_INVEST_RED(7, "邀请投资奖励红包");
    RedenvelopeType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int    type;
    public final String des;

    public static RedenvelopeType valueOf(Integer type) {
        for (RedenvelopeType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }
}
