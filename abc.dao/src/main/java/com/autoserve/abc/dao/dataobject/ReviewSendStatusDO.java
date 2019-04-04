package com.autoserve.abc.dao.dataobject;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,15:41
 */
public class ReviewSendStatusDO {
    /**
     * 自增主键
     * abc_review_send_status.rss_id
     */
    private Integer rssId;

    /**
     * 相关联的审核ID
     * abc_review_send_status.rss_review_id
     */
    private Integer rssReviewId;

    /**
     * 是否发送到小贷公司 0否 1是
     * abc_review_send_status.send_loan_gov
     */
    private Boolean rssSendLoanGov;

    /**
     * 是否发送到担保机构 0否 1是
     * abc_review_send_status.send_guar_gov
     */
    private Boolean rssSendGuarGov;

    /**
     * 是否发送到平台审核 0否 1是
     * abc_review_send_status.send_platform
     */
    private Boolean rssSendPlatform;

    public Integer getRssId() {
        return rssId;
    }

    public void setRssId(Integer rssId) {
        this.rssId = rssId;
    }

    public Integer getRssReviewId() {
        return rssReviewId;
    }

    public void setRssReviewId(Integer rssReviewId) {
        this.rssReviewId = rssReviewId;
    }

    public Boolean getRssSendLoanGov() {
        return rssSendLoanGov;
    }

    public void setRssSendLoanGov(Boolean rssSendLoanGov) {
        this.rssSendLoanGov = rssSendLoanGov;
    }

    public Boolean getRssSendGuarGov() {
        return rssSendGuarGov;
    }

    public void setRssSendGuarGov(Boolean rssSendGuarGov) {
        this.rssSendGuarGov = rssSendGuarGov;
    }

    public Boolean getRssSendPlatform() {
        return rssSendPlatform;
    }

    public void setRssSendPlatform(Boolean rssSendPlatform) {
        this.rssSendPlatform = rssSendPlatform;
    }
}
