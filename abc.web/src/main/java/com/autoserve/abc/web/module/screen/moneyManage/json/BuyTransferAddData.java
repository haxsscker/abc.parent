package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.enums.FullTransferType;
import com.autoserve.abc.service.biz.intf.loan.fulltransfer.FullTransferService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 资金管理-收购满标划转
 *
 * @author liuwei 2015年1月8日 下午8:06:20
 */
public class BuyTransferAddData {
    @Resource
    private FullTransferService fullTransferService;
    @Resource
    private HttpSession         httpSession;

    public JsonBaseVO execute(ParameterParser params, @Param("buyLoanId") int buyLoanId,
                              @Param("collectFee") double collectFee, @Param("strCheckCode") String strCheckCode) {
        String code = (String) httpSession.getAttribute("VCode");
        Date date = (Date) httpSession.getAttribute("VDate");

        DateTime dt1 = new DateTime(date);
        DateTime dt2 = DateTime.now();

        JsonBaseVO vo = new JsonBaseVO();
        if (Minutes.minutesBetween(dt1, dt2).getMinutes() > 30) {
            vo.setSuccess(false);
            vo.setMessage("验证码超时!");
            return vo;
        }
        if (!strCheckCode.equals(code)) {
            vo.setSuccess(false);
            vo.setMessage("验证码错误!");
            return vo;
        }
        BaseResult result = this.fullTransferService.buyBidMoneyTransfer(buyLoanId, collectFee,
                FullTransferType.BUY_LOAN_FULL_TRANSFER, LoginUserUtil.getEmpId());
        return ResultMapper.toBaseVO(result);

    }

}
