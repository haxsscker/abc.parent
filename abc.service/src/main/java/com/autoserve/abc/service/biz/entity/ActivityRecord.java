/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.ActivityType;

/**
 * 活动记录
 *
 * @author segen189 2014年11月22日 下午1:04:39
 */
public class ActivityRecord {
    /**
     * 主键id
     */
    private Integer      id;

    /**
     * 活动类型
     */
    private ActivityType activityType;

    /**
     * 外键id
     */
    private Integer      foreignId;

    /**
     * 创建时间
     */
    private Date         createtime;

    /**
     * 创建人
     */
    private Integer      creator;

    /**
     * 结束时间
     */
    private Date         endtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Integer getForeignId() {
        return foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

}
