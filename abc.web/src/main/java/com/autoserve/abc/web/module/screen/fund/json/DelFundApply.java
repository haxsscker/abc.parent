package com.autoserve.abc.web.module.screen.fund.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;

/**
 * Created by pp on 2014/12/3.
 */
public class DelFundApply {
    @Resource
    private FundApplyService fundApplyService;

    public JsonBaseVO execute(@Param("id") Integer fundApplyId){
    	FundApply fundAppy = new FundApply();
    	fundAppy.setFaFundId(fundApplyId);
        return ResultMapper.toBaseVO(fundApplyService.removeFundApply(fundAppy));
    }
}
