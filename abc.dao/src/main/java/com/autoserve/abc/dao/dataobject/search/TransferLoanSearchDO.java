/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;
import java.util.List;

/**
 * 转让标查询条件
 *
 * @author segen189 2014年12月23日 下午4:17:56
 */
public class TransferLoanSearchDO{
	/**
	 * 原始项目id
	 */
	private Integer loanId;
    /**
     * 项目编号
     */
    private String  loanNo;

    /**
     * 转让人姓名
     */
    private String  transferUserName;

    /**
     * 转让金额区间开始
     */
    private Double  transferMoneyStart;

    /**
     * 转让金额区间结束
     */
    private Double  transferMoneyEnd;

    /**
     * 申请日期区间开始
     */
    private Date    applyDateStart;

    /**
     * 申请日期区间结束
     */
    private Date    applyDateEnd;

    /**
     * 项目分类
     */
    private Integer loanCategory;

    /**
     * 转让标状态
     */
    private Integer transferLoanState;
    /**
     * 转让标状态集合
     */
    private List<Integer> transferLoanStates;
    
    /**
	 * 投资人ID
	 */
	private int userId;
	
	/**
	 * 开始时间
	 */
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 最近时间  1： 7日 ，2: 1月，3:3月
 	 */
	private Integer Recent;
	
	/**
	 * 项目名称
	 */
	private String loanName;
	
	/**
	 * 项目状态(1、回款中  2、招标中  3、已结清)
	 * @return
	 */
	private int loanState;
    
    

    public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getTransferUserName() {
        return transferUserName;
    }

    public void setTransferUserName(String transferUserName) {
        this.transferUserName = transferUserName;
    }

    public Double getTransferMoneyStart() {
        return transferMoneyStart;
    }

    public void setTransferMoneyStart(Double transferMoneyStart) {
        this.transferMoneyStart = transferMoneyStart;
    }

    public Double getTransferMoneyEnd() {
        return transferMoneyEnd;
    }

    public void setTransferMoneyEnd(Double transferMoneyEnd) {
        this.transferMoneyEnd = transferMoneyEnd;
    }

    public Date getApplyDateStart() {
        return applyDateStart;
    }

    public void setApplyDateStart(Date applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public Date getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(Date applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Integer getTransferLoanState() {
        return transferLoanState;
    }

    public void setTransferLoanState(Integer transferLoanState) {
        this.transferLoanState = transferLoanState;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getRecent() {
		return Recent;
	}

	public void setRecent(Integer recent) {
		Recent = recent;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public int getLoanState() {
		return loanState;
	}

	public void setLoanState(int loanState) {
		this.loanState = loanState;
	}

	public List<Integer> getTransferLoanStates() {
		return transferLoanStates;
	}

	public void setTransferLoanStates(List<Integer> transferLoanStates) {
		this.transferLoanStates = transferLoanStates;
	}
    

}
