package com.autoserve.abc.service.biz.enums;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 审核操作
 *
 * @author yuqing.zheng
 *         Created on 2014-11-18,17:10
 */
public enum ReviewOpType {
    /**
     * 通过
     */
    PASS(1, "通过"),

    /**
     * 否决
     */
    REJECT(2, "否决"),

    /**
     * 退回
     */
    ROLL_BACK(3, "退回"),

    /**
     * 挂起
     */
    SUSPEND(4, "挂起"),

    /**
     * 发送
     * 注意：对审核进行发送后，该审核不会自动返回
     */
    SEND(5, "发送"),

    /**
     * 撤回
     */
    REVOKE(6, "撤回");

    public final int    typeId;
    public final String prompt;

    ReviewOpType(int typeId, String prompt) {
        this.typeId = typeId;
        this.prompt = prompt;
    }

    public static ReviewOpType valueOf(Integer typeId) {
        checkNotNull(typeId, "参数typeId不能为null");

        for (ReviewOpType op : values()) {
            if (op.typeId == typeId) {
                return op;
            }
        }

        throw new IllegalArgumentException("枚举类型ReviewOpType中没有typeId为" + typeId + "的枚举值");
    }
}
