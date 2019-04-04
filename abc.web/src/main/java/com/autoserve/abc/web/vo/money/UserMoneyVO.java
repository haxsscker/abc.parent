/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.money;

import java.math.BigDecimal;

/**
 * 类UserMoneyVO.java的实现描述：用户账余额vo
 * 
 * @author J.YL 2014年12月17日 上午11:43:01
 */
public class UserMoneyVO {
    /**
     * 用户名称
     */
    private String     userName;
    /**
     * 用户账户
     */
    private String     userAccountNo;
    /**
     * 账户总额
     */
    private BigDecimal totalMoney;
    /**
     * 账户可用余额
     */
    private BigDecimal useableMoney;
    /**
     * 账户冻结金额
     */
    private BigDecimal frozenMoney;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userAccountNo
     */
    public String getUserAccountNo() {
        return userAccountNo;
    }

    /**
     * @param userAccountNo the userAccountNo to set
     */
    public void setUserAccountNo(String userAccountNo) {
        this.userAccountNo = userAccountNo;
    }

    /**
     * @return the totalMoney
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * @param totalMoney the totalMoney to set
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * @return the useableMoney
     */
    public BigDecimal getUseableMoney() {
        return useableMoney;
    }

    /**
     * @param useableMoney the useableMoney to set
     */
    public void setUseableMoney(BigDecimal useableMoney) {
        this.useableMoney = useableMoney;
    }

    /**
     * @return the frozenMoney
     */
    public BigDecimal getFrozenMoney() {
        return frozenMoney;
    }

    /**
     * @param frozenMoney the frozenMoney to set
     */
    public void setFrozenMoney(BigDecimal frozenMoney) {
        this.frozenMoney = frozenMoney;
    }
}
