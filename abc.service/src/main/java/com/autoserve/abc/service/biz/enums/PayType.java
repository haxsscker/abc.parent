/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 还款计划的还款类型
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum PayType {

    /**
     * 正常还清<br>
     * 前台用户正常还清本期还款计划
     */
    COMMON_CLEAR(1, "正常还清"),

    /**
     * 平台代还清<br>
     * 平台代替借款人，先将本期剩余应还款额还给投资人；<br>
     * 借款人后续还款时，需将 被代还的款额+逾期的利息+罚息 一起补还给平台
     */
    PLA_CLEAR(2, "平台代还清"),

    /**
     * 强制还清<br>
     * 平台强制将借款人的钱还给投资人
     */
    FORCE_CLEAR(3, "强制还清"),
    /**
     * 提前还清
     */
    AHEAD_CLEAR(4, "提前还清"),
    /**
     * 担保代还清
     */
    GUAR_CLEAR(5, "担保代还清");

    PayType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static PayType valueOf(Integer type) {
        for (PayType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    type;
    public final String prompt;

}
