package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.ReviewState;

import java.util.Date;

/**
 * 机构信息修改记录实体类
 *
 * @author RJQ 2014/12/19 21:20.
 */
public class GovUpdateHistory {
    private int updateHistoryId;

    private int govId;

    private String govName;

    private int updateEmpId;

    private String updateEmpName;

    private Date updateDate;

    private int reviewEmpId;

    private String reviewEmpName;

    private Date reviewDate;

    private String reviewMessage;

    private ReviewState reviewState;

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public int getUpdateHistoryId() {
        return updateHistoryId;
    }

    public void setUpdateHistoryId(int updateHistoryId) {
        this.updateHistoryId = updateHistoryId;
    }

    public int getGovId() {
        return govId;
    }

    public void setGovId(int govId) {
        this.govId = govId;
    }

    public int getUpdateEmpId() {
        return updateEmpId;
    }

    public void setUpdateEmpId(int updateEmpId) {
        this.updateEmpId = updateEmpId;
    }

    public String getUpdateEmpName() {
        return updateEmpName;
    }

    public void setUpdateEmpName(String updateEmpName) {
        this.updateEmpName = updateEmpName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getReviewEmpId() {
        return reviewEmpId;
    }

    public void setReviewEmpId(int reviewEmpId) {
        this.reviewEmpId = reviewEmpId;
    }

    public String getReviewEmpName() {
        return reviewEmpName;
    }

    public void setReviewEmpName(String reviewEmpName) {
        this.reviewEmpName = reviewEmpName;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public ReviewState getReviewState() {
        return reviewState;
    }

    public void setReviewState(ReviewState reviewState) {
        this.reviewState = reviewState;
    }
}
