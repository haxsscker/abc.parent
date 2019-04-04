/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Map;

import com.autoserve.abc.service.biz.enums.DealDetailType;

/**
 * 交易详情
 * 
 * @author J.YL 2014年11月22日 上午9:27:42
 */
public class DealDetail {
    /**
     * 付款帐号
     */
    private String         payAccountId;
    /**
     * 收款帐号
     */
    private String         receiveAccountId;
    /**
     * 收款金额
     */
    private BigDecimal     moneyAmount;
    /**
     * 交易详情类型 :(0, "投资金额"), (1, "撤投金额"), (2, "还款本金"), (3, "还款利息"), (4, "超期罚金"),
     * (5, "平台服务费"), (6, "充值金额"), (7, "提现金额"), (8, "退款金额"), (9, "划转金额"), (11,
     * "平台手续费"), (10, "担保服务费"), (11, "转让金额"), (12, "转让手续费"), (13, "收购金额"), (14,
     * "收购手续费");
     */
    private DealDetailType dealDetailType;
    /**
    * 接收发往第三方Map数据
    * @return
    */
    public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	private Map data ;
    public String getPayAccountId() {
        return payAccountId;
    }

    public void setPayAccountId(String payAccountId) {
        this.payAccountId = payAccountId;
    }

    public String getReceiveAccountId() {
        return receiveAccountId;
    }

    public void setReceiveAccountId(String receiveAccountId) {
        this.receiveAccountId = receiveAccountId;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public DealDetailType getDealDetailType() {
        return dealDetailType;
    }

    public void setDealDetailType(DealDetailType dealDetailType) {
        this.dealDetailType = dealDetailType;
    }
}
