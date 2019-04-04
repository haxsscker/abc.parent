package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewOpType;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-19,17:47
 */
public class ReviewOp {
    private int opLogId;

    private ReviewOpType opType;

    /**
     * 审核操作人ID
     */
    private Employee employee;

    /**
     * 审核操作为“转发”时，此属性用于设置转发到的审核角色
     * 注意：审核操作为“打回”时，此字段不需要显式地指定，系统将自动指定正确的值
     */
    private BaseRoleType  nextRole;

    /**
     * 审核操作为“转发”时，此属性用于设置转发到的审核人
     * 注意：审核操作为“打回”时，此字段不需要显式地指定，系统将自动指定正确的值
     */
    private Employee nextEmp;

    /**
     * 审核操作的附加信息
     */
    private String message;

    /**
     * 审核时间
     */
    private Date date;

    /**
     * 此审核操作相关的Review对象
     * 注意: 调用ReviewOperationService对审核进行操作时，可以不用设置此字段
     */
    private Review review;

    /**
     * 当前审核人角色
     * 注意: 调用ReviewOperationService对审核进行操作时，可以不用设置此字段
     */
    private BaseRoleType  role;

    public ReviewOp() {
    }

    public ReviewOp(int opLogId) {
        this.opLogId = opLogId;
    }

    public int getOpLogId() {
        return opLogId;
    }

    public void setOpLogId(int opLogId) {
        this.opLogId = opLogId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public BaseRoleType getRole() {
        return role;
    }

    public void setRole(BaseRoleType role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ReviewOpType getOpType() {
        return opType;
    }

    public void setOpType(ReviewOpType opType) {
        this.opType = opType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BaseRoleType getNextRole() {
        return nextRole;
    }

    public void setNextRole(BaseRoleType nextRole) {
        this.nextRole = nextRole;
    }

    public Employee getNextEmp() {
        return nextEmp;
    }

    public void setNextEmp(Employee nextEmp) {
        this.nextEmp = nextEmp;
    }
}
