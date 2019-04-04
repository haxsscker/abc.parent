package com.autoserve.abc.dao.dataobject;

import java.util.Date;

public class AutoTransferDO {
	
	  /**
     * 自主转账标识 abc_auto_transfer.at_id
     */
    private Integer     atId;

    /**
     * 付款人账户id account_info表 abc_auto_transfer.at_pay_accotunt
     */
    private String      atPayAccotunt;

    /**
     * 收款人账户id account_info abc_auto_transfer.at_receibe_accotunt
     */
    private String      atReceibeAccotunt;

    /**
     * 交易金钱数 abc_auto_transfer.at_money_amount
     */
    private String      atMoneyAmount;

    /**
     * 金额类型 abc_auto_transfer.at_money_type
     */
    private String      atMoneyType;

    /**
     * 交易状态,0 等待响应，1 成功 2 失败 abc_auto_transfer.at_state
     */
    private Integer   atState;

    /**
     * 交易创建日期 abc_auto_transfer.at_operate_date
     */
    private Date        atOperateDate;

    /**
     * 交易操作人 abc_auto_transfer.at_operator
     */
    private Integer     atOperator;

    /**
     * 资金调用流水号，通过其可以确定一个唯一的资金调用,暂时未使用。在第三方支付接口不支持批量操作时使用。
     * abc_auto_transfer.at_out_seq_no
     */
    private String      atOutSeqNo;

    /**
     * 审核状态(0、待审核，1、通过，2、未通过） abc_auto_transfer.at_audit_state
     */
    private Integer atAuditState;
    /**
     * 付款人ID
     */
    private Integer atReceibeUserId;
    /**
     * 备注
     */
    private String atRemarks;
    
    /**
     * 收款人或付款人账号
     */
    private  String payOrReciveAccount;

	public Integer getAtId() {
		return atId;
	}

	public void setAtId(Integer atId) {
		this.atId = atId;
	}

	public String getAtPayAccotunt() {
		return atPayAccotunt;
	}

	public void setAtPayAccotunt(String atPayAccotunt) {
		this.atPayAccotunt = atPayAccotunt;
	}

	public String getAtReceibeAccotunt() {
		return atReceibeAccotunt;
	}

	public void setAtReceibeAccotunt(String atReceibeAccotunt) {
		this.atReceibeAccotunt = atReceibeAccotunt;
	}

	public String getAtMoneyAmount() {
		return atMoneyAmount;
	}

	public void setAtMoneyAmount(String atMoneyAmount) {
		this.atMoneyAmount = atMoneyAmount;
	}

	public String getAtMoneyType() {
		return atMoneyType;
	}

	public void setAtMoneyType(String atMoneyType) {
		this.atMoneyType = atMoneyType;
	}

	public Integer getAtState() {
		return atState;
	}

	public void setAtState(Integer atState) {
		this.atState = atState;
	}

	public Date getAtOperateDate() {
		return atOperateDate;
	}

	public void setAtOperateDate(Date atOperateDate) {
		this.atOperateDate = atOperateDate;
	}

	public Integer getAtOperator() {
		return atOperator;
	}

	public void setAtOperator(Integer atOperator) {
		this.atOperator = atOperator;
	}

	public String getAtOutSeqNo() {
		return atOutSeqNo;
	}

	public void setAtOutSeqNo(String atOutSeqNo) {
		this.atOutSeqNo = atOutSeqNo;
	}

	public Integer getAtAuditState() {
		return atAuditState;
	}

	public void setAtAuditState(Integer atAuditState) {
		this.atAuditState = atAuditState;
	}

	public Integer getAtReceibeUserId() {
		return atReceibeUserId;
	}

	public void setAtReceibeUserId(Integer atReceibeUserId) {
		this.atReceibeUserId = atReceibeUserId;
	}

	public String getAtRemarks() {
		return atRemarks;
	}

	public void setAtRemarks(String atRemarks) {
		this.atRemarks = atRemarks;
	}

	public String getPayOrReciveAccount() {
		return payOrReciveAccount;
	}

	public void setPayOrReciveAccount(String payOrReciveAccount) {
		this.payOrReciveAccount = payOrReciveAccount;
	}	
	
}
