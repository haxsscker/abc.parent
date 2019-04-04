/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Jan 06 14:27:21 CST 2015
 * Description:
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.ScheduleStage;
import com.autoserve.abc.service.biz.enums.TransferLoanState;

/**
 * TransferScheduleDO abc_transfer_schedule
 */
public class TransferSchedule {
    /**
     * abc_transfer_schedule.ts_id
     */
    private Integer           id;

    /**
     * abc_transfer_schedule.ts_name
     */
    private ScheduleStage     segment;

    /**
     * 环节名称
     */
    private String            name;

    /**
     * abc_transfer_schedule.ts_operator_id
     */
    private Integer           operatorId;
    /**
     * abc_transfer_schedule.ts_operator_id
     */
    private String            operator;

    /**
     * abc_transfer_schedule.ts_operate_time
     */
    private Date              operateTime;
    /**
     * 时间格式转换
     */
    private String            strOperateTime;

    /**
     * abc_transfer_schedule.ts_state
     */
    private TransferLoanState state;

    /**
     * 状态名称
     */
    private String            stateName;

    /**
     * abc_transfer_schedule.ts_loan_id
     */
    private Integer           loanId;

    /**
     * abc_transfer_schedule.ts_transfer_loan_id
     */
    private Integer           transferLoanId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ScheduleStage getSegment() {
        return segment;
    }

    public void setSegment(ScheduleStage segment) {
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public TransferLoanState getState() {
        return state;
    }

    public void setState(TransferLoanState state) {
        this.state = state;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getTransferLoanId() {
        return transferLoanId;
    }

    public void setTransferLoanId(Integer transferLoanId) {
        this.transferLoanId = transferLoanId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStrOperateTime() {
        return strOperateTime;
    }

    public void setStrOperateTime(String strOperateTime) {
        this.strOperateTime = strOperateTime;
    }

}
