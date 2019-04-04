/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

/**
 * 用户账户资金详情entity
 * 
 * @author J.YL 2014年11月26日 上午10:28:58
 */
public class UserAccountMoney {
    /**
     * 可用余额
     */
    private BigDecimal useableMoney;
    /**
     * 冻结金额
     */
    private BigDecimal frozeenMoney;
    /**
     * 总额
     */
    private BigDecimal totalMoney;

    public BigDecimal getUseableMoney() {
        return useableMoney;
    }

    public void setUseableMoney(BigDecimal useableMoney) {
        this.useableMoney = useableMoney;
    }

    public BigDecimal getFrozeenMoney() {
        return frozeenMoney;
    }

    public void setFrozeenMoney(BigDecimal frozeenMoney) {
        this.frozeenMoney = frozeenMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

}
