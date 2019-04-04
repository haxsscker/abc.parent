/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.dao.intf.SysConfigDao;
import com.autoserve.abc.service.biz.intf.sys.SiteConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 网站设置服务<br>
 * 通过参数配置表实现，不同之处在于查询前后为查询关键字加上前缀/去掉前缀
 *
 * @author segen189 2015年1月4日 下午8:44:49
 */
@Service
public class SiteConfigServiceImpl implements SiteConfigService {

    @Autowired
    private SysConfigDao        configDao;

    private static final String siteConfigKeyPrefix = "SITE_AUTO_PREFIX_";

    @Override
    public BaseResult create(SysConfigDO toInsert) {
        toInsert.setConfKey(siteConfigKeyPrefix + toInsert.getConfKey());
        configDao.insert(toInsert);
        return BaseResult.SUCCESS;
    }

    @Override
    public PlainResult<SysConfigDO> queryBySiteKey(String siteKey) {
        PlainResult<SysConfigDO> result = new PlainResult<SysConfigDO>();

        if (StringUtils.isBlank(siteKey)) {
            return result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK);
        }

        result.setData(configDao.findByConfigKey(siteConfigKeyPrefix + siteKey));
        return result;
    }

    @Override
    public BaseResult modify(SysConfigDO toModify) {
        if (StringUtils.isBlank(toModify.getConfKey())) {
            new BaseResult().setError(CommonResultCode.BIZ_ERROR, "配置项的代号不能为空");
        }

        toModify.setConfKey(siteConfigKeyPrefix + toModify.getConfKey());
        configDao.updateByConfigKey(toModify);
        return BaseResult.SUCCESS;
    }

    @Override
    public BaseResult removeByConfigKey(String siteKey) {
        if (StringUtils.isBlank(siteKey)) {
            return new BaseResult().setError(CommonResultCode.ILLEGAL_PARAM_BLANK);
        }

        configDao.deleteByConfigKey(siteConfigKeyPrefix + siteKey);
        return BaseResult.SUCCESS;
    }

    @Override
    public PageResult<SysConfigDO> queryPageList(PageCondition pageCondition) {
        PageResult<SysConfigDO> result = new PageResult<SysConfigDO>(pageCondition);

        int count = configDao.countListWithPrefix(siteConfigKeyPrefix);
        if (count > 0) {
            List<SysConfigDO> data = configDao.findListWithPrefix(siteConfigKeyPrefix, pageCondition);

            for (SysConfigDO configDO : data) {
                configDO.setConfKey(configDO.getConfKey().replaceFirst(siteConfigKeyPrefix, ""));
            }

            result.setData(data);
        }

        return result;
    }

}
