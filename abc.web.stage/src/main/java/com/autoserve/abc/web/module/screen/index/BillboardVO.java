package com.autoserve.abc.web.module.screen.index;

import java.math.BigDecimal;

public class BillboardVO {
    //排名
    private Integer    number;
    //用户名
    private String     username;
    //投资金额
    private BigDecimal inInvestMoney;
    /**
     * 格式化后的投资金额
     */
    private String     formatInInvestMoney;

    public String getFormatInInvestMoney() {
        return formatInInvestMoney;
    }

    public void setFormatInInvestMoney(String formatInInvestMoney) {
        this.formatInInvestMoney = formatInInvestMoney;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getInInvestMoney() {
        return inInvestMoney;
    }

    public void setInInvestMoney(BigDecimal inInvestMoney) {
        this.inInvestMoney = inInvestMoney;
    }

}
