package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * SysConfig abc_sys_config
 */
public class SysConfigDO {
    /**
     * 自增id abc_sys_config.conf_id
     */
    private Integer confId;

    /**
     * 键名 abc_sys_config.conf_key
     */
    private String  confKey;

    /**
     * 值 abc_sys_config.conf_value
     */
    private String  confValue;

    /**
     * 描述 abc_sys_config.conf_desc
     */
    private String  confDesc;

    /**
     * 创建时间 abc_sys_config.conf_create_time
     */
    private Date    confCreateTime;

    /**
     * 修改时间 abc_sys_config.conf_modify_time
     */
    private Date    confModifyTime;

    /**
     * 参数名称 abc_sys_config.conf_name
     */
    private String  confName;

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    public String getConfDesc() {
        return confDesc;
    }

    public void setConfDesc(String confDesc) {
        this.confDesc = confDesc;
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

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }
}
