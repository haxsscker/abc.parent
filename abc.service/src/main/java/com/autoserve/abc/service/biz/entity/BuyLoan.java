/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.BuyLoanState;

/**
 * 收购标
 *
 * @author segen189 2014年11月22日 上午9:42:05
 */
public class BuyLoan {
    /**
     * 主键id
     */
    private Integer      id;

    /**
     * 原始贷款id
     */
    private Integer      originId;

    /**
     * 收购人
     */
    private Integer      userId;

    /**
     * 收购金额
     */
    private BigDecimal   buyMoney;

    /**
     * 收购债券总额
     */
    private BigDecimal   buyTotal;

    /**
     * 收购期数
     */
    private Integer      buyPeriod;

    /**
     * 收购手续费
     */
    private BigDecimal   fee;

    /**
     * 收购状态
     */
    private BuyLoanState buyLoanState;

    /**
     * 创建日期
     */
    private Date         createtime;

    /**
     * 认购开始日期
     */
    private Date         startTime;

    /**
     * 认购结束日期
     */
    private Date         endTime;

    /**
     * 满标日期
     */
    private Date         fullTime;

    /**
     * 放款成功时间
     */
    private Date         fullTransferedtime;

    /**
     * 当前投标总额
     */
    private BigDecimal   currentInvest;

    /**
     * 当前有效投标总额
     */
    private BigDecimal   currentValidInvest;

    /**
     * 下一次还款计划id
     */
    private Integer      nextPaymentId;

    /**
     * 冻结流水号
     */
    private String       freezeSeqNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal money) {
        this.buyMoney = money;
    }

    public BigDecimal getBuyTotal() {
        return buyTotal;
    }

    public void setBuyTotal(BigDecimal buyTotal) {
        this.buyTotal = buyTotal;
    }

    public Integer getBuyPeriod() {
        return buyPeriod;
    }

    public void setBuyPeriod(Integer buyPeriod) {
        this.buyPeriod = buyPeriod;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BuyLoanState getBuyLoanState() {
        return buyLoanState;
    }

    public void setBuyLoanState(BuyLoanState buyLoanState) {
        this.buyLoanState = buyLoanState;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getFullTime() {
        return fullTime;
    }

    public void setFullTime(Date fullTime) {
        this.fullTime = fullTime;
    }

    public Date getFullTransferedtime() {
        return fullTransferedtime;
    }

    public void setFullTransferedtime(Date fullTransferedtime) {
        this.fullTransferedtime = fullTransferedtime;
    }

    public BigDecimal getCurrentInvest() {
        return currentInvest;
    }

    public void setCurrentInvest(BigDecimal currentInvest) {
        this.currentInvest = currentInvest;
    }

    public BigDecimal getCurrentValidInvest() {
        return currentValidInvest;
    }

    public void setCurrentValidInvest(BigDecimal currentValidInvest) {
        this.currentValidInvest = currentValidInvest;
    }

    public Integer getNextPaymentId() {
        return nextPaymentId;
    }

    public void setNextPaymentId(Integer nextPaymentId) {
        this.nextPaymentId = nextPaymentId;
    }

    public String getFreezeSeqNo() {
        return freezeSeqNo;
    }

    public void setFreezeSeqNo(String freezeSeqNo) {
        this.freezeSeqNo = freezeSeqNo;
    }

}
