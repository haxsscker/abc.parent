/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 项目跟踪状态
 *
 * @author segen189 2015年1月9日 下午3:45:01
 */
public enum LoanTraceOperation {
	
	
	/**
     * 融资维护
     * xiatt 20161018 客户要求加入融资维护
     */
    LOAN_APPLY(-9,"融资维护"),
	
    /**
     * 从待发布状态有条件地撤回到项目初审通过
     */
    revokeToWaitProjectReview(1, "从已发布状态撤回到待发布"),

    /**
     * 从已发布状态撤回到待发布
     */
    // revokeToWaitRelease(2, "从已发布状态撤回到待发布"),

    /**
     * 把项目状态从待发布退回项目初审通过
     */
    sendBackToWaitProjectReview(3, "把项目状态从待发布退回项目初审通过"),

    /**
     * 把项目状态从项目初审通过改为待发布
     */
    sendToWaitRelease(4, "把项目状态从项目初审通过改为待发布"),

    /**
     * 融资维护删除
     */
    removeProject(5, "融资维护删除"),

    /**
     * 流标
     */
    cancelLoan(6, "流标"),

    /**
     * 普通标强制满标
     */
    forceLoanToFull(7, "普通标强制满标"),

    /**
     * 转让标强制满标
     */
    forceTransferLoanToFull(8, "转让标强制满标"),

    /**
     * 收购标强制满标
     */
    forceBuyLoanToFull(9, "收购标强制满标"),

    /**
     * 普通标满标
     */
    loanToFull(10, "普通标满标"),

    /**
     * 转让标满标
     */
    transferLoanToFull(11, "转让标满标"),

    /**
     * 收购标满标
     */
    buyLoanToFull(12, "收购标满标"),

    /**
     * 普通标满标回到投资中
     */
    loanFullBack(13, "普通标满标回到投资中"),

    /**
     * 转让标满标回到投资中
     */
    transferLoanFullBack(14, "转让标满标回到投资中"),

    /**
     * 收购标满标回到认购中
     */
    buyLoanFullBack(15, "收购标满标回到认购中"),

    /**
     * 发布普通标
     */
    releaseLoan(16, "发布普通标"),

    /**
     * 取消发布普通标
     */
    cancelRelease(17, "取消发布普通标"),

    /**
     * 普通标满标资金划转
     */
    loanMoneyTransfer(18, "普通标满标资金划转"),

    /**
     * 转让标满标资金划转
     */
    transferLoanMoneyTransfer(19, "转让标满标资金划转"),

    /**
     * 收购标满标资金划转
     */
    buyLoanMoneyTransfer(20, "收购标满标资金划转"),

    // TODO 下面审核相关字段重新命名

    /**
     * 意向审核
     */
    INTENTION_REVIEW(21, "意向审核"),

    /**
     * 项目初审
     */
    LOAN_FIRST_REVIEW(22, "项目初审"),

    /**
     * 满标审核
     */
    LOAN_FULL_BID_REVIEW(23, "满标审核"),

    /**
     * 项目转让审核
     */
    LOAN_TRANSFER_REVIEW(24, "项目转让审核"),

    /**
     * 转让满标审核
     */
    TRANSFER_FULL_BID_REVIEW(25, "转让满标审核"),

    /**
     * 收购审核
     */
    LOAN_PURCHASE_REVIEW(26, "收购审核"),

    /**
     * 收购满标审核
     */
    PURCHASE_FULL_BID_REVIEW(27, "收购满标审核"),

    /**
     * 撤销融资项目审核
     */
    LOAN_CANCLE_REVIEW(28, "撤销融资项目审核"),

    /**
     * 退费审核
     */
    REFUND_REVIEW(29, "退费审核"),

    /**
     * 实名认证审核
     */
    REAL_NAME_AUTHENTICATION_REVIEW(30, "实名认证审核"),

    /**
     * 证明材料审核
     */
    CERTIFICATE_MATERIAL_REVIEW(31, "证明材料审核"),

    /**
     * 现场认证审核
     */
    SCENE_CERTIFICATE_REVIEW(32, "现场认证审核"),

    /**
     * 有限合伙预约审核
     */
    PARTNER_RESERVATION_REVIEW(33, "有限合伙预约审核"),

    /**
     * 线下充值审核
     */
    OFFLINE_RECHARGE_REVIEW(34, "线下充值审核"),

    /**
     * 机构信息修改审核
     */
    GOVERNMENT_INFO_MODIFY_REVIEW(35, "机构信息修改审核"),

    /**
     * 积分兑现审核
     */
    SCORE_REDEEM_REVIEW(36, "积分兑现审核"),

    /**
     * 融资审核（融资维护） 注意：融资审核与意向审核基本类似，都是项目初审前的审核； 区别是意向审核是对前台用户发起的融资意向进行审核，
     * 融资审核时对后台发标进行的审核
     */
    FINANCING_REVIEW(37, "融资审核"),

    /**
     * 信用额度审核
     */
    CREDIT_APPLY_REVIEW(38, "信用额度审核"),

    /**
     * 融资意向退回审核
     */
    INTENTION_BACK_REVIEW(39, "融资意向退回审核"),

    /**
     * 还款结束
     */
    repayedCompleted(40, "还款结束"),

    /**
     * 担保审核
     * 担保审核
     */
    GUAR_REVIEW(41,"担保审核");
	

    LoanTraceOperation(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static LoanTraceOperation valueOf(Integer state) {
        for (LoanTraceOperation value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    state;
    public final String prompt;

}
