package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  ReviewOpLogDO
 *  abc_review_op_log
 */
public class ReviewOpLogDO {
    /**
     * 审核操作记录id
     * abc_review_op_log.rol_id
     */
    private Integer rolId;

    /**
     * 审核id
     * abc_review_op_log.rol_review_id
     */
    private Integer rolReviewId;

    /**
     * 审核者角色index
     * abc_review_op_log.rol_role_idx
     */
    private Integer rolRoleIdx;

    /**
     * 审核人id
     * abc_review_op_log.rol_emp_id
     */
    private Integer rolEmpId;

    /**
     * 审核操作 1通过 2否决 3打回 4挂起 5转移
     * abc_review_op_log.rol_op
     */
    private Integer rolOp;

    /**
     * 审核意见
     * abc_review_op_log.rol_msg
     */
    private String rolMsg;

    /**
     * 下一审核人角色index
     * abc_review_op_log.rol_next_role_idx
     */
    private Integer rolNextRoleIdx;

    /**
     * 下一审核人id
     * abc_review_op_log.rol_next_emp_id
     */
    private Integer rolNextEmpId;

    /**
     * 审核操作日期
     * abc_review_op_log.rol_date
     */
    private Date rolDate;

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public Integer getRolReviewId() {
        return rolReviewId;
    }

    public void setRolReviewId(Integer rolReviewId) {
        this.rolReviewId = rolReviewId;
    }

    public Integer getRolRoleIdx() {
        return rolRoleIdx;
    }

    public void setRolRoleIdx(Integer rolRoleIdx) {
        this.rolRoleIdx = rolRoleIdx;
    }

    public Integer getRolEmpId() {
        return rolEmpId;
    }

    public void setRolEmpId(Integer rolEmpId) {
        this.rolEmpId = rolEmpId;
    }

    public Integer getRolOp() {
        return rolOp;
    }

    public void setRolOp(Integer rolOp) {
        this.rolOp = rolOp;
    }

    public String getRolMsg() {
        return rolMsg;
    }

    public void setRolMsg(String rolMsg) {
        this.rolMsg = rolMsg;
    }

    public Integer getRolNextRoleIdx() {
        return rolNextRoleIdx;
    }

    public void setRolNextRoleIdx(Integer rolNextRoleIdx) {
        this.rolNextRoleIdx = rolNextRoleIdx;
    }

    public Integer getRolNextEmpId() {
        return rolNextEmpId;
    }

    public void setRolNextEmpId(Integer rolNextEmpId) {
        this.rolNextEmpId = rolNextEmpId;
    }

    public Date getRolDate() {
        return rolDate;
    }

    public void setRolDate(Date rolDate) {
        this.rolDate = rolDate;
    }
}
