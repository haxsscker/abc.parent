package com.autoserve.abc.service.biz.enums;

/**
 * 积分类型
 *
 * @author RJQ 2014/11/25 17:19.
 */
public enum ScoreType {
    /**
     * 登陆送积分
     */
    LOGIN_SCORE("loginscore"),

    /**
     * 开户送积分
     */
    REALNAME_SCORE("realnamescore"),

    /**
     * 推荐投资人送积分
     */
    RECOMMEND_SCORE("recommendscore"),

    /**
     * 投资送积分
     */
    INVEST_SCORE("investscore");

    //    /**
    //     * 邀请推荐
    //     */
    //    INVITE("yqtj"),
    //
    //    /**
    //     * 还款结清
    //     */
    //    REFUND_SETTLE("hkjq"),
    //
    //    /**
    //     * 强制还款
    //     */
    //    FORCE_REFUND("qzhk"),
    //
    //    /**
    //     * 系统代还
    //     */
    //    SYSTEM_REFUND("xtdh"),
    //
    //    /**
    //     * 正常还款
    //     */
    //    NORMAL_REFUND("zchk"),
    //
    //    /**
    //     * 债权收购
    //     */
    //    PURCHASE_LOAN("zqsg"),
    //
    //    /**
    //     * 债权转让
    //     */
    //    TRANSFER_LOAN("zqzr"),
    //
    //    /**
    //     * 项目投资
    //     */
    //    INVEST_PROJECT("xmtz"),
    //
    //    /**
    //     * 银行绑卡
    //     */
    //    BIND_CARD("yhbk"),
    //
    //    /**
    //     * 账户开户
    //     */
    //    OPEN_ACCOUNT("zhkh");

    ScoreType(String code) {
        this.code = code;
    }

    public String getCode() {

        return code;
    }

    public final String code;
}
