package com.autoserve.abc.web.module.screen.selfprove.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;

/**
 * 类RealNameExamineData.java的实现描述：实名认证审核
 *
 * @author fangrui 2014年12月23日 下午3:53:31
 */
public class RealNameExamineData {
    @Resource
    private RealnameAuthService realnameAuthService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult baseResult = new BaseResult();
        Integer rnpId = params.getInt("cpiId");
        //1:通过   2：不通过
        Integer checkStatus = params.getInt("checkStatus");
        String rnpReviewNote = params.getString("checkIdear");
        if (checkStatus == 1) {
            baseResult = realnameAuthService.realNamePassReview(rnpId, rnpReviewNote);
        } else {
            baseResult = realnameAuthService.realNameFailedPassReview(rnpId, rnpReviewNote);
        }

        return ResultMapper.toBaseVO(baseResult);
    }
}
