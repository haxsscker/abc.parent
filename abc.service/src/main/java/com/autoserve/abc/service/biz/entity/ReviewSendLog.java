package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.BaseRoleType;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-30,19:37
 */
public class ReviewSendLog {
    /**
     * 表示发送记录为空
     *
     * 说明：如果一个对象引用了ReviewSendLog，如果想要将该对象的ReviewSendLog段设为null，传入null是不行的
     * 因为mybatis在执行更新方法时，会忽略为null的字段，所以传入的null值不会被更新到数据库中
     * 因此需要显示的将该ReviewSendLog字段设置为此NULL_SEND_LOG，这样数据库中该字段值为-1，表示空的意思
     */
    public static ReviewSendLog NULL_SEND_LOG = new ReviewSendLog(-1);

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 相关的ReviewID
     */
    private Review review;

    /**
     * 此次发送时相关审核的版本号
     */
    private Integer reviewVersion;

    /**
     * 上一条发送记录ID
     */
    private ReviewSendLog prev;

    /**
     * 发送方角色
     */
    private BaseRoleType fromRole;

    /**
     * 发送方员工ID
     */
    private Employee fromEmployee;

    /**
     * 接收方角色
     */
    private BaseRoleType toRole;

    /**
     * 接收方员工ID
     */
    private Employee toEmployee;

    /**
     *  如果此次发送涉及到审核改变，改变后的ReviewID
     */
    private Review nextReview;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public ReviewSendLog() {
    }

    public ReviewSendLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Integer getReviewVersion() {
        return reviewVersion;
    }

    public void setReviewVersion(Integer reviewVersion) {
        this.reviewVersion = reviewVersion;
    }

    public ReviewSendLog getPrev() {
        return prev;
    }

    public void setPrev(ReviewSendLog prev) {
        this.prev = prev;
    }

    public BaseRoleType getFromRole() {
        return fromRole;
    }

    public void setFromRole(BaseRoleType fromRole) {
        this.fromRole = fromRole;
    }

    public Employee getFromEmployee() {
        return fromEmployee;
    }

    public void setFromEmployee(Employee fromEmployee) {
        this.fromEmployee = fromEmployee;
    }

    public BaseRoleType getToRole() {
        return toRole;
    }

    public void setToRole(BaseRoleType toRole) {
        this.toRole = toRole;
    }

    public Employee getToEmployee() {
        return toEmployee;
    }

    public void setToEmployee(Employee toEmployee) {
        this.toEmployee = toEmployee;
    }

    public Review getNextReview() {
        return nextReview;
    }

    public void setNextReview(Review nextReview) {
        this.nextReview = nextReview;
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
