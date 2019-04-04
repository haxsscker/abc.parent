package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-30,18:56
 */
public class ReviewSendLogDO {
    /**
     * 主键
     * abc_review_send_log.rsl_id
     */
    private Integer rslId;

    /**
     * 相关的ReviewID
     * abc_review_send_log.rsl_review_id
     */
    private Integer rslReviewId;

    /**
     * 此次发送时相关审核的版本号
     * abc_review_send_log.rsl_review_version
     */
    private Integer rslReviewVersion;

    /**
     * 上一条发送记录ID
     * abc_review_send_log.rsl_prev_id
     */
    private Integer rslPrevId;

    /**
     * 发送方角色
     * abc_review_send_log.rsl_from_role
     */
    private Integer rslFromRole;

    /**
     * 发送方员工ID
     * abc_review_send_log.rsl_from_emp
     */
    private Integer rslFromEmp;

    /**
     * 接收方角色
     * abc_review_send_log.rsl_to_role
     */
    private Integer rslToRole;

    /**
     * 接收方员工ID
     * abc_review_send_log.rsl_to_emp
     */
    private Integer rslToEmp;

    /**
     * 如果此次发送涉及到审核改变，改变后的ReviewID
     * abc_review_send_log.rsl_next_review_id
     */
    private Integer rslNextReviewId;

    /**
     * 创建时间
     * abc_review_send_log.rsl_create_time
     */
    private Date rslCreateTime;

    /**
     * 更新时间
     * abc_review_send_log.rsl_update_time
     */
    private Date rslUpdateTime;

    public Integer getRslId() {
        return rslId;
    }

    public void setRslId(Integer rslId) {
        this.rslId = rslId;
    }

    public Integer getRslReviewId() {
        return rslReviewId;
    }

    public void setRslReviewId(Integer rslReviewId) {
        this.rslReviewId = rslReviewId;
    }

    public Integer getRslReviewVersion() {
        return rslReviewVersion;
    }

    public void setRslReviewVersion(Integer rslReviewVersion) {
        this.rslReviewVersion = rslReviewVersion;
    }

    public Integer getRslPrevId() {
        return rslPrevId;
    }

    public void setRslPrevId(Integer rslPrevId) {
        this.rslPrevId = rslPrevId;
    }

    public Integer getRslFromRole() {
        return rslFromRole;
    }

    public void setRslFromRole(Integer rslFromRole) {
        this.rslFromRole = rslFromRole;
    }

    public Integer getRslFromEmp() {
        return rslFromEmp;
    }

    public void setRslFromEmp(Integer rslFromEmp) {
        this.rslFromEmp = rslFromEmp;
    }

    public Integer getRslToRole() {
        return rslToRole;
    }

    public void setRslToRole(Integer rslToRole) {
        this.rslToRole = rslToRole;
    }

    public Integer getRslToEmp() {
        return rslToEmp;
    }

    public void setRslToEmp(Integer rslToEmp) {
        this.rslToEmp = rslToEmp;
    }

    public Integer getRslNextReviewId() {
        return rslNextReviewId;
    }

    public void setRslNextReviewId(Integer rslNextReviewId) {
        this.rslNextReviewId = rslNextReviewId;
    }

    public Date getRslCreateTime() {
        return rslCreateTime;
    }

    public void setRslCreateTime(Date rslCreateTime) {
        this.rslCreateTime = rslCreateTime;
    }

    public Date getRslUpdateTime() {
        return rslUpdateTime;
    }

    public void setRslUpdateTime(Date rslUpdateTime) {
        this.rslUpdateTime = rslUpdateTime;
    }
}

