package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 银行信息
 * 
 * @author J.YL 2014年11月15日 下午6:17:09
 */
public class BankInfoDO {
    /**
     * 主键id abc_bank_info.bank_id
     */
    private Integer bankId;

    /**
     * 用户id abc_bank_info.bank_user_id
     */
    private Integer bankUserId;

    /**
     * 用户类型 abc_bank_info.bank_user_type
     */
    private Integer bankUserType;

    /**
     * 银行名称 abc_bank_info.bank_name
     */
    private String  bankName;

    /**
     * 银行卡号 abc_bank_info.bank_no
     */
    private String  bankNo;

    /**
     * 法人账户姓名 abc_bank_info.bank_lawer
     */
    private String  bankLawer;
    /**
     * 開卡類型
     */
    private Integer     cardType;
    /**
     * 銀行編碼
     */
    private String  bankCode;
    /**
     * 绑卡日期
     */
    private Date  bindDate;
    /**
     * 卡状态：0不可用 1可用
     */
    private Integer  cardStatus;
    /**
     * 銀行地區編碼
     */
    private String  areaCode;
    
    /**
     * 银行卡所属账户类型
     * @return
     */
    private Integer bankAccountType;
    /**
     * 绑定手机
     */
    private String  bindMobile;
    
    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(Integer bankUserId) {
        this.bankUserId = bankUserId;
    }

    public Integer getBankUserType() {
        return bankUserType;
    }

    public void setBankUserType(Integer bankUserType) {
        this.bankUserType = bankUserType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankLawer() {
        return bankLawer;
    }

    public void setBankLawer(String bankLawer) {
        this.bankLawer = bankLawer;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Integer getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}
    

}
