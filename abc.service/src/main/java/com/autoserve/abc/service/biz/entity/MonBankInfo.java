package com.autoserve.abc.service.biz.entity;

/**
 * @author dengjingyu 2014年12月23日 上午10:37:41
 */
public class MonBankInfo {
    /**
     * 主键id abc_mon_bank_info.mon_bank_id
     */
    private Integer monBankId;

    /**
     * 有限合伙id abc_mon_bank_info.mon_fund_id
     */
    private Integer monFundId;

    /**
     * 基金名称 abc_mon_bank_info.fun_fund_name
     */
    private String  funFundName;

    /**
     * 银行名称 abc_mon_bank_info.mon_bank_name
     */
    private String  monBankName;

    /**
     * 银行卡号 abc_mon_bank_info.mon_bank_card
     */
    private String  monBankCard;

    /**
     * 用户户名 abc_mon_bank_info.mon_user_namer
     */
    private String  monUserNamer;

    public Integer getMonBankId() {
        return monBankId;
    }

    public void setMonBankId(Integer monBankId) {
        this.monBankId = monBankId;
    }

    public Integer getMonFundId() {
        return monFundId;
    }

    public void setMonFundId(Integer monFundId) {
        this.monFundId = monFundId;
    }

    public String getFunFundName() {
        return funFundName;
    }

    public void setFunFundName(String funFundName) {
        this.funFundName = funFundName;
    }

    public String getMonBankName() {
        return monBankName;
    }

    public void setMonBankName(String monBankName) {
        this.monBankName = monBankName;
    }

    public String getMonBankCard() {
        return monBankCard;
    }

    public void setMonBankCard(String monBankCard) {
        this.monBankCard = monBankCard;
    }

    public String getMonUserNamer() {
        return monUserNamer;
    }

    public void setMonUserNamer(String monUserNamer) {
        this.monUserNamer = monUserNamer;
    }
}
