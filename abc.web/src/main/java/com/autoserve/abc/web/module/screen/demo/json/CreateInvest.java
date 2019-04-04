/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.demo.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 创建投资
 * 
 * @author segen189 2015年1月13日 下午3:31:24
 */
public class CreateInvest {
    private static final Logger log = LoggerFactory.getLogger(CreateInvest.class);

    @Resource
    private InvestService       investService;

    @Resource
    private InvestQueryService  investQueryService;

    @Resource
    private DealRecordService   dealRecordService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        try {
            // 投资
            String mainJsonStr = params.getString("mainInvest");
            JSONObject mainJson = JSON.parseObject(mainJsonStr);

            int userId = mainJson.getInteger("userId");
            BidType bidType = BidType.valueOf(mainJson.getInteger("bidType"));
            if (bidType == null) {
                bidType = BidType.BUY_LOAN;
            }

            int bidId = mainJson.getInteger("bidId");

            Invest inv = new Invest();
            inv.setUserId(userId);
            inv.setBidType(bidType);

            if (BidType.COMMON_LOAN.equals(bidType)) {
                inv.setBidId(bidId);
                inv.setOriginId(bidId);
            } else {
                inv.setBidId(mainJson.getInteger("bidId"));
            }

            if (mainJson.containsKey("investMoney")) {
                double investMoney = mainJson.getDouble("investMoney");
                inv.setInvestMoney(BigDecimal.valueOf(investMoney));
            }
            List<Integer> redsendIdList = new ArrayList<Integer>();
            PlainResult<Integer> investCreateResult = investService.createInvest(inv, redsendIdList);
            if (!investCreateResult.isSuccess()) {
                return directReturn(investCreateResult);
            }

            // 支付
            if (BidType.BUY_LOAN.equals(inv.getBidType())) {
                return JsonBaseVO.SUCCESS;
            } else {
                PlainResult<Invest> investQueryResult = investQueryService.queryById(investCreateResult.getData());
                if (!investQueryResult.isSuccess()) {
                    return directReturn(investQueryResult);
                }
                BaseResult invokeResult = new BaseResult();/*
                                                            * dealRecordService.
                                                            * invokePayment
                                                            * (investQueryResult
                                                            * .getData
                                                            * ().getInnerSeqNo
                                                            * ())
                                                            */
                if (investCreateResult.isSuccess()) {
                    invokeResult.setSuccess(true);
                    invokeResult.setMessage("投资成功");
                }
                return ResultMapper.toBaseVO(invokeResult);
            }
        } catch (Exception e) {
            log.error("投资失败", e);
        }

        return directReturn(new BaseResult().setError(CommonResultCode.BIZ_ERROR, "投资失败"));
    }

    private JsonBaseVO directReturn(BaseResult serviceResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(serviceResult.getMessage());
        result.setSuccess(serviceResult.isSuccess());
        return result;
    }

}
