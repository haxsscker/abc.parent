/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.demo.json;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 发起转让或发起收购
 * 
 * @author segen189 2015年1月13日 下午3:31:24
 */
public class LaunchTransOrBuy {
    private static final Logger log = LoggerFactory.getLogger(LaunchTransOrBuy.class);

    @Resource
    private TransferLoanService transferLoanService;

    @Resource
    private BuyLoanService      buyLoanService;

    @Resource
    private SysConfigService    sysConfigService;

    @Resource
    private LoanQueryService    loanQueryService;

    public JsonBaseVO execute(Context context, ParameterParser params) {
        try {
            String mainJsonStr = params.getString("mainLaunch");
            JSONObject mainJson = JSON.parseObject(mainJsonStr);

            int userId = mainJson.getInteger("userId");
            int originId = mainJson.getInteger("originId");
            int investId = mainJson.getInteger("investId");
            BidType bidType = BidType.valueOf(mainJson.getInteger("bidType"));
            double launchMoney = mainJson.getDouble("launchMoney");

            // 发起转让
            if (BidType.TRANSFER_LOAN.equals(bidType)) {
                return launchTransfer(userId, originId, investId, launchMoney);
            }
            // 发起收购
            else if (BidType.BUY_LOAN.equals(bidType)) {
                return launchBuy(userId, originId, launchMoney);
            }
        } catch (Exception e) {
            log.error("发起失败", e);
        }

        return directReturn(new BaseResult().setError(CommonResultCode.BIZ_ERROR, "参数填写错误"));
    }

    private JsonBaseVO launchTransfer(int userId, int originId, int investId, double launchMoney) {

        JsonBaseVO result = new JsonBaseVO();

        TransferLoan pojo = new TransferLoan();

        //首次债权转让距放款周期判断
        PlainResult<Loan> loanResult = loanQueryService.queryById(originId);

        PlainResult<SysConfig> periodResult = this.sysConfigService.querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD);

        PlainResult<SysConfig> periodTypeResult = this.sysConfigService
                .querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD_TYPE);

        int flag = 0;

        DateTime fullTransferedtime = new DateTime(loanResult.getData().getLoanFullTransferedtime());

        if (periodTypeResult.getData().getConfValue().equals("1")) {

            flag = fullTransferedtime.plusDays(Integer.valueOf(periodResult.getData().getConfValue())).compareTo(
                    DateTime.now());
            if (flag > 0) {
                result.setMessage("距离划转放款日期没到" + periodResult.getData().getConfValue() + "日");
                return result;
            }

        } else if (periodTypeResult.getData().getConfValue().equals("2")) {

            flag = fullTransferedtime.plusMonths(Integer.valueOf(periodResult.getData().getConfValue())).compareTo(
                    DateTime.now());
            if (flag > 0) {
                result.setMessage("距离划转放款日期没到" + periodResult.getData().getConfValue() + "月");
                return result;
            }
        }

        //债权转让次数判断
        PlainResult<SysConfig> limitResult = this.sysConfigService.querySysConfig(SysConfigEntry.LOAN_TRANSFER_LIMIT);

        if (limitResult.getData().getConfValue() != null) {
            TransferLoan transferLoan = new TransferLoan();
            transferLoan.setOriginId(originId);
            PageResult<TransferLoan> transferResult = transferLoanService.queryListByParam(transferLoan, null);

            if (transferResult.getData() != null && StringUtils.isNotBlank(limitResult.getData().getConfValue())) {
                if (transferResult.getData().size() >= Integer.valueOf(limitResult.getData().getConfValue())) {
                    result.setMessage("当前借口债权转让次数已经达到" + limitResult.getData().getConfValue() + "次，已经无法转让");
                    return result;
                }

            }
        }

        pojo.setUserId(userId);
        pojo.setOriginId(originId);

        pojo.setInvestId(investId);//根据投资ID进行转让

        pojo.setTransferMoney(BigDecimal.valueOf(launchMoney));

        pojo.setTransferDiscountFee(BigDecimal.valueOf(0)); // 转让折让费，不做控制
        pojo.setTransferTotal(BigDecimal.valueOf(0)); //转让债权总额，不做控制
        pojo.setTransferPeriod(1); // 转让期数，不做控制

        PlainResult<Integer> plainResult = transferLoanService.createTransferLoan(pojo);

        result.setSuccess(plainResult.isSuccess());
        if (plainResult.isSuccess()) {
            result.setMessage("发起转让成功，转让标id为：" + plainResult.getData());
        } else {
            result.setMessage(plainResult.getMessage());
        }

        return result;
    }

    private JsonBaseVO launchBuy(int userId, int originId, double launchMoney) {
        BuyLoan pojo = new BuyLoan();

        pojo.setUserId(userId);
        pojo.setOriginId(originId);
        pojo.setBuyMoney(BigDecimal.valueOf(launchMoney));

        pojo.setBuyPeriod(1);// 收购期数，不做控制
        pojo.setBuyTotal(BigDecimal.valueOf(launchMoney));//收购总债权，不做控制
        pojo.setFee(BigDecimal.valueOf(20));//不做控制

        PlainResult<Integer> plainResult = buyLoanService.createBuyLoan(pojo);
        JsonBaseVO result = new JsonBaseVO();
        result.setSuccess(plainResult.isSuccess());
        if (plainResult.isSuccess()) {
            result.setMessage("发起收购成功，收购标id为：" + plainResult.getData());
        } else {
            result.setMessage(plainResult.getMessage());
        }

        return result;
    }

    private JsonBaseVO directReturn(BaseResult serviceResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setMessage(serviceResult.getMessage());
        result.setSuccess(serviceResult.isSuccess());
        return result;
    }

    /**
     * 计算两个时间相差天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public int getDifferenceDays(Date date1, Date date2) {

        int loanDays = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(date1);
            c2.setTime(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (c1.before(c2)) {
            loanDays++;
            c1.add(Calendar.DAY_OF_YEAR, 1);
        }

        return loanDays;
    }

}
