/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.sys;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 网站设置服务
 *
 * @author segen189 2015年1月4日 下午8:28:44
 */
public interface SiteConfigService {

    BaseResult create(SysConfigDO toInsert);

    PlainResult<SysConfigDO> queryBySiteKey(String siteKey);

    BaseResult modify(SysConfigDO toModify);

    BaseResult removeByConfigKey(String configKey);

    PageResult<SysConfigDO> queryPageList(PageCondition pageCondition);

}
