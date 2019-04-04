package com.autoserve.abc.dao.dataobject;


/**
 * 银行编码信息
 * 
 * @author LZ
 */
public class BankMappingDO {
    /**
     * 主键id
     */
    private Integer bankId;

    /**
     * 银行名称
     */
    private String  bankName;

  
    /**
     * 銀行編碼
     */
    private String  bankCode;

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
