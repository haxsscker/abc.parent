/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * 免费提现额度申请
 * 
 * @author zhangkang 2015年6月17日 上午11:50:41
 */
public class CashQuotaApplyDO {
    /**
     * 主键
     */
    private int        cqaId;
    /**
     * 申请人id（客服）
     */
    private int        cqaApplyUserId;
    /**
     * 申请人username
     */
    private String     cqaApplyUsername;
    /**
     * 用户id
     */
    private int        cqaUserId;
    /**
     * 用户username
     */
    private String     cqaUsername;
    /**
     * 原来免费提现
     */
    private BigDecimal cqaOldCashQuota;
    /**
     * 申请增加提现额度
     */
    private BigDecimal cqaApplyCashQuota;
    /**
     * 申请说明
     */
    private String     cqaInfo;
    /**
     * 状态1,待审核，2：审核通过，3：审核不通过
     */
    private Integer    cqaState;
    /**
     * 创建时间
     */
    private String     cqaCreateTime;
    /**
     * 审核时间
     */
    private Date       cqaAuditTime;
    /**
     * 审核意见
     */
    private String     cqaAuditOpinion;
    /**
     * 审核人id(财务)
     */
    private int        cqaAuditId;

    public int getCqaId() {
        return cqaId;
    }

    public void setCqaId(int cqaId) {
        this.cqaId = cqaId;
    }

    public int getCqaApplyUserId() {
        return cqaApplyUserId;
    }

    public void setCqaApplyUserId(int cqaApplyUserId) {
        this.cqaApplyUserId = cqaApplyUserId;
    }

    public String getCqaApplyUsername() {
        return cqaApplyUsername;
    }

    public void setCqaApplyUsername(String cqaApplyUsername) {
        this.cqaApplyUsername = cqaApplyUsername;
    }

    public int getCqaUserId() {
        return cqaUserId;
    }

    public void setCqaUserId(int cqaUserId) {
        this.cqaUserId = cqaUserId;
    }

    public String getCqaUsername() {
        return cqaUsername;
    }

    public void setCqaUsername(String cqaUsername) {
        this.cqaUsername = cqaUsername;
    }

    public BigDecimal getCqaOldCashQuota() {
        return cqaOldCashQuota;
    }

    public void setCqaOldCashQuota(BigDecimal cqaOldCashQuota) {
        this.cqaOldCashQuota = cqaOldCashQuota;
    }

    public BigDecimal getCqaApplyCashQuota() {
        return cqaApplyCashQuota;
    }

    public void setCqaApplyCashQuota(BigDecimal cqaApplyCashQuota) {
        this.cqaApplyCashQuota = cqaApplyCashQuota;
    }

    public String getCqaInfo() {
        return cqaInfo;
    }

    public void setCqaInfo(String cqaInfo) {
        this.cqaInfo = cqaInfo;
    }

    public Integer getCqaState() {
        return cqaState;
    }

    public void setCqaState(Integer cqaState) {
        this.cqaState = cqaState;
    }

    public String getCqaCreateTime() {
        return cqaCreateTime;
    }

    public void setCqaCreateTime(String cqaCreateTime) {
        this.cqaCreateTime = cqaCreateTime;
    }

    public Date getCqaAuditTime() {
        return cqaAuditTime;
    }

    public void setCqaAuditTime(Date cqaAuditTime) {
        this.cqaAuditTime = cqaAuditTime;
    }

    public String getCqaAuditOpinion() {
        return cqaAuditOpinion;
    }

    public void setCqaAuditOpinion(String cqaAuditOpinion) {
        this.cqaAuditOpinion = cqaAuditOpinion;
    }

    public int getCqaAuditId() {
        return cqaAuditId;
    }

    public void setCqaAuditId(int cqaAuditId) {
        this.cqaAuditId = cqaAuditId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
