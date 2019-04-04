/**
 * Copyright (C) 2006-2012 Tuniu All rigimport java.math.BigDecimal;
import java.util.Date;
7 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DealRecordDO abc_deal_record
 */
public class DealRecordDO {
	/**
	 * 主键id abc_deal_record.dr_id
	 */
	private Integer drId;

	/**
	 * 资金调用流水号，通过其可以确定一个唯一的资金调用,暂时未使用。在第三方支付接口不支持批量操作时使用。
	 * abc_deal_record.dr_out_seq_no
	 */
	private String drOutSeqNo;

	/**
	 * 内部交易流水号，一个交易流水号对应多个资金调用流水号 abc_deal_record.dr_inner_seq_no
	 */
	private String drInnerSeqNo;

	/**
	 * 付款人账户id account_info表 abc_deal_record.dr_pay_account
	 */
	private String drPayAccount;

	/**
	 * 收款人账户id account_info abc_deal_record.dr_receive_account
	 */
	private String drReceiveAccount;

	/**
	 * 交易金钱数 abc_deal_record.dr_money_amount
	 */
	private BigDecimal drMoneyAmount;
	private String drMoneyAmountStr;

	/**
	 * 交易细则类型，如果没有则为交易类型(平台手续费、平台服务费、担保费、本金、利息等) abc_deal_record.dr_detail_type
	 */
	private Integer drDetailType;
	private String drDetailTypeStr;

	/**
	 * 交易类型(0投资，1还款，2收费，3资金划转，4退费) abc_deal_record.dr_type
	 */
	private Integer drType;
	private String drTypeStr;

	/**
	 * 交易类型对应的交易的业务类型，做外键使用 abc_deal_record.dr_business_id
	 */
	private Integer drBusinessId;

	/**
	 * 资金操作记录表id abc_deal_record.dr_cash_id
	 */
	private Integer drCashId;

	/**
	 * 交易状态,0 等待响应，1 成功 2 失败 abc_deal_record.dr_state
	 */
	private Integer drState;
	private String drStateStr;

	/**
	 * 交易创建日期 abc_deal_record.dr_operate_date
	 */
	private Date drOperateDate;
	private String drOperateDateStr;
	/**
	 * 交易操作人 abc_deal_record.dr_operator
	 */
	private Integer drOperator;

	// 交易对方
	private String drCustomerAccount;
	// 交易订单号
	private String drOrderNoStr;
	
	
	private Date startTradeDate;
	
	
	private Date endTradeDate;
	private String payUserName;
	private String payRealName;
	private String receiveUserName;
	private String receiveRealName;
	private String loanNo;
	
