/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Mar 10 16:45:23 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvestSet abc_invest_set
 */
public class InvestSetDO {
	/**
	 * abc_invest_set.is_id
	 */
	private Integer isId;

	/**
	 * 用户ID abc_invest_set.is_user_id
	 */
	private Integer isUserId;

	/**
	 * 最小借款金额 abc_invest_set.is_min_money
	 */
	private BigDecimal isMinMoney;

	/**
	 * 最大借款金额 abc_invest_set.is_max_money
	 */
	private BigDecimal isMaxMoney;

	/**
	 * 最小年化利率 abc_invest_set.is_min_rate
	 */
	private BigDecimal isMinRate;

	/**
	 * 最大年化利率 abc_invest_set.is_max_rate
	 */
	private BigDecimal isMaxRate;

	/**
	 * 借款期限 abc_invest_set.is_loan_period
	 */
	private Integer isMaxLoanPeriod;
	/**
	 * 借款期限 abc_invest_set.is_loan_period
	 */
	private Integer isMinLoanPeriod;

	/**
	 * 还款方式 0：所有 1：等额本息 2：按月还息，到期还本 abc_invest_set.is_loan_type
	 */
	private Integer isLoanType;

	/**
	 * 每笔投资金额 abc_invest_set.is_invest_money
	 */
	private BigDecimal isInvestMoney;

	/**
	 * 账户保留余额 abc_invest_set.is_sett_money
	 */
	private BigDecimal isSettMoney;

	/**
	 * 是否开启 1：已开启 0：已关闭 abc_invest_set.is_is_open
	 */
	private Integer isIsOpen;
	/**
	 * 项目类型： abc_invest_set
	 */
	private Integer isLoanCategory;

	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return abc_invest_set.is_id
	 */
	public Integer getIsId() {
		return isId;
	}

	/**
	 * @param Integer
	 *            isId (abc_invest_set.is_id )
	 */
	public void setIsId(Integer isId) {
		this.isId = isId;
	}

	/**
	 * @return abc_invest_set.is_user_id
	 */
	public Integer getIsUserId() {
		return isUserId;
	}

	/**
	 * @param Integer
	 *            isUserId (abc_invest_set.is_user_id )
	 */
	public void setIsUserId(Integer isUserId) {
		this.isUserId = isUserId;
	}

	/**
	 * @return abc_invest_set.is_min_money
	 */
	public BigDecimal getIsMinMoney() {
		return isMinMoney;
	}

	/**
	 * @param BigDecimal
	 *            isMinMoney (abc_invest_set.is_min_money )
	 */
	public void setIsMinMoney(BigDecimal isMinMoney) {
		this.isMinMoney = isMinMoney;
	}

	/**
	 * @return abc_invest_set.is_max_money
	 */
	public BigDecimal getIsMaxMoney() {
		return isMaxMoney;
	}

	/**
	 * @param BigDecimal
	 *            isMaxMoney (abc_invest_set.is_max_money )
	 */
	public void setIsMaxMoney(BigDecimal isMaxMoney) {
		this.isMaxMoney = isMaxMoney;
	}

	/**
	 * @return abc_invest_set.is_min_rate
	 */
	public BigDecimal getIsMinRate() {
		return isMinRate;
	}

	/**
	 * @param BigDecimal
	 *            isMinRate (abc_invest_set.is_min_rate )
	 */
	public void setIsMinRate(BigDecimal isMinRate) {
		this.isMinRate = isMinRate;
	}

	/**
	 * @return abc_invest_set.is_max_rate
	 */
	public BigDecimal getIsMaxRate() {
		return isMaxRate;
	}

	/**
	 * @param BigDecimal
	 *            isMaxRate (abc_invest_set.is_max_rate )
	 */
	public void setIsMaxRate(BigDecimal isMaxRate) {
		this.isMaxRate = isMaxRate;
	}

	public Integer getIsMaxLoanPeriod() {
		return isMaxLoanPeriod;
	}

	public void setIsMaxLoanPeriod(Integer isMaxLoanPeriod) {
		this.isMaxLoanPeriod = isMaxLoanPeriod;
	}

	public Integer getIsMinLoanPeriod() {
		return isMinLoanPeriod;
	}

	public void setIsMinLoanPeriod(Integer isMinLoanPeriod) {
		this.isMinLoanPeriod = isMinLoanPeriod;
	}

	/**
	 * @return abc_invest_set.is_loan_type
	 */
	public Integer getIsLoanType() {
		return isLoanType;
	}

	/**
	 * @param Integer
	 *            isLoanType (abc_invest_set.is_loan_type )
	 */
	public void setIsLoanType(Integer isLoanType) {
		this.isLoanType = isLoanType;
	}

	/**
	 * @return abc_invest_set.is_invest_money
	 */
	public BigDecimal getIsInvestMoney() {
		return isInvestMoney;
	}

	/**
	 * @param BigDecimal
	 *            isInvestMoney (abc_invest_set.is_invest_money )
	 */
	public void setIsInvestMoney(BigDecimal isInvestMoney) {
		this.isInvestMoney = isInvestMoney;
	}

	/**
	 * @return abc_invest_set.is_sett_money
	 */
	public BigDecimal getIsSettMoney() {
		return isSettMoney;
	}

	/**
	 * @param BigDecimal
	 *            isSettMoney (abc_invest_set.is_sett_money )
	 */
	public void setIsSettMoney(BigDecimal isSettMoney) {
		this.isSettMoney = isSettMoney;
	}

	/**
	 * @return abc_invest_set.is_is_open
	 */
	public Integer getIsIsOpen() {
		return isIsOpen;
	}

	/**
	 * @param Integer
	 *            isIsOpen (abc_invest_set.is_is_open )
	 */
	public void setIsIsOpen(Integer isIsOpen) {
		this.isIsOpen = isIsOpen;
	}

	public Integer getIsLoanCategory() {
		return isLoanCategory;
	}

	public void setIsLoanCategory(Integer isLoanCategory) {
		this.isLoanCategory = isLoanCategory;
	}

}
