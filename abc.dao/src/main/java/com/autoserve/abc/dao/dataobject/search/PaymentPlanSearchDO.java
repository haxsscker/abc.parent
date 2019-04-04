package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 还款计划搜索条件
 * 
 * @author zhangkang
 *
 */
public class PaymentPlanSearchDO {
	/**
	 * 项目名称
	 */
	private String loanNO;
	/**
	 * 应还日期-开始范围
	 */
	private Date payTime1;
	/**
	 * 应还日期-结束范围
	 */
	private Date payTime2;
	/**
	 * 还款状态 －1:未激活 0:未还清 1:付款中 2:已还清
	 */
	private List<Integer> payStates;
	/**
	 * 还款类型 1:正常还款 2:平台代还 3:强制还款
	 */
	private Integer payType;
	/**
	 * 担保机构Id
	 */
	private Integer govId;
	public String getLoanNO() {
		return loanNO;
	}

	public void setLoanNO(String loanNO) {
		this.loanNO = loanNO;
	}

	public Date getPayTime1() {
		return payTime1;
	}

	public void setPayTime1(Date payTime1) {
		this.payTime1 = payTime1;
	}

	public Date getPayTime2() {
		return payTime2;
	}

	public void setPayTime2(Date payTime2) {
		this.payTime2 = payTime2;
	}

	public List<Integer> getPayStates() {
		return payStates;
	}

	public void setPayStates(List<Integer> payStates) {
		this.payStates = payStates;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public Integer getGovId() {
		return govId;
	}

	public void setGovId(Integer govId) {
		this.govId = govId;
	}
}
