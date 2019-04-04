package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-27,15:48
 */
public class SysConfigConverter {
    public static SysConfigDO toSysConfigDO(SysConfig sysConfig) {
        SysConfigDO configDO = new SysConfigDO();
        if (sysConfig == null) {
            return configDO;
        }

        configDO.setConfId(sysConfig.getConfId());
        configDO.setConfKey(sysConfig.getConf().name());
        configDO.setConfValue(sysConfig.getConfValue());
        configDO.setConfDesc(sysConfig.getConf().descrip);
        configDO.setConfCreateTime(sysConfig.getConfCreateTime());
        configDO.setConfModifyTime(sysConfig.getConfModifyTime());

        return configDO;
    }

    public static SysConfig toSysConfig(SysConfigDO configDO) {
        SysConfig sysConfig = new SysConfig();
        if (configDO == null) {
            return sysConfig;
        }

        sysConfig.setConfId(configDO.getConfId());
        sysConfig.setConfValue(configDO.getConfValue());
        sysConfig.setConf(SysConfigEntry.valueOf(configDO.getConfKey()));
        sysConfig.setConfCreateTime(configDO.getConfCreateTime());
        sysConfig.setConfModifyTime(configDO.getConfModifyTime());

        return sysConfig;
    }


}
