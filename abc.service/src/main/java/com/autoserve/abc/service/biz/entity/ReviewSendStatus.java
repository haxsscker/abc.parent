package com.autoserve.abc.service.biz.entity;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,15:47
 */
public class ReviewSendStatus {
    private Integer id;

    private Review review;

    private Boolean sendLoanGov;

    private Boolean sendGuarGov;

    private Boolean sendPlatform;

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

    public Boolean getSendLoanGov() {
        return sendLoanGov;
    }

    public void setSendLoanGov(Boolean sendLoanGov) {
        this.sendLoanGov = sendLoanGov;
    }

    public Boolean getSendGuarGov() {
        return sendGuarGov;
    }

    public void setSendGuarGov(Boolean sendGuarGov) {
        this.sendGuarGov = sendGuarGov;
    }

    public Boolean getSendPlatform() {
        return sendPlatform;
    }

    public void setSendPlatform(Boolean sendPlatform) {
        this.sendPlatform = sendPlatform;
    }
}
