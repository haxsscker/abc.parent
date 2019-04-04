/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 删除红包规则
 * 
 * @author zhangkang 2015年6月12日 上午10:44:02
 */
public class RewardDel {
    @Resource
    private RedService redService;

    private Logger     logger = LoggerFactory.getLogger(getClass());

    public JsonBaseVO execute(ParameterParser params) {
        int redId = params.getInt("redId");
        logger.debug(redId + "");
        BaseResult baseResult = redService.removeRed(redId);
        return ResultMapper.toBaseVO(baseResult);

    }
}