	public void newCreateDrTypeStr() {
		switch (this.getDrType()) {
		case 0:
			setDrTypeStr("投资冻结");
			break;
		case 1:
			setDrTypeStr("撤投");
			break;
		case 2:
			if (getDrDetailType() == 9) {
				setDrTypeStr("投资划转");
			}else if (getDrDetailType() == 5) {
				setDrTypeStr("平台服务费");
			}else if (getDrDetailType() == 12) {
				setDrTypeStr("担保服务费");
			} else if (getDrDetailType() == 14) {
				setDrTypeStr("转让手续费");
			} else if (getDrDetailType() == 13) {
				setDrTypeStr("转让划转");
			}else{
				setDrTypeStr("资金划转");
			}
			break;
		case 3:
			if (getDrDetailType() == 5) {
				setDrTypeStr("平台服务费");
			}else{
				setDrTypeStr("还款");
			}
			break;
		case 4:
			setDrTypeStr("充值");
			break;
		case 5:
			if (getDrDetailType() == 11) {
				setDrTypeStr("提现手续费");
			}else{
				setDrTypeStr("提现");
			}
			break;
		case 6:
			setDrTypeStr("退款");
			break;
		case 7:
			setDrTypeStr("收购");
			break;
		case 8:
			setDrTypeStr("流标退回金额");
			break;
		case 9:
			setDrTypeStr("自主转账");
			break;
		case 10:
			setDrTypeStr("红包返还");
			break;
		default:
			setDrTypeStr("-");
		}
	}
	/**
	 * 调用此方法设置：drTypeStr
	 * 
	 * @param accountNo
	 * @return
	 */
	public void createDrTypeStr(String accountNo) {
		switch (this.getDrType()) {
		case 0:
			setDrTypeStr("投资冻结");
			break;
		case 1:
			setDrTypeStr("撤投");
			break;
		case 2:
			/*if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 19) {
				setDrTypeStr("二次分配");
			} else*/ 
			if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 9) {
				setDrTypeStr("投资划转");
			}else if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 5) {
				setDrTypeStr("平台服务费");
			}else if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 12) {
				setDrTypeStr("担保服务费");
			} else if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 14) {
				setDrTypeStr("转让手续费");
			} else if (getDrReceiveAccount().equals(accountNo)) {
				if (getDrDetailType() == 13) {
					setDrTypeStr("转让");
				} else {
					if(getDrDetailType() == 16){
						setDrTypeStr("红包");
					}else{
						setDrTypeStr("借款");
					}
				}
			}else if (getDrPayAccount().equals(accountNo)) {
				setDrTypeStr("转让");
			}
			break;
		case 3:
			if (getDrPayAccount().equals(accountNo)
					&& getDrDetailType() == 5) {
				setDrTypeStr("平台服务费");
			}else if (getDrPayAccount().equals(accountNo) && getDrDetailType() != 11) {
				setDrTypeStr("还款");
			} else if (getDrReceiveAccount().equals(accountNo)) {
				setDrTypeStr("收款");
			}
			break;
		case 4:
			setDrTypeStr("充值");
			break;
		case 5:
			setDrTypeStr("提现");
			break;
		case 6:
			setDrTypeStr("退款");
			break;
		case 7:
			setDrTypeStr("收购");
			break;
		case 8:
			setDrTypeStr("流标退回金额");
			break;
		case 9:
			setDrTypeStr("自主转账");
			break;
		case 10:
			setDrTypeStr("红包返还");
			break;
		default:
			setDrTypeStr("-");
		}
	}

	public String getDrMoneyAmountStr() {
		return drMoneyAmountStr;
	}

	public void setDrMoneyAmountStr(String drMoneyAmountStr) {
		this.drMoneyAmountStr = drMoneyAmountStr;
	}

	public String getDrTypeStr() {
		return drTypeStr;
	}

	public void setDrTypeStr(String drTypeStr) {
		this.drTypeStr = drTypeStr;
	}
    
	public String getDrDetailTypeStr() {
		return drDetailTypeStr;
	}

	public void setDrDetailTypeStr(String drDetailTypeStr) {
		this.drDetailTypeStr = drDetailTypeStr;
	}

	public String getDrStateStr() {
		return drStateStr;
	}

	public void setDrStateStr(String drStateStr) {
		this.drStateStr = drStateStr;
	}

	public String getDrOperateDateStr() {
		return drOperateDateStr;
	}

	public void setDrOperateDateStr(String drOperateDateStr) {
		this.drOperateDateStr = drOperateDateStr;
	}

	public String getDrCustomerAccount() {
		return drCustomerAccount;
	}

	public void setDrCustomerAccount(String drCustomerAccount) {
		this.drCustomerAccount = drCustomerAccount;
	}

	public String getDrOrderNoStr() {
		return drOrderNoStr;
	}

	public void setDrOrderNoStr(String drOrderNoStr) {
		this.drOrderNoStr = drOrderNoStr;
	}

	public Integer getDrId() {
		return drId;
	}

	public void setDrId(Integer drId) {
		this.drId = drId;
	}

	public String getDrOutSeqNo() {
		return drOutSeqNo;
	}

	public void setDrOutSeqNo(String drOutSeqNo) {
		this.drOutSeqNo = drOutSeqNo;
	}

	public String getDrInnerSeqNo() {
		return drInnerSeqNo;
	}

	public void setDrInnerSeqNo(String drInnerSeqNo) {
		this.drInnerSeqNo = drInnerSeqNo;
	}

	public String getDrPayAccount() {
		return drPayAccount;
	}

	public void setDrPayAccount(String drPayAccount) {
		this.drPayAccount = drPayAccount;
	}

	public String getDrReceiveAccount() {
		return drReceiveAccount;
	}

	public void setDrReceiveAccount(String drReceiveAccount) {
		this.drReceiveAccount = drReceiveAccount;
	}

	public BigDecimal getDrMoneyAmount() {
		return drMoneyAmount;
	}

	public void setDrMoneyAmount(BigDecimal drMoneyAmount) {
		this.drMoneyAmount = drMoneyAmount;
	}

	public Integer getDrDetailType() {
		return drDetailType;
	}

	public void setDrDetailType(Integer drDetailType) {
		this.drDetailType = drDetailType;
	}

	public Integer getDrType() {
		return drType;
	}

	public void setDrType(Integer drType) {
		this.drType = drType;
	}

	public Integer getDrBusinessId() {
		return drBusinessId;
	}

	public void setDrBusinessId(Integer drBusinessId) {
		this.drBusinessId = drBusinessId;
	}

	public Integer getDrCashId() {
		return drCashId;
	}

	public void setDrCashId(Integer drCashId) {
		this.drCashId = drCashId;
	}

	public Integer getDrState() {
		return drState;
	}

	public void setDrState(Integer drState) {
		this.drState = drState;
	}

	public Date getDrOperateDate() {
		return drOperateDate;
	}

	public void setDrOperateDate(Date drOperateDate) {
		this.drOperateDate = drOperateDate;
	}

	public Integer getDrOperator() {
		return drOperator;
	}

	public void setDrOperator(Integer drOperator) {
		this.drOperator = drOperator;
	}

	public Date getStartTradeDate() {
		return startTradeDate;
	}

	public void setStartTradeDate(Date startTradeDate) {
		this.startTradeDate = startTradeDate;
	}

	public Date getEndTradeDate() {
		return endTradeDate;
	}

	public void setEndTradeDate(Date endTradeDate) {
		this.endTradeDate = endTradeDate;
	}

	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}

	public String getPayRealName() {
		return payRealName;
	}

	public void setPayRealName(String payRealName) {
		this.payRealName = payRealName;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveRealName() {
		return receiveRealName;
	}

	public void setReceiveRealName(String receiveRealName) {
		this.receiveRealName = receiveRealName;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	
}
