package com.autoserve.abc.service.biz.entity;

public class BankMapping {
    /**
     * 主键id abc_bank_info.bank_id
     */
    private Integer  bankId;

    /**
     * 银行名称 abc_bank_info.bank_name
     */
    private String   bankName;

    /**
     * 銀行編碼
     */
    private String   bankCode;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
