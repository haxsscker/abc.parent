package com.autoserve.abc.web.vo.bank;

/**
 * 类MonBankInfoVO.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月24日 上午11:38:33
 */
public class MonBankInfoVO {

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

    /**
     * @return abc_mon_bank_info.mon_bank_id
     */
    public Integer getMonBankId() {
        return monBankId;
    }

    /**
     * @param Integer monBankId (abc_mon_bank_info.mon_bank_id )
     */
    public void setMonBankId(Integer monBankId) {
        this.monBankId = monBankId;
    }

    /**
     * @return abc_mon_bank_info.mon_fund_id
     */
    public Integer getMonFundId() {
        return monFundId;
    }

    /**
     * @param Integer monFundId (abc_mon_bank_info.mon_fund_id )
     */
    public void setMonFundId(Integer monFundId) {
        this.monFundId = monFundId;
    }

    /**
     * @return abc_mon_bank_info.fun_fund_name
     */
    public String getFunFundName() {
        return funFundName;
    }

    /**
     * @param String funFundName (abc_mon_bank_info.fun_fund_name )
     */
    public void setFunFundName(String funFundName) {
        this.funFundName = funFundName == null ? null : funFundName.trim();
    }

    /**
     * @return abc_mon_bank_info.mon_bank_name
     */
    public String getMonBankName() {
        return monBankName;
    }

    /**
     * @param String monBankName (abc_mon_bank_info.mon_bank_name )
     */
    public void setMonBankName(String monBankName) {
        this.monBankName = monBankName == null ? null : monBankName.trim();
    }

    /**
     * @return abc_mon_bank_info.mon_bank_card
     */
    public String getMonBankCard() {
        return monBankCard;
    }

    /**
     * @param String monBankCard (abc_mon_bank_info.mon_bank_card )
     */
    public void setMonBankCard(String monBankCard) {
        this.monBankCard = monBankCard == null ? null : monBankCard.trim();
    }

    /**
     * @return abc_mon_bank_info.mon_user_namer
     */
    public String getMonUserNamer() {
        return monUserNamer;
    }

    /**
     * @param String monUserNamer (abc_mon_bank_info.mon_user_namer )
     */
    public void setMonUserNamer(String monUserNamer) {
        this.monUserNamer = monUserNamer == null ? null : monUserNamer.trim();
    }
}
