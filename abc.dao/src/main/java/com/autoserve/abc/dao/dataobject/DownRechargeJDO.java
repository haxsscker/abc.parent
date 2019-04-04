package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 线下充值审核JDO
 */
public class DownRechargeJDO {
    /**
     * 记录主键id
     */
    private Integer    downRechargeId;
    /**
     * 充值人真实姓名
     */
    private String     userRealName;

    /**
     * 充值银行ID 外键mon_bank_info abc_down_recharge.dr_bank_id
     */
    private Integer    drBankId;

    /**
     * 充值银行卡号
     */
    private String     bankNo;

    /**
     * 充值日期
     */
    private Date       downRechargeDate;

    /**
     * 充值金额
     */
    private BigDecimal downRechargeMoney;

    /**
     * 审核状态 abc_down_recharge.dr_check_state
     */
    private Integer    downRechargeCheckState;

    /**
     * 审核人id 外键employee abc_down_recharge.dr_check_operator
     */
    private Integer    downRechargeCheckOperator;

    /**
     * 审核日期 abc_down_recharge.dr_check_date
     */
    private Date       downRechargeCheckDate;

    /**
     * 审核意见 abc_down_recharge.dr_check_idear
     */
    private String     downRechargeCheckIdear;

    /**
     * 影像资料dataID 非主键 关联file_upload_info表的data_id abc_down_recharge.dr_file_id
     */
    private String     downRechargeFileId;
    /**
     * 备注
     */
    private String     drMark;

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Integer getDrBankId() {
        return drBankId;
    }

    public void setDrBankId(Integer drBankId) {
        this.drBankId = drBankId;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Date getDownRechargeDate() {
        return downRechargeDate;
    }

    public void setDownRechargeDate(Date downRechargeDate) {
        this.downRechargeDate = downRechargeDate;
    }

    public BigDecimal getDownRechargeMoney() {
        return downRechargeMoney;
    }

    public void setDownRechargeMoney(BigDecimal downRechargeMoney) {
        this.downRechargeMoney = downRechargeMoney;
    }

    public Integer getDownRechargeCheckState() {
        return downRechargeCheckState;
    }

    public void setDownRechargeCheckState(Integer downRechargeCheckState) {
        this.downRechargeCheckState = downRechargeCheckState;
    }

    public Integer getDownRechargeCheckOperator() {
        return downRechargeCheckOperator;
    }

    public void setDownRechargeCheckOperator(Integer downRechargeCheckOperator) {
        this.downRechargeCheckOperator = downRechargeCheckOperator;
    }

    public Date getDownRechargeCheckDate() {
        return downRechargeCheckDate;
    }

    public void setDownRechargeCheckDate(Date downRechargeCheckDate) {
        this.downRechargeCheckDate = downRechargeCheckDate;
    }

    public String getDownRechargeCheckIdear() {
        return downRechargeCheckIdear;
    }

    public void setDownRechargeCheckIdear(String downRechargeCheckIdear) {
        this.downRechargeCheckIdear = downRechargeCheckIdear;
    }

    public String getDownRechargeFileId() {
        return downRechargeFileId;
    }

    public void setDownRechargeFileId(String downRechargeFileId) {
        this.downRechargeFileId = downRechargeFileId;
    }

    /**
     * @return the downRechargeId
     */
    public Integer getDownRechargeId() {
        return downRechargeId;
    }

    /**
     * @param downRechargeId the downRechargeId to set
     */
    public void setDownRechargeId(Integer downRechargeId) {
        this.downRechargeId = downRechargeId;
    }

    /**
     * @return the drMark
     */
    public String getDrMark() {
        return drMark;
    }

    /**
     * @param drMark the drMark to set
     */
    public void setDrMark(String drMark) {
        this.drMark = drMark;
    }

}
