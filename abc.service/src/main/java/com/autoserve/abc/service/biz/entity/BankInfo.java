package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;

public class BankInfo {
    /**
     * 主键id abc_bank_info.bank_id
     */
    private Integer  bankId;

    /**
     * 用户id abc_bank_info.bank_user_id
     */
    private Integer  bankUserId;

    /**
     * 用户类型 abc_bank_info.bank_user_type
     */
    private Integer  bankUserType;

    /**
     * 银行名称 abc_bank_info.bank_name
     */
    private String   bankName;

    /**
     * 银行卡号 abc_bank_info.bank_no
     */
    private String   bankNo;

    /**
     * 法人账户姓名 abc_bank_info.bank_lawer
     */
    private String   bankLawer;
    /**
     * 開卡類型
     */
    private CardType cardType;
    /**
     * 銀行編碼
     */
    private String   bankCode;
    /**
     * 绑卡日期
     */
    private Date  bindDate;
    /**
     * 卡状态：0不可用 1可用
     */
    private CardStatus  cardStatus;
    /**
     * 銀行地區編碼
     */
    private String   areaCode;
    
    
    /**
     * 银行卡所属账户类型
     * @return
     */
    private Integer bankAccountType;
    
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

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
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

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Integer getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

}
