package com.autoserve.abc.service.biz.enums;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-04,11:08
 */
public enum ReviewSendState {
    SEND_TO_PLATFORM(0, "发送到平台审核"),
    SEND_TO_LOAN_GOV(1, "发送到小贷审核"),
    SEND_TO_GUAR_GOV(2, "发送到担保审核");

    public final int type;
    public final String prompt;

    ReviewSendState(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static ReviewSendState valueOf(Integer type) {
        checkNotNull(type, "参数type不能为null");

        for (ReviewSendState sendState : values()) {
            if (sendState.type == type) {
                return sendState;
            }
        }

        throw new IllegalArgumentException("枚举类型ReviewSendState中没有type为" + type + "的枚举值");
    }
}
