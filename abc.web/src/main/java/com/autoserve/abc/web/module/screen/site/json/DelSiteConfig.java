/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.site.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.sys.SiteConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 网站设置列表
 *
 * @author segen189 2015年1月4日 下午9:42:58
 */
public class DelSiteConfig {

    @Resource
    private SiteConfigService siteConfigService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult baseResult = siteConfigService.removeByConfigKey(params.getString("siteKey"));
        return ResultMapper.toBaseVO(baseResult);
    }

}
