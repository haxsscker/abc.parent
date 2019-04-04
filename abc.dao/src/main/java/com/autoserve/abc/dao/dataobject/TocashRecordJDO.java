package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 提现记录和用户联合DO
 * 
 * @author liuwei 2014年12月23日 下午3:36:09
 */
public class TocashRecordJDO {

    /**
     * 名称
     */
    private String     custName;
    /**
     * 名称
     */
    private String     custFullName;
    /**
     * 提现账号
     */
    private String     extcashNumberofBank;
    /**
     * 提现银行
     */
    private String     extcashBank;
    /**
     * 提现金额
     */
    private BigDecimal extcashMoney;
    /**
     * 提现时间
     */
    private String     extcashDate;
    /**
     * 状态
     */
    private Integer    extcashState;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustFullName() {
        return custFullName;
    }

    public void setCustFullName(String custFullName) {
        this.custFullName = custFullName;
    }

    public String getExtcashNumberofBank() {
        return extcashNumberofBank;
    }

    public void setExtcashNumberofBank(String extcashNumberofBank) {
        this.extcashNumberofBank = extcashNumberofBank;
    }

    public String getExtcashBank() {
        return extcashBank;
    }

    public void setExtcashBank(String extcashBank) {
        this.extcashBank = extcashBank;
    }

    public BigDecimal getExtcashMoney() {
        return extcashMoney;
    }

    public void setExtcashMoney(BigDecimal extcashMoney) {
        this.extcashMoney = extcashMoney;
    }

    public String getExtcashDate() {
        return extcashDate;
    }

    public void setExtcashDate(String extcashDate) {
        this.extcashDate = extcashDate;
    }

    public Integer getExtcashState() {
        return extcashState;
    }

    public void setExtcashState(Integer extcashState) {
        this.extcashState = extcashState;
    }

}
