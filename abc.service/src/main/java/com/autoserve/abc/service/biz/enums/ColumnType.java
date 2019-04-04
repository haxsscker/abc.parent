/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * @author liuwei 2014年12月5日 上午11:10:49
 */
public enum ColumnType {
    INSYSTEM(0, "系统内"),
    OUTSYSTEM(1, "系统外");
    ColumnType(int state, String des) {
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

    public static ColumnType valueOf(Integer state) {
        for (ColumnType value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
