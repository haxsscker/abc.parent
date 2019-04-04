package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
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
 * @author DS
 *
 * 2015年1月31日下午3:27:04
 */
public class Transfer {
	private static final Logger log = LoggerFactory.getLogger(Transfer.class);
	 @Resource
	 private TransferLoanService transferLoanService;
	 @Resource
	 private BuyLoanService      buyLoanService;
	 @Resource
	 private SysConfigService    sysConfigService;
	 @Resource
	 private LoanQueryService    loanQueryService;
	public  JsonBaseVO execute(Context context, ParameterParser params,Navigator nav) {
	try {
		int userId = params.getInt("userId");
        int originId = params.getInt("originId");
        int investId=params.getInt("investId");
        BidType bidType = BidType.valueOf(params.getInt("bidType"));
        double launchMoney = params.getDouble("launchMoney");
        String flag=params.getString("flag");
     // 发起转让
        if (BidType.TRANSFER_LOAN.equals(bidType)) {
            return launchTransfer(userId, originId, launchMoney,investId,flag);
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
	
	private JsonBaseVO launchTransfer(int userId, int originId, double launchMoney,int investId,String flag) {
		 JsonBaseVO result = new JsonBaseVO();
		//首次债权转让距放款周期判断
        PlainResult<Loan> loanResult = loanQueryService.queryById(originId);

        PlainResult<SysConfig> periodResult = this.sysConfigService.querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD);

        PlainResult<SysConfig> periodTypeResult = this.sysConfigService
                .querySysConfig(SysConfigEntry.LOAN_TRANSFER_PERIOD_TYPE);

        int flagDay = 0;

        DateTime fullTransferedtime = new DateTime(loanResult.getData().getLoanFullTransferedtime());

        if (periodTypeResult.getData().getConfValue().equals("1")) {

        	flagDay = fullTransferedtime.plusDays(Integer.valueOf(periodResult.getData().getConfValue())).compareTo(
                    DateTime.now());
            if (flagDay > 0) {
            	result.setSuccess(false);
                result.setMessage("距离划转放款日期没到" + periodResult.getData().getConfValue() + "日");
                return result;
            }

        } else if (periodTypeResult.getData().getConfValue().equals("2")) {

        	flagDay = fullTransferedtime.plusMonths(Integer.valueOf(periodResult.getData().getConfValue())).compareTo(
                    DateTime.now());
            if (flagDay > 0) {
            	result.setSuccess(false);
                result.setMessage("距离划转放款日期没到" + periodResult.getData().getConfValue() + "月");
                return result;
            }
        }

        //债权转让次数判断
        PlainResult<SysConfig> limitResult = this.sysConfigService.querySysConfig(SysConfigEntry.LOAN_TRANSFER_LIMIT);

        if (limitResult.getData().getConfValue() != null && !"-1".equals(limitResult.getData().getConfValue())) {
            TransferLoan transferLoan = new TransferLoan();
            transferLoan.setOriginId(originId);
            PageResult<TransferLoan> transferResult = transferLoanService.queryListByParam(transferLoan, new PageCondition(1, 65535));

            if (transferResult.getData() != null && StringUtils.isNotBlank(limitResult.getData().getConfValue())) {
                if (transferResult.getData().size() >= Integer.valueOf(limitResult.getData().getConfValue())) {
                	result.setSuccess(false);
                    result.setMessage("当前债权的转让次数已经达到" + limitResult.getData().getConfValue() + "次，已经无法转让");
                    return result;
                }

            }
        }
        TransferLoan pojo = new TransferLoan();
        pojo.setUserId(userId);
        pojo.setInvestId(investId);
        pojo.setOriginId(originId);
        pojo.setTransferMoney(BigDecimal.valueOf(launchMoney));

        pojo.setTransferDiscountFee(BigDecimal.valueOf(20)); // 转让折让费，不做控制
        pojo.setTransferTotal(BigDecimal.valueOf(100)); //转让债权总额，不做控制
        pojo.setTransferPeriod(1); // 转让期数，不做控制

        PlainResult<Integer> plainResult = transferLoanService.createTransferLoan(pojo);

       
        result.setSuccess(plainResult.isSuccess());
        if (plainResult.isSuccess()) {
            result.setMessage("发起转让成功，转让标id为：" + plainResult.getData());
            if(flag!=null && "investRecord".equals(flag)){
            	 result.setRedirectUrl("/account/myInvest/investRecord?investType=HKZ");
            }else if(flag!=null && "investClaim".equals(flag)){
            	 result.setRedirectUrl("/account/myInvest/investClaim?type=HKZ");
            }          
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


}
