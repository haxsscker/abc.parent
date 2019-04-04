package com.autoserve.abc.web.module.screen.account.myInvest;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.FeeSetting;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.sys.FeeSettingService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class Debt {
	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Resource
	private IncomePlanService incomePlanService;
	@Resource
	private FeeSettingService feeSettingService;
	@Resource
	private LoanService loanService;
	@Resource
	private TransferLoanService transferLoanService;
	 public void execute(Context context, ParameterParser params,Navigator nav) {
		 
		 User user = (User) session.getAttribute("user");
		 PlainResult<UserDO> result = userService.findById(user.getUserId());
		 UserDO userDO = result.getData();
		 String payPs = userDO.getUserDealPwd();
		 Integer investId=params.getInt("investId");
		 Integer bidType=params.getInt("bidType");
		 String flag=params.getString("flag");
		 PlainResult<Invest> invests=investQueryService.queryById(investId);
		 //债权总价值
		 IncomePlan incomePlan=new IncomePlan();
		 incomePlan.setBeneficiary(user.getUserId());
		 incomePlan.setInvestId(investId);
		 PlainResult<BigDecimal> palinResult=incomePlanService.queryBondMoney(incomePlan);
		 //转让手续费
		 BigDecimal counterFee=new BigDecimal(0);
		 Loan loan=null;
		 if(bidType!=null){
			 switch(invests.getData().getBidType()){
				 case COMMON_LOAN:
					 loan=queryOriginLoan(invests.getData().getBidId());
					 break;
				case TRANSFER_LOAN:
					PlainResult<TransferLoan>  transferLoanResult=transferLoanService.queryById(invests.getData().getBidId());
					if(transferLoanResult.getData()!=null){
						 loan=queryOriginLoan(transferLoanResult.getData().getOriginId());
					}
					break;
				default:
					break;
			 }
		 }
		 if(loan!=null && loan.getLoanCategory()!=null){
			 PlainResult<FeeSetting> feeSettingResult=feeSettingService.queryByFeeTypeLoanCategory(FeeType.TRANSFER_FEE, loan.getLoanCategory(), palinResult.getData());
			 if(feeSettingResult.getData()!=null){
				 counterFee= calculateFee(feeSettingResult.getData(),palinResult.getData());
			 }
			 
		 }
		 context.put("invest", invests.getData());	 
		 context.put("bidType", bidType); 
		 context.put("mon", palinResult.getData());
		 context.put("payPs", payPs);//交易密码
		 context.put("flag", flag);
		 context.put("counterFee", counterFee);
		 //转让收益总额
		 context.put("transferTotal", palinResult.getData().subtract(counterFee));
	 }
	 
	 
	 //获取转让标所对应的原始标
	 private Loan queryOriginLoan(Integer id){
		 ListResult<Loan> listsLoan=loanService.queryByIds(Arrays.asList(id), null);
		 if(listsLoan!=null && listsLoan.getData()!=null && listsLoan.getData().size()!=0){
			 return listsLoan.getData().get(0);
		 }
		 return null;
	 }
	 
	 /*
	  * 计算手续费
	  */
	 private BigDecimal calculateFee(FeeSetting feeSetting, BigDecimal base) {
	        if (feeSetting == null) {
	            return null;
	        }

	        switch (feeSetting.getChargeType()) {
	            case BY_DEAL: {
	                return feeSetting.getAccurateAmount();
	            }
	            case BY_RATIO: {
	                double fee = base.doubleValue() * feeSetting.getRate() / 100;
	                    return new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
	            }
	            default:
	                return new BigDecimal(0);
	        }

	    }
}
