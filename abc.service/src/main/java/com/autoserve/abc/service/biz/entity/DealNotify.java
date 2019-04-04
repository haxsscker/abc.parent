/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

import com.autoserve.abc.service.biz.enums.DealState;

/**
 * 交易结果通知entity
 * 
 * @author J.YL 2014年11月24日 下午3:50:02
 */
public class DealNotify {
    /**
     * 交易内部流水号
     */
    private String     innerSeqNo;
    /**
     * 交易状态
     */
    private DealState  state;
    /**
     * 交易总金额 单位：RMB Yuan 0.01～1000000.00
     */
    private BigDecimal totalFee;

    public DealState getState() {
        return state;
    }

    public void setState(DealState state) {
        this.state = state;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

}
