/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.control.common;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;

/**
 * 项目的分页投资列表
 *
 * @author segen189 2014年12月25日 上午10:41:11
 */
public class ProjectInvestList {

    @Resource
    private InvestQueryService investQueryService;

    public void execute(Context context) {
        context.put("bidId", Integer.valueOf(context.get("bidId").toString()));
        context.put("bidType", Integer.valueOf(context.get("bidType").toString()));
        context.put("page", Integer.valueOf(context.get("page").toString()));
        context.put("rows", Integer.valueOf(context.get("rows").toString()));
    }
}
