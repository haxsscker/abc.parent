/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.moneyManage;

import java.math.BigDecimal;

/**
 * 类RefundListCheckViewVO.java的实现描述：退款审核页面VO
 * 
 * @author J.YL 2014年12月24日 下午8:45:02
 */
public class RefundListCheckViewVO {

    /**
     * 退款申请id
     */
    private Integer    refundId;
    /**
     * 收款人
     */
    private String     userName;
    /**
     * 收款人帐号
     */
    private String     accountNo;
    /**
     * 收款人手机号
     */
    private String     userPhone;
    /**
     * 退款金额
     */
    private BigDecimal refundMoney;
    /**
     * 退款原因
     */
    private String     refundReason;
    /**
     * 退费状态
     */
    private String     refundState;
    /**
     * 审核状态
     */
    private String     reviewState;
    /**
     * 申请日期
     */
    private String     applyDate;
    /**
     * 审核意见
     */
    private String     applyReviewMessage;
    /**
     * 可否审核标志，只有状态为待审核的申请为可审核
     */
    private boolean    canCheck;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public BigDecimal getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(BigDecimal refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getReviewState() {
        return reviewState;
    }

    public void setReviewState(String reviewState) {
        this.reviewState = reviewState;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

    public boolean isCanCheck() {
        return canCheck;
    }

    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
    }

    public String getRefundState() {
        return refundState;
    }

    public void setRefundState(String refundState) {
        this.refundState = refundState;
    }

    /**
     * @return the applyReviewMessage
     */
    public String getApplyReviewMessage() {
        return applyReviewMessage;
    }

    /**
     * @param applyReviewMessage the applyReviewMessage to set
     */
    public void setApplyReviewMessage(String applyReviewMessage) {
        this.applyReviewMessage = applyReviewMessage;
    }

}
