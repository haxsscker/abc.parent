package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-17,11:00
 */
public class Review {
    /**
     * 主键ID
     */
    private Integer reviewId;

    /**
     * 原始审核
     * 例如，一个意向审核通过后会进入项目初审，则这个项目初审的原始审核为此意向审核
     */
    private Review originReview;

    /**
     * 审核类型
     */
    private ReviewType type;

    /**
     * 相关申请ID
     * 注意：applyID为相关审核类型的申请表的主键，
     * ReviewType和applyID两个值可以唯一确定一条审核信息
     * 例如：当审核类型ReviewType为项目初审时，applyID就是项目表Loan的主键ID，
     * 当审核类型为转让初审时，applyID即是转让表TransferLoan的主键ID
     */
    private Integer applyId;

    private String info;

    private Boolean suspend;

    /**
     * 该审核是否已结束
     * 注：对任意的审核进行同意、否决都会使得该审核流程结束
     */
    private Boolean end;

    /**
     * 当前待审人的角色
     */
    private BaseRoleType currRole;

    /**
     * 当前待审人的员工ID
     */
    private Employee currEmp;

    /**
     * 审核状态
     */
    private ReviewState state;

    /**
     * 版本号，对审核的任何操作都会使其加1
     */
    private Integer version;

    /**
     * 该审核的最后一条有效审核操作信息
     */
    private ReviewOp lastOp;

    /**
     * 此审核相关的最后发送记录id
     */
    private ReviewSendLog lastSendLog;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Review() {
    }

    public Review(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Review(ReviewType type, int applyId) {
        this.type = type;
        this.applyId = applyId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public ReviewType getType() {
        return type;
    }

    public void setType(ReviewType type) {
        this.type = type;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean isSuspend() {
        return suspend;
    }

    public void setSuspend(Boolean suspend) {
        this.suspend = suspend;
    }

    public Boolean isEnd() {
        return end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public BaseRoleType getCurrRole() {
        return currRole;
    }

    public void setCurrRole(BaseRoleType currRole) {
        this.currRole = currRole;
    }

    public Employee getCurrEmp() {
        return currEmp;
    }

    public void setCurrEmp(Employee currEmp) {
        this.currEmp = currEmp;
    }

    public ReviewOp getLastOp() {
        return lastOp;
    }

    public void setLastOp(ReviewOp lastOp) {
        this.lastOp = lastOp;
    }

    public ReviewState getState() {
        return state;
    }

    public void setState(ReviewState state) {
        this.state = state;
    }

    public Review getOriginReview() {
        return originReview;
    }

    public void setOriginReview(Review originReview) {
        this.originReview = originReview;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ReviewSendLog getLastSendLog() {
        return lastSendLog;
    }

    public void setLastSendLog(ReviewSendLog lastSendLog) {
        this.lastSendLog = lastSendLog;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
