package com.autoserve.abc.dao.dataobject;

import java.util.Date;

public class ApplicationDO {
    private Integer           applicationId;

    private String            applicationName;

    private String            applicationAlias;

    private String            applicationDescription;

    private Integer           applicationStatus;

    private Integer           packVersion;

    private String            reportAddr;

    private Integer           authLevel;

    private String            creator;
    private String            modifier;
    private Date              gmtCreated;
    private Date              gmtModified;

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationAlias() {
        return applicationAlias;
    }

    public void setApplicationAlias(String applicationAlias) {
        this.applicationAlias = applicationAlias;
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getPackVersion() {
        return packVersion;
    }

    public void setPackVersion(Integer packVersion) {
        this.packVersion = packVersion;
    }

    public String getReportAddr() {
        return reportAddr;
    }

    public void setReportAddr(String reportAddr) {
        this.reportAddr = reportAddr;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
