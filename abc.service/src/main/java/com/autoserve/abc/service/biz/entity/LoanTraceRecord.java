/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanTraceOperation;

/**
 * 项目跟踪状态记录
 *
 * @author segen189 2015年1月9日 下午3:41:20
 */
public class LoanTraceRecord {
    /**
     * 主键
     */
    private Integer            id;

    /**
     * 普通标id
     */
    private Integer            loanId;

    /**
     * 旧的项目状态
     */
    private LoanState          oldLoanState;

    /**
     * 新的项目状态
     */
    private LoanState          newLoanState;

    /**
     * 项目跟踪状态
     */
    private LoanTraceOperation loanTraceOperation;

    /**
     * 创建人
     */
    private Integer            creator;

    /**
     * 创建时间
     */
    private Date               createtime;

    /**
     * 备注说明
     */
    private String             note;

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

    public LoanState getOldLoanState() {
        return oldLoanState;
    }

    public void setOldLoanState(LoanState oldLoanState) {
        this.oldLoanState = oldLoanState;
    }

    public LoanState getNewLoanState() {
        return newLoanState;
    }

    public void setNewLoanState(LoanState newLoanState) {
        this.newLoanState = newLoanState;
    }

    public LoanTraceOperation getLoanTraceOperation() {
        return loanTraceOperation;
    }

    public void setLoanTraceOperation(LoanTraceOperation loanTraceOperation) {
        this.loanTraceOperation = loanTraceOperation;
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
