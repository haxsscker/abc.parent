/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.entity.Refund;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.cash.RefundService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 退款插入数据
 * 
 * @author J.YL 2014年12月22日 下午9:16:08
 */
public class RefundApply {

    @Resource
    private RefundService    refundService;
    @Resource
    private SysConfigService sysConfigService;

    public JsonBaseVO execute(ParameterParser params) {
        BaseResult resultVO = new BaseResult();
        Integer applicantId = LoginUserUtil.getEmpId();
        String platFormAccountNo = sysConfigService.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData()
                .getConfValue();
        Integer userId = params.getInt("rep_back_emp");
        String accountNo = params.getString("rep_back_bank");
        String userPhone = params.getString("rep_user_phone");
        BigDecimal amount = new BigDecimal(params.get("rep_return_amount").toString());
        String reason = params.getString("rep_reject_reason");

        if (accountNo.equals(platFormAccountNo)) {
            resultVO.setMessage("创建退款申请失败！收款人不能为平台资金账户！");
            resultVO.setSuccess(false);
            return ResultMapper.toBaseVO(resultVO);
        }
        Refund refund = new Refund();
        refund.setRefundAccountNo(accountNo);
        refund.setRefundAmount(amount);
        refund.setRefundOperator(applicantId);
        refund.setRefundUserId(userId);
        refund.setRefundReason(reason);
        refund.setRefundUserPhone(userPhone);

        BaseResult result = refundService.createRefundApply(refund);
        if (!result.isSuccess()) {
            resultVO.setMessage("创建退款申请失败！");
            resultVO.setSuccess(false);
        } else {
            resultVO.setMessage("创建退款成功！");
        }

        return ResultMapper.toBaseVO(resultVO);
    }
}
