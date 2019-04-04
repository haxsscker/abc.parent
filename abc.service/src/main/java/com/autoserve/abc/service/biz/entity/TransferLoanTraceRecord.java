/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.enums.TransferLoanTraceOperation;

/**
 * 转让项目跟踪状态记录
 *
 * @author segen189 2015年1月9日 下午3:41:20
 */
public class TransferLoanTraceRecord {
    /**
     * 主键
     */
    private Integer                    id;

    /**
     * 普通标id
     */
    private Integer                    loanId;

    /**
     * 转让标id
     */
    private Integer                    transferLoanId;

    /**
     * 旧的转让项目状态
     */
    private TransferLoanState          oldTransferLoanState;

    /**
     * 新的转让项目状态
     */
    private TransferLoanState          newTransferLoanState;

    /**
     * 项目跟踪状态
     */
    private TransferLoanTraceOperation transferLoanTraceOperation;

    /**
     * 创建人
     */
    private Integer                    creator;

    /**
     * 创建时间
     */
    private Date                       createtime;

    /**
     * 备注说明
     */
    private String                     note;

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

    public Integer getTransferLoanId() {
        return transferLoanId;
    }

    public void setTransferLoanId(Integer transferLoanId) {
        this.transferLoanId = transferLoanId;
    }

    public TransferLoanState getOldTransferLoanState() {
        return oldTransferLoanState;
    }

    public void setOldTransferLoanState(TransferLoanState oldTransferLoanState) {
        this.oldTransferLoanState = oldTransferLoanState;
    }

    public TransferLoanState getNewTransferLoanState() {
        return newTransferLoanState;
    }

    public void setNewTransferLoanState(TransferLoanState newTransferLoanState) {
        this.newTransferLoanState = newTransferLoanState;
    }

    public TransferLoanTraceOperation getTransferLoanTraceOperation() {
        return transferLoanTraceOperation;
    }

    public void setTransferLoanTraceOperation(TransferLoanTraceOperation transferLoanTraceOperation) {
        this.transferLoanTraceOperation = transferLoanTraceOperation;
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
