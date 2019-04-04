/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;

/**
 * 收购标的认购
 *
 * @author segen189 2014年12月15日 下午4:43:54
 */
public class BuyLoanSubscribe {
    /**
     * 主键id
     */
    private Integer               id;

    /**
     * 收购申请id
     */
    private Integer               buyId;

    /**
     * 贷款id
     */
    private Integer               loanId;

    /**
     * 转让人
     */
    private Integer               userId;

    /**
     * 转让日期
     */
    private Date                  transferTime;

    /**
     * 转让金额
     */
    private BigDecimal            transferMoney;

    /**
     * 收益金额
     */
    private BigDecimal            earnMoney;

    /**
     * 状态
     */
    private BuyLoanSubscribeState state;

    /**
     * 创建时间
     */
    private Date                  createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public BigDecimal getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(BigDecimal earnMoney) {
        this.earnMoney = earnMoney;
    }

    public BuyLoanSubscribeState getState() {
        return state;
    }

    public void setState(BuyLoanSubscribeState state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}
