package com.autoserve.abc.web.module.screen.feeset.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class DelFeeData {

    @Resource
    private FeeSettingService feeSettingService;

    public JsonBaseVO execute(ParameterParser params) {
        Integer id = params.getInt("id");
        BaseResult result = feeSettingService.deleteFeeSetting(id);
        return ResultMapper.toBaseVO(result);
    }

}
