/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.fund.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.fund.FundOrderService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;

/**
 * 类fundOrderApplyAudit.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月25日 下午3:04:40
 */
public class FundOrderApplyAudit {
    @Resource
    private FundOrderService fundOrderService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult baseResult;
        Integer foOrderId = params.getInt("cpiId");
        //1:通过   2：不通过
        Integer checkStatus = params.getInt("checkStatus");
        String fcCheckIdear = params.getString("checkIdear");
        //String rnpReviewDate = (new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_STYLE)).format(new Date());
        if (checkStatus == 1) {
            baseResult = fundOrderService.passReview(foOrderId, fcCheckIdear);
        } else {
            baseResult = fundOrderService.failedPassReview(foOrderId, fcCheckIdear);
        }

        return ResultMapper.toBaseVO(baseResult);
    }
}
