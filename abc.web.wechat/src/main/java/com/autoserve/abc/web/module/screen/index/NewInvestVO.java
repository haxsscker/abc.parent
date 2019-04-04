package com.autoserve.abc.web.module.screen.index;

import java.math.BigDecimal;
import java.util.Date;

public class NewInvestVO {
	//投资时间
	 private Date createtime;
	//姓名
	 private String name;
	 //标题
	 private String loanno;
	// 投资金额
	 private BigDecimal  investMoney;
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoanno() {
		return loanno;
	}
	public void setLoanno(String loanno) {
		this.loanno = loanno;
	}
	public BigDecimal getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}
}
