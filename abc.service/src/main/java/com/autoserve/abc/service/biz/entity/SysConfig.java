package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.SysConfigEntry;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-27,15:15
 */
public class SysConfig {
    private Integer           confId;

    private SysConfigEntry    conf;

    private String            confValue;

    private Date              confCreateTime;

    private Date              confModifyTime;

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public SysConfigEntry getConf() {
        return conf;
    }

    public void setConf(SysConfigEntry conf) {
        this.conf = conf;
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    public Date getConfCreateTime() {
        return confCreateTime;
    }

    public void setConfCreateTime(Date confCreateTime) {
        this.confCreateTime = confCreateTime;
    }

    public Date getConfModifyTime() {
        return confModifyTime;
    }

    public void setConfModifyTime(Date confModifyTime) {
        this.confModifyTime = confModifyTime;
    }
}
