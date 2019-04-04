package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 线下充值审核DO
 */
public class DownRechargeDO {
    /**
     * 主键 abc_down_recharge.dr_id
     */
    private Integer    drId;

    /**
     * 充值人id 外键 user表 abc_down_recharge.dr_user_id
     */
    private Integer    drUserId;

    /**
     * 充值银行ID 外键mon_bank_info abc_down_recharge.dr_bank_id
     */
    private Integer    drBankId;

    /**
     * 充值银行卡号 abc_down_recharge.dr_bank_no
     */
    private String     drBankNo;

    /**
     * 充值日期 abc_down_recharge.dr_date
     */
    private Date       drDate;

    /**
     * 充值金额 abc_down_recharge.dr_money
     */
    private BigDecimal drMoney;

    /**
     * 审核状态 abc_down_recharge.dr_check_state
     */
    private Integer    drCheckState;

    /**
     * 审核人id 外键employee abc_down_recharge.dr_check_operator
     */
    private Integer    drCheckOperator;

    /**
     * 审核日期 abc_down_recharge.dr_check_date
     */
    private Date       drCheckDate;

    /**
     * 审核意见 abc_down_recharge.dr_check_idear
     */
    private String     drCheckIdear;

    /**
     * 影像资料dataID 非主键 关联file_upload_info表的data_id abc_down_recharge.dr_file_id
     */
    private String     drFileId;

    /**
     * 备注
     */
    private String     drMark;

    public Integer getDrId() {
        return drId;
    }

    public void setDrId(Integer drId) {
        this.drId = drId;
    }

    public Integer getDrUserId() {
        return drUserId;
    }

    public void setDrUserId(Integer drUserId) {
        this.drUserId = drUserId;
    }

    public Integer getDrBankId() {
        return drBankId;
    }

    public void setDrBankId(Integer drBankId) {
        this.drBankId = drBankId;
    }

    public String getDrBankNo() {
        return drBankNo;
    }

    public void setDrBankNo(String drBankNo) {
        this.drBankNo = drBankNo == null ? null : drBankNo.trim();
    }

    public Date getDrDate() {
        return drDate;
    }

    public void setDrDate(Date drDate) {
        this.drDate = drDate;
    }

    public BigDecimal getDrMoney() {
        return drMoney;
    }

    public void setDrMoney(BigDecimal drMoney) {
        this.drMoney = drMoney;
    }

    public Integer getDrCheckState() {
        return drCheckState;
    }

    public void setDrCheckState(Integer drCheckState) {
        this.drCheckState = drCheckState;
    }

    public Integer getDrCheckOperator() {
        return drCheckOperator;
    }

    public void setDrCheckOperator(Integer drCheckOperator) {
        this.drCheckOperator = drCheckOperator;
    }

    public Date getDrCheckDate() {
        return drCheckDate;
    }

    public void setDrCheckDate(Date drCheckDate) {
        this.drCheckDate = drCheckDate;
    }

    public String getDrCheckIdear() {
        return drCheckIdear;
    }

    public void setDrCheckIdear(String drCheckIdear) {
        this.drCheckIdear = drCheckIdear == null ? null : drCheckIdear.trim();
    }

    public String getDrFileId() {
        return drFileId;
    }

    public void setDrFileId(String drFileId) {
        this.drFileId = drFileId == null ? null : drFileId.trim();
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
