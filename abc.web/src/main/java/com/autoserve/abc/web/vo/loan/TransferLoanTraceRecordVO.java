package com.autoserve.abc.web.vo.loan;

/**
 * 类实现描述 转让跟踪的VO
 * 
 * @author Tiuwer 2015年3月31日 下午3:29:05
 */
public class TransferLoanTraceRecordVO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 普通标id
     */
    private Integer loanId;

    /**
     * 转让标id
     */
    private Integer transferLoanId;

    /**
     * 旧的转让项目状态
     */
    private String  oldTransferLoanState;

    /**
     * 新的转让项目状态
     */
    private String  newTransferLoanState;

    /**
     * 项目跟踪状态
     */
    private String  transferLoanTraceOperation;

    /**
     * 创建人
     */
    private String  creatorName;
    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 创建时间
     */
    private String  createtime;

    /**
     * 备注说明
     */
    private String  note;

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

    public String getOldTransferLoanState() {
        return oldTransferLoanState;
    }

    public void setOldTransferLoanState(String oldTransferLoanState) {
        this.oldTransferLoanState = oldTransferLoanState;
    }

    public String getNewTransferLoanState() {
        return newTransferLoanState;
    }

    public void setNewTransferLoanState(String newTransferLoanState) {
        this.newTransferLoanState = newTransferLoanState;
    }

    public String getTransferLoanTraceOperation() {
        return transferLoanTraceOperation;
    }

    public void setTransferLoanTraceOperation(String transferLoanTraceOperation) {
        this.transferLoanTraceOperation = transferLoanTraceOperation;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

}
