/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.CashOperateState;
import com.autoserve.abc.service.biz.enums.CashOperateType;

/**
 * 第三方接口资金操作记录entity
 * 
 * @author J.YL 2014年11月22日 下午3:35:36
 */
public class CashRecord {
    /**
     * 主键id abc_cash_record.cr_id
     */
    private Integer          crId;

    /**
     * 请求url abc_cash_record.cr_request_url
     */
    private String           crRequestUrl;

    /**
     * 请求方式 abc_cash_record.cr_request_method
     */
    private String           crRequestMethod;

    /**
     * 请求参数 abc_cash_record.cr_request_parameter
     */
    private String           crRequestParameter;

    /**
     * 交易流水号 abc_cash_record.cr_seq_no
     */
    private String           crSeqNo;

    /**
     * 支付接口返回数据 abc_cash_record.cr_response
     */
    private String           crResponse;

    /**
     * 支付请求的返回状态 abc_cash_record.cr_response_state
     */
    private CashOperateState crResponseState;

    /**
     * 支付钱数 abc_cash_record.cr_money_amount
     */
    private BigDecimal       crMoneyAmount;

    /**
     * 支付操作类型（转账，退费，冻结，解冻，充值，提现） abc_cash_record.cr_operate_type
     */
    private CashOperateType  crOperateType;

    /**
     * 操作日期 abc_cash_record.cr_operate_date
     */
    private Date             crOperateDate;

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public String getCrRequestUrl() {
        return crRequestUrl;
    }

    public void setCrRequestUrl(String crRequestUrl) {
        this.crRequestUrl = crRequestUrl;
    }

    public String getCrRequestMethod() {
        return crRequestMethod;
    }

    public void setCrRequestMethod(String crRequestMethod) {
        this.crRequestMethod = crRequestMethod;
    }

    public String getCrRequestParameter() {
        return crRequestParameter;
    }

    public void setCrRequestParameter(String crRequestParameter) {
        this.crRequestParameter = crRequestParameter;
    }

    public String getCrSeqNo() {
        return crSeqNo;
    }

    public void setCrSeqNo(String crSeqNo) {
        this.crSeqNo = crSeqNo;
    }

    public String getCrResponse() {
        return crResponse;
    }

    public void setCrResponse(String crResponse) {
        this.crResponse = crResponse;
    }

    public CashOperateState getCrResponseState() {
        return crResponseState;
    }

    public void setCrResponseState(CashOperateState crResponseState) {
        this.crResponseState = crResponseState;
    }

    public BigDecimal getCrMoneyAmount() {
        return crMoneyAmount;
    }

    public void setCrMoneyAmount(BigDecimal crMoneyAmount) {
        this.crMoneyAmount = crMoneyAmount;
    }

    public CashOperateType getCrOperateType() {
        return crOperateType;
    }

    public void setCrOperateType(CashOperateType crOperateType) {
        this.crOperateType = crOperateType;
    }

    public Date getCrOperateDate() {
        return crOperateDate;
    }

    public void setCrOperateDate(Date crOperateDate) {
        this.crOperateDate = crOperateDate;
    }

}
