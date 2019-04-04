/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.util.List;

/**
 * 投资查询条件
 * 
 * @author segen189 2015年1月1日 下午1:26:21
 */
public class InvestSearchDO extends InvestSearchJDO {
	/**
     * 投资id
     */
    Integer       investId;
	
	/**
     * 标id
     */
    Integer       bidId;

    /**
     * 标类型
     */
    Integer       bidType;

    /**
     * 原始贷款id
     */
    Integer       originId;

    /**
     * 投资人id
     */
    Integer       investUserId;

    /**
     * 投资状态集合
     */
    List<Integer> investStates;

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }
    
    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Integer getBidType() {
        return bidType;
    }

    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getInvestUserId() {
        return investUserId;
    }

    public void setInvestUserId(Integer investUserId) {
        this.investUserId = investUserId;
    }

    public List<Integer> getInvestStates() {
        return investStates;
    }

    public void setInvestStates(List<Integer> investStates) {
        this.investStates = investStates;
    }
}
