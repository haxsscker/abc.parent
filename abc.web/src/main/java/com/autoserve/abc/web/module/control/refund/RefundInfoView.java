/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.control.refund;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.service.biz.enums.RefundState;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.refund.RefundRecordService;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.moneyManage.RefundListCheckViewVO;

/**
 * 退款审核信息查看
 * 
 * @author J.YL 2015年1月17日 上午11:14:18
 */
public class RefundInfoView {
    @Resource
    private RefundRecordService refundRecordService;
    @Resource
    private AccountInfoService  accountInfoService;

    public void execute(Context context, ParameterParser params) {

        Integer refundId = params.getInt("refundId");
        RefundRecordDO refund = refundRecordService.queryById(refundId);
        AccountInfoDO account = accountInfoService.queryByAccountNo(refund.getRefundAccountNo());
        RefundListCheckViewVO refundVO = new RefundListCheckViewVO();
        refundVO.setAccountNo(refund.getRefundAccountNo());
        refundVO.setApplyDate(DateUtil.formatDate(refund.getRefundApplyDate(), DateUtil.DEFAULT_FORMAT_STYLE));
        refundVO.setRefundMoney(refund.getRefundAmount());
        refundVO.setRefundReason(refund.getRefundReason());
        refundVO.setUserName(account.getAccountUserName());
        refundVO.setUserPhone(refund.getRefundUserPhone());
        refundVO.setRefundState(RefundState.valueOf(refund.getRefundState()).getDes());
        refundVO.setApplyReviewMessage(refund.getRefundApplyOpinion());
        context.put("refund", refundVO);
    }
}
