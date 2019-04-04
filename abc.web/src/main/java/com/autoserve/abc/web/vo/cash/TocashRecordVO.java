package com.autoserve.abc.web.vo.cash;

import java.math.BigDecimal;

/**
 * 提现记录查询VO
 * 
 * @author liuwei 2014年12月23日 下午5:21:01
 */
public class TocashRecordVO {

    private String     cust_name;
    private String     cust_fullname;
    private String     extcash_numberofbank;
    private String     extcash_bank;
    private BigDecimal extcash_money;
    private String     extcash_date;
    private Integer    extcash_state;

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_fullname() {
        return cust_fullname;
    }

    public void setCust_fullname(String cust_fullname) {
        this.cust_fullname = cust_fullname;
    }

    public String getExtcash_numberofbank() {
        return extcash_numberofbank;
    }

    public void setExtcash_numberofbank(String extcash_numberofbank) {
        this.extcash_numberofbank = extcash_numberofbank;
    }

    public String getExtcash_bank() {
        return extcash_bank;
    }

    public void setExtcash_bank(String extcash_bank) {
        this.extcash_bank = extcash_bank;
    }

    public BigDecimal getExtcash_money() {
        return extcash_money;
    }

    public void setExtcash_money(BigDecimal extcash_money) {
        this.extcash_money = extcash_money;
    }

    public String getExtcash_date() {
        return extcash_date;
    }

    public void setExtcash_date(String extcash_date) {
        this.extcash_date = extcash_date;
    }

    public Integer getExtcash_state() {
        return extcash_state;
    }

    public void setExtcash_state(Integer extcash_state) {
        this.extcash_state = extcash_state;
    }

}
