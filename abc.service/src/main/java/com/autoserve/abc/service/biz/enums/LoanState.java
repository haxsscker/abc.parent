/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 普通标状态
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum LoanState {
    /**
     * -1:已删除
     */
    DELETED(-1, "已删除", true),
    
    /**
     * 融资维护
     * xiatt 20161018 客户要求加入融资维护
     */
    LOAN_APPLY(-9,"融资维护",false),

    /**
     * 意向待审核
     */
    WAIT_INTENT_REVIEW(1, "意向待审核", false),

    /**
     * 意向审核通过
     */
    INTENT_REVIEW_PASS(2, "意向审核通过", false),

    /**
     * 意向审核未通过
     */
    INTENT_REVIEW_FAIL(3, "意向审核未通过", true),

    /**
     * 待项目初审
     */
    WAIT_PROJECT_REVIEW(4, "待项目初审", false),

    /**
     * 项目初审通过
     */
    PROJECT_REVIEW_PASS(5, "项目初审通过", false),

    /**
     * 项目初审已退回
     */
    PROJECT_REVIEW_BACK(6, "项目初审已退回", false),

    /**
     * 项目初审未通过
     */
    PROJECT_REVIEW_FAIL(7, "项目初审未通过", true),

    /**
     * 待发布
     */
    WAIT_RELEASE(8, "待发布", false),

    /**
     * 招标中
     */
    BID_INVITING(9, "招标中", false),

    /**
     * 满标待审
     */
    FULL_WAIT_REVIEW(10, "满标待审", false),

    /**
     * 满标审核通过
     */
    FULL_REVIEW_PASS(11, "满标审核通过", false),

    /**
     * 满标审核未通过
     */
    FULL_REVIEW_FAIL(12, "满标审核未通过", true),
    /**
     * 流标中
     */
    BID_CANCELING(20, "流标中", true),

    /**
     * 已流标
     */
    BID_CANCELED(13, "已流标", true),

    /**
     * 划转中
     */
    MONEY_TRANSFERING(14, "划转中", false),

    /**
     * 还款中
     */
    REPAYING(15, "还款中", false),

    /**
     * 已结清
     */
    REPAY_COMPLETED(16, "已结清", true),

    /**
     * 融资维护待审核
     */
    WAIT_MAINTAIN_REVIEW(17, "融资维护待审核", false),

    /**
     * 融资维护审核通过
     */
    MAINTAIN_REVIEW_PASS(18, "融资维护审核通过", false),

    /**
     * 融资维护审核未通过
     */
    MAINTAIN_REVIEW_FAIL(19, "融资维护审核未通过", true),
    
    /**
     * 担保待审核
     */
    WAIT_GUAR_REVIEW(21, "担保待审核",true),
    
    /**
     * 担保审核通过
     */
    GUAR_REVIEW_PASS(22, "担保审核通过",true),
    
    /**
     * 担保审核未通过
     */
    GUAR_REVIEW_FAIL(23, "担保审核未通过",true);

    LoanState(int state, String prompt, boolean endedState) {
        this.state = state;
        this.prompt = prompt;
        this.endedState = endedState;
    }

    public static LoanState valueOf(Integer state) {
        for (LoanState value : values()) {
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

    public boolean isEndedState() {
        return endedState;
    }

    public final int    state;
    public final String prompt;
    public boolean      endedState;

}
