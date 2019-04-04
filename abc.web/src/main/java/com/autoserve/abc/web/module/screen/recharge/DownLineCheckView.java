/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.recharge;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.DownRechargeJDO;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.mon.DownRechargeService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 线下审核审核页面
 * 
 * @author J.YL 2015年1月8日 下午9:06:00
 */
public class DownLineCheckView {
    @Resource
    private DownRechargeService downRechargeService;

    public void execute(Context context, ParameterParser params) {
        Integer downRechargeId = params.getInt("downRechargeId");
        PlainResult<DownRechargeJDO> result = downRechargeService.queryById(downRechargeId);
        if (!result.isSuccess()) {
            return;
        }
        DownRechargeJDO data = result.getData();
        context.put("downLineRecharge", data);
        context.put("fileUploadClassType", FileUploadClassType.DOWN_LINE_RECHARGE.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("dataId", data.getDownRechargeFileId());
    }
}
