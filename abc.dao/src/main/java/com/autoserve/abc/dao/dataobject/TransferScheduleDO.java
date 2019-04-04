/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Jan 06 14:27:21 CST 2015
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * TransferScheduleDO abc_transfer_schedule
 */
public class TransferScheduleDO {
    /**
     * abc_transfer_schedule.ts_id
     */
    private Integer tsId;

    /**
     * 环节名称
     * abc_transfer_schedule.ts_name
     */
    private Integer tsName;

    /**
     * 操作人
     * abc_transfer_schedule.ts_operator_id
     */
    private Integer tsOperatorId;

    /**
     * 操作人名称
     */
    private String  operator;

    /**
     * 操作时间
     * abc_transfer_schedule.ts_operate_time
     */
    private Date    tsOperateTime;

    /**
     * 标类型
     * abc_transfer_schedule.ts_state
     */
    private Integer tsState;

    /**
     * 原始表
     * abc_transfer_schedule.ts_loan_id
     */
    private Integer tsLoanId;

    /**
     * 转让标
     * abc_transfer_schedule.ts_transfer_loan_id
     */
    private Integer tsTransferLoanId;

    /**
     * @return abc_transfer_schedule.ts_id
     */
    public Integer getTsId() {
        return tsId;
    }

    /**
     * @param Integer tsId (abc_transfer_schedule.ts_id )
     */
    public void setTsId(Integer tsId) {
        this.tsId = tsId;
    }

    /**
     * @return abc_transfer_schedule.ts_operator_id
     */
    public Integer getTsOperatorId() {
        return tsOperatorId;
    }

    /**
     * @param Integer tsOperatorId (abc_transfer_schedule.ts_operator_id )
     */
    public void setTsOperatorId(Integer tsOperatorId) {
        this.tsOperatorId = tsOperatorId;
    }

    /**
     * @return abc_transfer_schedule.ts_operate_time
     */
    public Date getTsOperateTime() {
        return tsOperateTime;
    }

    /**
     * @param Date tsOperateTime (abc_transfer_schedule.ts_operate_time )
     */
    public void setTsOperateTime(Date tsOperateTime) {
        this.tsOperateTime = tsOperateTime;
    }

    /**
     * @return abc_transfer_schedule.ts_state
     */
    public Integer getTsState() {
        return tsState;
    }

    /**
     * @param Integer tsState (abc_transfer_schedule.ts_state )
     */
    public void setTsState(Integer tsState) {
        this.tsState = tsState;
    }

    /**
     * @return abc_transfer_schedule.ts_loan_id
     */
    public Integer getTsLoanId() {
        return tsLoanId;
    }

    /**
     * @param Integer tsLoanId (abc_transfer_schedule.ts_loan_id )
     */
    public void setTsLoanId(Integer tsLoanId) {
        this.tsLoanId = tsLoanId;
    }

    /**
     * @return abc_transfer_schedule.ts_transfer_loan_id
     */
    public Integer getTsTransferLoanId() {
        return tsTransferLoanId;
    }

    /**
     * @param Integer tsTransferLoanId
     *            (abc_transfer_schedule.ts_transfer_loan_id )
     */
    public void setTsTransferLoanId(Integer tsTransferLoanId) {
        this.tsTransferLoanId = tsTransferLoanId;
    }

    public Integer getTsName() {
        return tsName;
    }

    public void setTsName(Integer tsName) {
        this.tsName = tsName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
