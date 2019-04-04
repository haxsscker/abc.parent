package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.ReviewState;

/**
 * 类AutoTransfer.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月23日 上午11:04:05
 */
public class AutoTransfer {
    /**
     * 自主转账标识 abc_auto_transfer.at_id
     */
    private Integer     id;

    /**
     * 付款人账户id account_info表 abc_auto_transfer.at_pay_accotunt
     */
    private String      payAccotunt;

    /**
     * 收款人账户id account_info abc_auto_transfer.at_receibe_accotunt
     */
    private String      receibeAccotunt;

    /**
     * 交易金钱数 abc_auto_transfer.at_money_amount
     */
    private String      moneyAmount;

    /**
     * 金额类型 abc_auto_transfer.at_money_type
     */
    private String      moneyType;

    /**
     * 交易状态,0 等待响应，1 成功 2 失败 abc_auto_transfer.at_state
     */
    private DealState   state;

    /**
     * 交易创建日期 abc_auto_transfer.at_operate_date
     */
    private Date        operateDate;

    /**
     * 交易操作人 abc_auto_transfer.at_operator
     */
    private Integer     operator;

    /**
     * 资金调用流水号，通过其可以确定一个唯一的资金调用,暂时未使用。在第三方支付接口不支持批量操作时使用。
     * abc_auto_transfer.at_out_seq_no
     */
    private String      outSeqNo;

    /**
     * 审核状态(0、待审核，1、通过，2、未通过） abc_auto_transfer.at_audit_state
     */
    private ReviewState auditState;
    /**
     * 转入人ID
     */
    private Integer receibeUserId;
    /**
     * 备注
     */
    private String atRemarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayAccotunt() {
        return payAccotunt;
    }

    public void setPayAccotunt(String payAccotunt) {
        this.payAccotunt = payAccotunt;
    }

    public String getReceibeAccotunt() {
        return receibeAccotunt;
    }

    public void setReceibeAccotunt(String receibeAccotunt) {
        this.receibeAccotunt = receibeAccotunt;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public DealState getState() {
        return state;
    }

    public void setState(DealState state) {
        this.state = state;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getOutSeqNo() {
        return outSeqNo;
    }

    public void setOutSeqNo(String outSeqNo) {
        this.outSeqNo = outSeqNo;
    }

    public ReviewState getAuditState() {
        return auditState;
    }

    public void setAuditState(ReviewState auditState) {
        this.auditState = auditState;
    }

	public Integer getReceibeUserId() {
		return receibeUserId;
	}

	public void setReceibeUserId(Integer receibeUserId) {
		this.receibeUserId = receibeUserId;
	}

	public String getAtRemarks() {
		return atRemarks;
	}

	public void setAtRemarks(String atRemarks) {
		this.atRemarks = atRemarks;
	}

}
