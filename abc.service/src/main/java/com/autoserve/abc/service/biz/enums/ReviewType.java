package com.autoserve.abc.service.biz.enums;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 审核类型的枚举类 枚举值的no属性对应数据库中abc_review表的review_type字段值，因此：
 * 注意：添加新的枚举值时，请按顺序在最后添加，不要修改现有的枚举值
 *
 * @author yuqing.zheng Created on 2014-11-17,17:29
 */
public enum ReviewType {

    /**
     * 意向审核
     */
    INTENTION_REVIEW(1, "意向审核"),

    /**
     * 项目初审
     */
    LOAN_FIRST_REVIEW(2, "项目初审"),

    /**
     * 满标审核
     */
    LOAN_FULL_BID_REVIEW(3, "满标审核"),

    /**
     * 项目转让审核
     */
    LOAN_TRANSFER_REVIEW(4, "项目转让审核"),

    /**
     * 转让满标审核
     */
    TRANSFER_FULL_BID_REVIEW(5, "转让满标审核"),

    /**
     * 收购审核
     */
    LOAN_PURCHASE_REVIEW(6, "收购审核"),

    /**
     * 收购满标审核
     */
    PURCHASE_FULL_BID_REVIEW(7, "收购满标审核"),

    /**
     * 撤销融资项目审核
     */
    LOAN_CANCLE_REVIEW(8, "撤销融资项目审核"),

    /**
     * 退费审核
     */
    REFUND_REVIEW(9, "退费审核"),

    /**
     * 实名认证审核
     */
    REAL_NAME_AUTHENTICATION_REVIEW(10, "实名认证审核"),

    /**
     * 证明材料审核
     */
    CERTIFICATE_MATERIAL_REVIEW(11, "证明材料审核"),

    /**
     * 现场认证审核
     */
    SCENE_CERTIFICATE_REVIEW(12, "现场认证审核"),

    /**
     * 有限合伙预约审核
     */
    PARTNER_RESERVATION_REVIEW(13, "有限合伙预约审核"),

    /**
     * 线下充值审核
     */
    OFFLINE_RECHARGE_REVIEW(14, "线下充值审核"),

    /**
     * 机构信息修改审核
     */
    GOVERNMENT_INFO_MODIFY_REVIEW(15, "机构信息修改审核"),

    /**
     * 积分兑现审核
     */
    SCORE_REDEEM_REVIEW(16, "积分兑现审核"),

    /**
     * 融资审核（融资维护） 注意：融资审核与意向审核基本类似，都是项目初审前的审核； 区别是意向审核是对前台用户发起的融资意向进行审核，
     * 融资审核时对后台发标进行的审核
     */
    FINANCING_REVIEW(17, "融资审核"),

    /**
     * 信用额度审核
     */
    CREDIT_APPLY_REVIEW(18, "信用额度审核"),

    /**
     * 融资意向退回审核
     */
    INTENTION_BACK_REVIEW(19, "融资意向退回审核"),
    /**
     * 信用额度审核
     */
    AUTO_TRANSFER_REVIEW(20, "自主转账审核"),
    
    /**
     * 担保审核
     */
    GUAR_REVIEW(21, "担保审核"),
    
    /**
     * 担保退回审核
     */
    GUAR_BACK_REVIEW(22, "担保退回审核");

    public final int    type;
    public final String prompt;

    ReviewType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static ReviewType valueOf(Integer type) {
        checkNotNull(type, "参数type不能为null");

        for (ReviewType reviewType : values()) {
            if (reviewType.type == type) {
                return reviewType;
            }
        }

        throw new IllegalArgumentException("枚举类型ReviewType中没有type为" + type + "的枚举值");
    }
}
