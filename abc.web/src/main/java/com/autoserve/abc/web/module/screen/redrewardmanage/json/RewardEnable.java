package com.autoserve.abc.web.module.screen.redrewardmanage.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 红包启用停用
 * 
 * @author sean 2015年3月12日 上午10:51:46
 */
public class RewardEnable {

    @Resource
    private RedService redService;

    public JsonBaseVO execute(ParameterParser params) {
        int redId = params.getInt("redId");
        boolean enable = params.getBoolean("enable");

        if (enable) {
            BaseResult baseResult = redService.enableRed(redId);
            return ResultMapper.toBaseVO(baseResult);
        } else {
            BaseResult baseResult = redService.disableRed(redId);
            return ResultMapper.toBaseVO(baseResult);
        }
    }
}
