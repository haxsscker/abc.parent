/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.List;

import com.autoserve.abc.service.biz.enums.DealType;

/**
 * 交易entity
 *
 * @author J.YL 2014年11月21日 下午3:45:48
 */
public class Deal {

    private List<DealDetail> dealDetail;

    /**
     * 内部交易号
     */
    private InnerSeqNo       innerSeqNo;
    /**
     * 特定businessType对应的特定交易的Id 交易类型为：投资、还款、满额资金划转、撤投、收购 时该字段为 项目的id（loanId）
     */
    private Integer          businessId;
    /**
     * 交易类型 (0, "投资"), (1, "撤投"), (2, "资金划转"), (3, "还款"), (4, "充值"), (5, "提现"),
     * (6, "退款") (7, "收购") (8, "流标");
     */
    private DealType         businessType;
    /**
     * 操作人id
     */
    private Integer          operator;

    public List<DealDetail> getDealDetail() {
        return dealDetail;
    }

    public void setDealDetail(List<DealDetail> dealDetail) {
        this.dealDetail = dealDetail;
    }

    public InnerSeqNo getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(InnerSeqNo innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public DealType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(DealType businessType) {
        this.businessType = businessType;
    }

}
