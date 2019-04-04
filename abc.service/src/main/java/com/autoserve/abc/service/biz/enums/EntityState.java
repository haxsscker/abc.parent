package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：员工/用户/机构等实体状态
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum EntityState {

    /**已删除**/
    STATE_DELETED(-1, "已删除"),

    /**停用**/
    STATE_DISABLE(0, "已停用"),

    /**启用**/
    STATE_ENABLE(1, "启用");

    EntityState(int state, String des) {
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

    public static EntityState valueOf(Integer state) {
        for (EntityState entityState : values()) {
            if (state != null && entityState.state == state) {
                return entityState;
            }
        }

        return null;
    }
}
