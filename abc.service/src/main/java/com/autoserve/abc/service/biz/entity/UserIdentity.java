/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.UserType;

/**
 * 类UserIdentity.java的实现描述：用户身份标识
 * 
 * @author J.YL 2014年12月4日 下午1:56:33
 */
public class UserIdentity {
    private Integer  userId;
    private UserType userType;
    private Integer accountCategory;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

	public Integer getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(Integer accountCategory) {
		this.accountCategory = accountCategory;
	}

}
