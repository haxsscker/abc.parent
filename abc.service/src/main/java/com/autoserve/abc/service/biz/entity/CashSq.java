package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CashSq implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3386151974478525909L;

	
	
    /**
     * 主键 abc_cash_sq.user_id
     */
    
    private Integer    Id;
    
    
    private Integer    userId;

    
	
    /**
     * 申请时间   
     */
    private Date    sqtime;
	
    /**
     * 申请额度
     */
    private BigDecimal    userCashQuotaSqadd;
	
    /**
     * 审核意见
     */
    private String    shyj;
	
    /**
     * 通过额度
     */
    private BigDecimal    userCashQuotaShadd;

    /**
     * 状态
     */
    private Integer    state;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getSqtime() {
		return sqtime;
	}

	public void setSqtime(Date sqtime) {
		this.sqtime = sqtime;
	}

	public BigDecimal getUserCashQuotaSqadd() {
		return userCashQuotaSqadd;
	}

	public void setUserCashQuotaSqadd(BigDecimal userCashQuotaSqadd) {
		this.userCashQuotaSqadd = userCashQuotaSqadd;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public BigDecimal getUserCashQuotaShadd() {
		return userCashQuotaShadd;
	}

	public void setUserCashQuotaShadd(BigDecimal userCashQuotaShadd) {
		this.userCashQuotaShadd = userCashQuotaShadd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
    
}
