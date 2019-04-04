package com.autoserve.abc.web.module.screen.fund.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.intf.fund.FundApplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.FundApplyVOConvert;
import com.autoserve.abc.web.convert.FundProfitVOConvert;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.fund.FundApplyVO;
import com.autoserve.abc.web.vo.fund.FundProfitVO;

/**
 * Created by pp on 2014/12/3.
 */
public class EditFundApply {
    @Resource
    private FundApplyService fundApplyService;

    public JsonBaseVO execute(ParameterParser param){
    	JsonBaseVO vo = new JsonBaseVO();
    	String fundApplyParam = param.getString("main");
    	String fundProfitParam = param.getString("fund_profit");
    	FundApplyVO fundApplyVO = JSON.parseObject(fundApplyParam, FundApplyVO.class);
    	List<FundProfitVO> fundProfitList = JSON.parseArray(fundProfitParam, FundProfitVO.class);
//    	List<FundProfitVO> fundProfitList = JSONObject.parseArray(fundProfitParam, FundProfitVO.class);
    	
    	BaseResult result = fundApplyService.updateFundApply(FundApplyVOConvert.toFundApply(fundApplyVO), fundProfitList!=null?FundProfitVOConvert.convertEntityList(fundProfitList):null);
    	vo = ResultMapper.toBaseVO(result);

        return vo;
    }
}
