package com.autoserve.abc.dao.dataobject.pdfBean;

import java.math.BigDecimal;

public class InvestInformationDO {
    /**
     * 用户名
     */
    private String     userId;
    
    /**
     * 投资id
     */
    private Integer     investId;
    /**
     * 投资总额
     */
    private BigDecimal payTotalMoney;
    /**
     * 本金合计
     */
    private BigDecimal payCapital;
    
    /**
     * 利息合计
     */
    private BigDecimal payInterest;
    /**
     * 期限
     */
    private String     date;
    
    private String 	   userDocNo;
    
    private String 	   realName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getPayTotalMoney() {
        return payTotalMoney;
    }

    public void setPayTotalMoney(BigDecimal payTotalMoney) {
        this.payTotalMoney = payTotalMoney;
    }

    public BigDecimal getPayCapital() {
        return payCapital;
    }

    public void setPayCapital(BigDecimal payCapital) {
        this.payCapital = payCapital;
    }

	public String getUserDocNo() {
		return userDocNo;
	}

	public void setUserDocNo(String userDocNo) {
		this.userDocNo = userDocNo;
	}

	public BigDecimal getPayInterest() {
		return payInterest;
	}

	public void setPayInterest(BigDecimal payInterest) {
		this.payInterest = payInterest;
	}

	public Integer getInvestId() {
		return investId;
	}

	public void setInvestId(Integer investId) {
		this.investId = investId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
