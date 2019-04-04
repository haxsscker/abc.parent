package com.autoserve.abc.service.biz.enums;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 类的实现描述：审核状态
 *
 * @author RJQ 2014/11/17 20:06.
 */
public enum ReviewState {

    PENDING_REVIEW(0, "待审核"),
    PASS_REVIEW(1, "审核已通过"),
    FAILED_PASS_REVIEW(2, "审核未通过"),
    SYSTEM_DEALED(9, "系统已处理"),
    CANCEL_WAIT_REVIEW(3, "流标处理中");
    ReviewState(int state, String des) {
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

    public static ReviewState valueOf(Integer state) {
        checkNotNull(state, "参数state不能为null");

        for (ReviewState value : values()) {
            if (value.state == state) {
                return value;
            }
        }

        throw new IllegalArgumentException("枚举类型ReviewState中没有state为" + state + "的枚举值");
    }
}
