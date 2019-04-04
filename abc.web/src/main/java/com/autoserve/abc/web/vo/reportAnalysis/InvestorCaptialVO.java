/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.reportAnalysis;

import java.math.BigDecimal;

/**
 * 类InvestorCaptialVO.java的实现描述：投资人资金对账表VO
 * 
 * @author J.YL 2014年12月20日 下午6:08:52
 */
public class InvestorCaptialVO {

    private String     customer_name;
    private String     real_name;
    private BigDecimal assets_total = BigDecimal.ZERO;
    private BigDecimal availableBalance = BigDecimal.ZERO;
    private BigDecimal amountFrozen = BigDecimal.ZERO;
    private BigDecimal pro_collect_money = BigDecimal.ZERO;
    private BigDecimal pro_collect_rate = BigDecimal.ZERO;
    private BigDecimal pro_collect_over_rate = BigDecimal.ZERO;
    private BigDecimal pro_invest_money = BigDecimal.ZERO;
    private BigDecimal buy_money = BigDecimal.ZERO;
    private BigDecimal transfer_money = BigDecimal.ZERO;
    private BigDecimal transfer_fee = BigDecimal.ZERO;
    private BigDecimal purchase_money = BigDecimal.ZERO;
    private BigDecimal purchasefee = BigDecimal.ZERO;
    private String customer_id;
    private String account_no;

    /**
     * @return the customer_name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * @param customer_name the customer_name to set
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * @return the real_name
     */
    public String getReal_name() {
        return real_name;
    }

    /**
     * @param real_name the real_name to set
     */
    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    /**
     * @return the assets_total
     */
    public BigDecimal getAssets_total() {
        return assets_total;
    }

    /**
     * @param assets_total the assets_total to set
     */
    public void setAssets_total(BigDecimal assets_total) {
        this.assets_total = assets_total;
    }

    /**
     * @return the availableBalance
     */
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    /**
     * @param availableBalance the availableBalance to set
     */
    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * @return the amountFrozen
     */
    public BigDecimal getAmountFrozen() {
        return amountFrozen;
    }

    /**
     * @param amountFrozen the amountFrozen to set
     */
    public void setAmountFrozen(BigDecimal amountFrozen) {
        this.amountFrozen = amountFrozen;
    }

    /**
     * @return the pro_collect_money
     */
    public BigDecimal getPro_collect_money() {
    	if(pro_collect_money==null)
    		return BigDecimal.ZERO;
        return pro_collect_money;
    }

    /**
     * @param pro_collect_money the pro_collect_money to set
     */
    public void setPro_collect_money(BigDecimal pro_collect_money) {
        this.pro_collect_money = pro_collect_money;
    }

    /**
     * @return the pro_collect_rate
     */
    public BigDecimal getPro_collect_rate() {
    	if(pro_collect_rate==null)
    		return BigDecimal.ZERO;
        return pro_collect_rate;
    }

    /**
     * @param pro_collect_rate the pro_collect_rate to set
     */
    public void setPro_collect_rate(BigDecimal pro_collect_rate) {
        this.pro_collect_rate = pro_collect_rate;
    }

    /**
     * @return the pro_collect_over_rate
     */
    public BigDecimal getPro_collect_over_rate() {
    	if(pro_collect_over_rate==null){
    		return BigDecimal.ZERO;
    	}
        return pro_collect_over_rate;
    }

    /**
     * @param pro_collect_over_rate the pro_collect_over_rate to set
     */
    public void setPro_collect_over_rate(BigDecimal pro_collect_over_rate) {
        this.pro_collect_over_rate = pro_collect_over_rate;
    }

    /**
     * @return the pro_invest_money
     */
    public BigDecimal getPro_invest_money() {
    	if(pro_invest_money==null){
    		return BigDecimal.ZERO;
    	}
        return pro_invest_money;
    }

    /**
     * @param pro_invest_money the pro_invest_money to set
     */
    public void setPro_invest_money(BigDecimal pro_invest_money) {
        this.pro_invest_money = pro_invest_money;
    }

    /**
     * @return the buy_money
     */
    public BigDecimal getBuy_money() {
        return buy_money;
    }

    /**
     * @param buy_money the buy_money to set
     */
    public void setBuy_money(BigDecimal buy_money) {
        this.buy_money = buy_money;
    }

    /**
     * @return the transfer_money
     */
    public BigDecimal getTransfer_money() {
        return transfer_money;
    }

    /**
     * @param transfer_money the transfer_money to set
     */
    public void setTransfer_money(BigDecimal transfer_money) {
        this.transfer_money = transfer_money;
    }

    /**
     * @return the transfer_fee
     */
    public BigDecimal getTransfer_fee() {
        return transfer_fee;
    }

    /**
     * @param transfer_fee the transfer_fee to set
     */
    public void setTransfer_fee(BigDecimal transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    /**
     * @return the purchase_money
     */
    public BigDecimal getPurchase_money() {
        return purchase_money;
    }

    /**
     * @param purchase_money the purchase_money to set
     */
    public void setPurchase_money(BigDecimal purchase_money) {
        this.purchase_money = purchase_money;
    }

    /**
     * @return the purchasefee
     */
    public BigDecimal getPurchasefee() {
        return purchasefee;
    }

    /**
     * @param purchasefee the purchasefee to set
     */
    public void setPurchasefee(BigDecimal purchasefee) {
        this.purchasefee = purchasefee;
    }

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

}
