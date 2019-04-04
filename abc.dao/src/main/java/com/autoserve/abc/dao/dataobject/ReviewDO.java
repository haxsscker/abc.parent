package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * ReviewDO
 * abc_review
 */
public class ReviewDO {
    /**
     * 审核id
     * abc_review.review_id
     */
    private Integer reviewId;

    /**
     * 创建此审核的原始审核id，可为空
     * abc_review.review_origin_review_id
     */
    private Integer reviewOriginReviewId;

    /**
     * 审核类型，对应枚举类ReviewType
     * abc_review.review_type
     */
    private Integer reviewType;

    /**
     * 对应的申请id
     * abc_review.review_apply_id
     */
    private Integer reviewApplyId;

    /**
     * 简要的审核说明信息
     * abc_review.review_info
     */
    private String reviewInfo;

    /**
     * 审核是否挂起 0否 1是 默认0未挂起
     * abc_review.review_suspend
     */
    private Boolean reviewSuspend;

    /**
     * 审核的流程是否已结束 0否 1是 默认0未结束
     * abc_review.review_end
     */
    private Boolean reviewEnd;

    /**
     * 当前待审核角色index
     * abc_review.review_curr_role_id
     */
    private Integer reviewCurrRoleIdx;

    /**
     * 当前待审核人id
     * abc_review.review_curr_emp_id
     */
    private Integer reviewCurrEmpId;

    /**
     * 最后有效审核操作记录id
     * abc_review.review_last_op_log_id
     */
    private Integer reviewLastOpLogId;

    /**
     * 此审核相关的最后发送记录id
     * abc_review.review_last_send_log_id
     */
    private Integer reviewLastSendLogId;

    /**
     * 审核状态 0待审核 1已通过 2未通过
     * abc_review.review_state
     */
    private Integer reviewState;

    /**
     * 版本号，对审核的任何操作都会使其加1
     * abc_review.review_version
     */
    private Integer reviewVersion;

    /**
     * 创建时间
     * abc_review.review_create_time
     */
    private Date reviewCreateTime;

    /**
     * 更新时间
     * abc_review.review_update_time
     */
    private Date reviewUpdateTime;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public Integer getReviewApplyId() {
        return reviewApplyId;
    }

    public void setReviewApplyId(Integer reviewApplyId) {
        this.reviewApplyId = reviewApplyId;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public Boolean getReviewSuspend() {
        return reviewSuspend;
    }

    public void setReviewSuspend(Boolean reviewSuspend) {
        this.reviewSuspend = reviewSuspend;
    }

    public Boolean getReviewEnd() {
        return reviewEnd;
    }

    public void setReviewEnd(Boolean reviewEnd) {
        this.reviewEnd = reviewEnd;
    }

    public Integer getReviewCurrRoleIdx() {
        return reviewCurrRoleIdx;
    }
    
    public void setReviewCurrRoleIdx(Integer reviewCurrRoleIdx) {
        this.reviewCurrRoleIdx = reviewCurrRoleIdx;
    }
    
    public Integer getReviewCurrEmpId() {
        return reviewCurrEmpId;
    }

    
    public void setReviewCurrEmpId(Integer reviewCurrEmpId) {
        this.reviewCurrEmpId = reviewCurrEmpId;
    }
    
    public Integer getReviewLastOpLogId() {
        return reviewLastOpLogId;
    }
    
    public void setReviewLastOpLogId(Integer reviewLastOpLogId) {
        this.reviewLastOpLogId = reviewLastOpLogId;
    }

    public Integer getReviewState() {
        return reviewState;
    }

    public void setReviewState(Integer reviewState) {
        this.reviewState = reviewState;
    }

    public Integer getReviewOriginReviewId() {
        return reviewOriginReviewId;
    }

    public void setReviewOriginReviewId(Integer reviewOriginReviewId) {
        this.reviewOriginReviewId = reviewOriginReviewId;
    }

    public Integer getReviewLastSendLogId() {
        return reviewLastSendLogId;
    }

    public void setReviewLastSendLogId(Integer reviewLastSendLogId) {
        this.reviewLastSendLogId = reviewLastSendLogId;
    }

    public Integer getReviewVersion() {
        return reviewVersion;
    }

    public void setReviewVersion(Integer reviewVersion) {
        this.reviewVersion = reviewVersion;
    }

    public Date getReviewCreateTime() {
        return reviewCreateTime;
    }

    public void setReviewCreateTime(Date reviewCreateTime) {
        this.reviewCreateTime = reviewCreateTime;
    }

    public Date getReviewUpdateTime() {
        return reviewUpdateTime;
    }

    public void setReviewUpdateTime(Date reviewUpdateTime) {
        this.reviewUpdateTime = reviewUpdateTime;
    }
}
