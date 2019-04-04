/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.BuyLoanState;
import com.autoserve.abc.service.biz.enums.BuyLoanTraceOperation;

/**
 * 收购项目跟踪状态记录
 *
 * @author segen189 2015年1月9日 下午3:41:20
 */
public class BuyLoanTraceRecord {
    /**
     * 主键
     */
    private Integer               id;

    /**
     * 普通标id
     */
    private Integer               loanId;

    /**
     * 收购标id
     */
    private Integer               buyLoanId;

    /**
     * 旧的收购项目状态
     */
    private BuyLoanState          oldBuyLoanState;

    /**
     * 新的收购项目状态
     */
    private BuyLoanState          newBuyLoanState;

    /**
     * 项目跟踪状态
     */
    private BuyLoanTraceOperation buyLoanTraceOperation;

    /**
     * 创建人
     */
    private Integer               creator;

    /**
     * 创建时间
     */
    private Date                  createtime;

    /**
     * 备注说明
     */
    private String                note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getBuyLoanId() {
        return buyLoanId;
    }

    public void setBuyLoanId(Integer buyLoanId) {
        this.buyLoanId = buyLoanId;
    }

    public BuyLoanState getOldBuyLoanState() {
        return oldBuyLoanState;
    }

    public void setOldBuyLoanState(BuyLoanState oldBuyLoanState) {
        this.oldBuyLoanState = oldBuyLoanState;
    }

    public BuyLoanState getNewBuyLoanState() {
        return newBuyLoanState;
    }

    public void setNewBuyLoanState(BuyLoanState newBuyLoanState) {
        this.newBuyLoanState = newBuyLoanState;
    }

    public BuyLoanTraceOperation getBuyLoanTraceOperation() {
        return buyLoanTraceOperation;
    }

    public void setBuyLoanTraceOperation(BuyLoanTraceOperation buyLoanTraceOperation) {
        this.buyLoanTraceOperation = buyLoanTraceOperation;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
