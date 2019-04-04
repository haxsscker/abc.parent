/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.DealDetailType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;

/**
 * DealRecordDO的entity
 * 
 * @author J.YL 2014年11月22日 上午11:59:11
 */
public class DealRecord {
    /**
     * 主键id abc_deal_record.dr_id
     */
    private Integer        id;

    /**
     * 资金调用流水号，通过其可以确定一个唯一的资金调用 abc_deal_record.dr_out_seq_no
     */
    private String         outSeqNo;

    /**
     * 内部交易流水号，一个交易流水号对应多个资金调用流水号 abc_deal_record.dr_inner_seq_no
     */
    private String         innerSeqNo;

    /**
     * 付款人账户id account_info表 abc_deal_record.dr_pay_account
     */
    private String         payAccount;

    /**
     * 收款人账户id account_info abc_deal_record.dr_receive_account
     */
    private String         receiveAccount;

    /**
     * 交易金钱数 abc_deal_record.dr_money_amount
     */
    private BigDecimal     moneyAmount;

    /**
     * 交易详情类型，还款(本金、利息、费用) 投资(投资),资金划转(资金划转,收费) 退费(退费)
     */
    private Integer detailType;
    /**
     * 交易类型(0投资，1还款，2收费，3资金划转，4退费) abc_deal_record.dr_type
     */
    private DealType       type;

    /**
     * 交易类型对应的交易的业务类型，做外键使用 abc_deal_record.dr_business_id
     */
    private Integer        businessId;

    /**
     * 资金操作记录表id abc_deal_record.dr_cash_id
     */
    private Integer        cashId;

    /**
     * 交易状态,0 等待响应，1 成功 2 失败 abc_deal_record.dr_state
     */
    private DealState      state;

    /**
     * 交易创建日期 abc_deal_record.dr_operate_date
     */
    private Date           operateDate;

    /**
     * 交易操作人 abc_deal_record.dr_operator
     */
    private Integer        operator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutSeqNo() {
        return outSeqNo;
    }

    public void setOutSeqNo(String outSeqNo) {
        this.outSeqNo = outSeqNo;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public DealType getType() {
        return type;
    }

    public void setType(DealType type) {
        this.type = type;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
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

    public Integer getDetailType() {
        return detailType;
    }

    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }
}
