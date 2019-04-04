/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.List;
import java.util.Map;

/**
 * 交易创建结束返回的数据结构
 * 
 * @author J.YL 2014年11月22日 下午3:06:38
 */
public class DealReturn {

    /**
     * 内部交易流水号，一个交易流水号对应多个资金调用流水号 dealRecords中的所有内部交易流水号相同
     */
    private String           drInnerSeqNo;
    /**
     * 创建交易记录的结果，一对多支付时会产生多个交易记录
     */
    private List<DealRecord> dealRecords;
    /**
     * 第三方支付接口支持批量支付时 list里只有一条数据
     */
    private List<CashRecord> cashRecords;

    /**
     * 第三方支付接口支持批量支付时 Map里只有一条数据
     */
    private String           params;

    public String getParams() {
        return params;
    }

    public void setParams(String string) {
        this.params = string;
    }

    public List<CashRecord> getCashRecords() {
        return cashRecords;
    }

    public void setCashRecords(List<CashRecord> cashRecords) {
        this.cashRecords = cashRecords;
    }

    public List<DealRecord> getDealRecords() {
        return dealRecords;
    }

    public void setDealRecords(List<DealRecord> dealRecords) {
        this.dealRecords = dealRecords;
    }

    public String getDrInnerSeqNo() {
        return drInnerSeqNo;
    }

    public void setDrInnerSeqNo(String drInnerSeqNo) {
        this.drInnerSeqNo = drInnerSeqNo;
    }
}
