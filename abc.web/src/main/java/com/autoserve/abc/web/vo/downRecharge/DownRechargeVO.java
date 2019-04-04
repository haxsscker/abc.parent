/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.downRecharge;

import java.math.BigDecimal;

/**
 * 线下充值审核展示VO
 * 
 * @author J.YL 2015年1月8日 下午5:29:55
 */
public class DownRechargeVO {

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
    private String     downRechargeDate;

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
    private String     downRechargeCheckDate;

    /**
     * 审核意见 abc_down_recharge.dr_check_idear
     */
    private String     downRechargeCheckIdear;

    /**
     * 影像资料dataID 非主键 关联file_upload_info表的data_id abc_down_recharge.dr_file_id
     */
    private String     downRechargeFileId;

    /**
     * 是否可审核
     */
    private boolean    canCheck;
    /**
     * 备注
     */
    private String     drMark;

    /**
     * @return the userRealName
     */
    public String getUserRealName() {
        return userRealName;
    }

    /**
     * @param userRealName the userRealName to set
     */
    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    /**
     * @return the drBankId
     */
    public Integer getDrBankId() {
        return drBankId;
    }

    /**
     * @param drBankId the drBankId to set
     */
    public void setDrBankId(Integer drBankId) {
        this.drBankId = drBankId;
    }

    /**
     * @return the bankNo
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * @param bankNo the bankNo to set
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    /**
     * @return the downRechargeDate
     */
    public String getDownRechargeDate() {
        return downRechargeDate;
    }

    /**
     * @param downRechargeDate the downRechargeDate to set
     */
    public void setDownRechargeDate(String downRechargeDate) {
        this.downRechargeDate = downRechargeDate;
    }

    /**
     * @return the downRechargeMoney
     */
    public BigDecimal getDownRechargeMoney() {
        return downRechargeMoney;
    }

    /**
     * @param downRechargeMoney the downRechargeMoney to set
     */
    public void setDownRechargeMoney(BigDecimal downRechargeMoney) {
        this.downRechargeMoney = downRechargeMoney;
    }

    /**
     * @return the downRechargeCheckState
     */
    public Integer getDownRechargeCheckState() {
        return downRechargeCheckState;
    }

    /**
     * @param downRechargeCheckState the downRechargeCheckState to set
     */
    public void setDownRechargeCheckState(Integer downRechargeCheckState) {
        this.downRechargeCheckState = downRechargeCheckState;
    }

    /**
     * @return the downRechargeCheckOperator
     */
    public Integer getDownRechargeCheckOperator() {
        return downRechargeCheckOperator;
    }

    /**
     * @param downRechargeCheckOperator the downRechargeCheckOperator to set
     */
    public void setDownRechargeCheckOperator(Integer downRechargeCheckOperator) {
        this.downRechargeCheckOperator = downRechargeCheckOperator;
    }

    /**
     * @return the downRechargeCheckDate
     */
    public String getDownRechargeCheckDate() {
        return downRechargeCheckDate;
    }

    /**
     * @param downRechargeCheckDate the downRechargeCheckDate to set
     */
    public void setDownRechargeCheckDate(String downRechargeCheckDate) {
        this.downRechargeCheckDate = downRechargeCheckDate;
    }

    /**
     * @return the downRechargeCheckIdear
     */
    public String getDownRechargeCheckIdear() {
        return downRechargeCheckIdear;
    }

    /**
     * @param downRechargeCheckIdear the downRechargeCheckIdear to set
     */
    public void setDownRechargeCheckIdear(String downRechargeCheckIdear) {
        this.downRechargeCheckIdear = downRechargeCheckIdear;
    }

    /**
     * @return the downRechargeFileId
     */
    public String getDownRechargeFileId() {
        return downRechargeFileId;
    }

    /**
     * @param downRechargeFileId the downRechargeFileId to set
     */
    public void setDownRechargeFileId(String downRechargeFileId) {
        this.downRechargeFileId = downRechargeFileId;
    }

    /**
     * @return the canCheck
     */
    public boolean isCanCheck() {
        return canCheck;
    }

    /**
     * @param canCheck the canCheck to set
     */
    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
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
