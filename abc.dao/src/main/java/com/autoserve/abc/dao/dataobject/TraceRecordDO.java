package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * TraceRecordDO abc_trace_record
 */
public class TraceRecordDO {
    /**
     * 主键 abc_trace_record.tr_id
     */
    private Integer trId;

    /**
     * 普通标id abc_trace_record.tr_loan_id
     */
    private Integer trLoanId;

    /**
     * 要跟踪的标的类型 abc_trace_record.tr_bid_type
     */
    private Integer trBidType;

    /**
     * 要跟踪的标的id abc_trace_record.tr_bid_id
     */
    private Integer trBidId;

    /**
     * 要跟踪的标的旧的项目状态 abc_trace_record.tr_bid_old_state
     */
    private Integer trBidOldState;
    private String 	trBidOldStateStr;
    
    /**
     * 要跟踪的标的新的项目状态 abc_trace_record.tr_bid_new_state
     */
    private Integer trBidNewState;
    private String trBidNewStateStr;
    /**
     * 跟踪操作 abc_trace_record.tr_bid_trace_operation
     */
    private Integer trBidTraceOperation;

    /**
     * 创建人 abc_trace_record.tr_creator
     */
    private Integer trCreator;
    private String trCreatorStr;

    /**
     * 创建时间 abc_trace_record.tr_createtime
     */
    private Date    trCreatetime;
    private String trCreatetimeStr;
    /**
     * 备注说明 abc_trace_record.tr_note
     */
    private String  trNote;

    public Integer getTrId() {
        return trId;
    }

    public void setTrId(Integer trId) {
        this.trId = trId;
    }

    public Integer getTrLoanId() {
        return trLoanId;
    }

    public void setTrLoanId(Integer trLoanId) {
        this.trLoanId = trLoanId;
    }

    public Integer getTrBidType() {
        return trBidType;
    }

    public void setTrBidType(Integer trBidType) {
        this.trBidType = trBidType;
    }

    public Integer getTrBidId() {
        return trBidId;
    }

    public void setTrBidId(Integer trBidId) {
        this.trBidId = trBidId;
    }

    public Integer getTrBidOldState() {
        return trBidOldState;
    }

    public void setTrBidOldState(Integer trBidOldState) {
        this.trBidOldState = trBidOldState;
    }

    public Integer getTrBidNewState() {
        return trBidNewState;
    }

    public void setTrBidNewState(Integer trBidNewState) {
        this.trBidNewState = trBidNewState;
    }

    public Integer getTrBidTraceOperation() {
        return trBidTraceOperation;
    }

    public void setTrBidTraceOperation(Integer trBidTraceOperation) {
        this.trBidTraceOperation = trBidTraceOperation;
    }

    public Integer getTrCreator() {
        return trCreator;
    }

    public void setTrCreator(Integer trCreator) {
        this.trCreator = trCreator;
    }

    public Date getTrCreatetime() {
        return trCreatetime;
    }

    public void setTrCreatetime(Date trCreatetime) {
        this.trCreatetime = trCreatetime;
    }

    public String getTrNote() {
        return trNote;
    }

    public void setTrNote(String trNote) {
        this.trNote = trNote;
    }

	public String getTrCreatorStr() {
		return trCreatorStr;
	}

	public void setTrCreatorStr(String trCreatorStr) {
		this.trCreatorStr = trCreatorStr;
	}

	public String getTrBidOldStateStr() {
		return trBidOldStateStr;
	}

	public void setTrBidOldStateStr(String trBidOldStateStr) {
		this.trBidOldStateStr = trBidOldStateStr;
	}

	public String getTrBidNewStateStr() {
		return trBidNewStateStr;
	}

	public void setTrBidNewStateStr(String trBidNewStateStr) {
		this.trBidNewStateStr = trBidNewStateStr;
	}

	public String getTrCreatetimeStr() {
		return trCreatetimeStr;
	}

	public void setTrCreatetimeStr(String trCreatetimeStr) {
		this.trCreatetimeStr = trCreatetimeStr;
	}


}
