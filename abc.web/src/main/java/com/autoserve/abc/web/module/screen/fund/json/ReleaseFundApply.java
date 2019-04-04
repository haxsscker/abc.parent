package com.autoserve.abc.web.module.screen.fund.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.FundApply;
import com.autoserve.abc.service.biz.enums.FundState;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * Created by wangyongheng on 2014/12/19
 */
public class ReleaseFundApply {
    @Resource
    private FundApplyService fundApplyService;

    public JsonBaseVO execute(@Param("id") Integer fundApplyId, @Param("releaseOrCancel") Integer fundState){
    	FundApply fundAppy = new FundApply();
    	fundAppy.setFaFundId(fundApplyId);
    	if(fundState==1){
    		fundAppy.setFaFundState(FundState.IS_RELEASE);
    	}else if(fundState==0){
    		fundAppy.setFaFundState(FundState.IS_NOT_RELEASE);
    	}
        return ResultMapper.toBaseVO(fundApplyService.modifyFundApply(fundAppy));
    }
}
