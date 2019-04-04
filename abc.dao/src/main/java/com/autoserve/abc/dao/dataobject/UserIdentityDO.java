/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

/**
 * 类UserIdentityDO.java的实现描述：查询DO
 * 
 * @author J.YL 2014年12月4日 下午8:06:26
 */
public class UserIdentityDO {
    private Integer userId;
    private Integer userType;
    private Integer accountCategory;
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public Integer getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(Integer accountCategory) {
		this.accountCategory = accountCategory;
	}

}
