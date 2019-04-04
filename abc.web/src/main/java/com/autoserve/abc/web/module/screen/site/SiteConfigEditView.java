/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.site;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.SysConfigDO;
import com.autoserve.abc.service.biz.intf.sys.SiteConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 网站设置编辑
 *
 * @author segen189 2015年1月4日 下午10:43:45
 */
public class SiteConfigEditView {

    @Resource
    private SiteConfigService siteConfigService;

    public void execute(Context context, ParameterParser params) {

        PlainResult<SysConfigDO> plainResult = siteConfigService.queryBySiteKey(params.getString("siteKey"));
        if (plainResult.isSuccess()) {
            context.put("siteConfig", plainResult.getData());
        }

    }

}
