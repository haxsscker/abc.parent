package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;

public class InvestSearchJDO {

    /**
     * 投资人ID/借款人ID
     */
    private int     userId;

    private Date    startDate;

    private Date    endDate;

    //add by 夏同同  20160518 计算当前日期之前的数据数目
    private Date    currentDate;
    

    /**
     * 最近时间 0： 7日 ，1:1月，2:2月，3:3月
     */
    private Integer Recent;

    /**
     * 项目名称
     */
    private String  loanName;

    /**
     * 项目状态(1、回款中 2、招标中 3、已结清)
     * 
     * @return
     */
    private int     loanState;
    
    /**
     * 是否移除被转让的收益计划记录（前台回款计划专用）
     */
    private boolean removeTransfer;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

	public boolean isRemoveTransfer() {
		return removeTransfer;
	}

	public void setRemoveTransfer(boolean removeTransfer) {
		this.removeTransfer = removeTransfer;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
    
}
