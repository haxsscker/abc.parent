package com.autoserve.abc.service.biz.enums;

/**
 * 转让进度环节名称
 *
 * @author liuwei 2015年1月6日 下午4:17:54
 */
public enum ScheduleStage {

    TRANSFER_CONFIRM(0, "转让认购"),
    CREDIT_TRANSFER_TRAIL(1, "债权转让初审"),
    TRANSFER_TRAIL(1, "转让初审"),
    CREDIT_TRANSFER_TRAIL_APPLY(1, "网贷平台债权转让申请");

    ScheduleStage(int state, String des) {
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

    public static ScheduleStage valueOf(Integer state) {
        for (ScheduleStage scheduleStage : values()) {
            if (state != null && scheduleStage.state == state) {
                return scheduleStage;
            }
        }

        return null;
    }

}
