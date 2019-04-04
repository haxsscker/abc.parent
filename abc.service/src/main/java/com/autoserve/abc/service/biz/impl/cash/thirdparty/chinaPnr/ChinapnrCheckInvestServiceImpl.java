package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.LoanDO;
import com.autoserve.abc.dao.dataobject.TransferLoanDO;
import com.autoserve.abc.dao.intf.LoanDao;
import com.autoserve.abc.dao.intf.TransferLoanDao;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.enums.TransferLoanState;
import com.autoserve.abc.service.biz.intf.cash.CheckInvestService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 
 * @author andy
 *	
 * @since 2015/3/12 15:45
 * 
 */

@Service
public class ChinapnrCheckInvestServiceImpl implements CheckInvestService{
	@Resource
	private LoanDao loanDao;
	
	@Resource
	private TransferLoanDao transferLoanDao; 
	
	@Resource
    private InvestOrderService      investOrderService;

	@Override
	public PlainResult<Integer> checkInvest(Invest pojo) {
		
		 PlainResult<Integer> result = new PlainResult<Integer>();
		 
		 if(pojo.getBidType()==BidType.COMMON_LOAN){
			 
			// 1. 执行数据库进行投资前置条件判断: 是否已经满标，是否满足项目标的投标条件
		        // 1.1 基本前置条件判断
		        final LoanDO loanDO = loanDao.findByLoanIdWithLock(pojo.getBidId());
		        
		        
		        if (loanDO == null) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资的标不存在");
		        } else if (pojo.getUserId().equals(loanDO.getLoanUserId())) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和借款人不能为同一个人");
		        }

		        // 1.2 投资普通标前置条件判断
		        Date nowDate = new Date();
		        if (loanDO.getLoanInvestEndtime() != null && loanDO.getLoanInvestEndtime().before(nowDate)) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资结束时间已到，不可投资");
		        } else if (loanDO.getLoanInvestStarttime() != null && loanDO.getLoanInvestStarttime().after(nowDate)) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资投资开始时间未到，不可投资");
		        }
		        
		        //判断当前标有没有在第三方停留    若标的LOANSTATE为INVEST_ING（99）则说明在用户在第三方停留
//		        if(loanDO.getLoanState() == 99){
//		        	return result.setError(CommonResultCode.BIZ_ERROR, "此标正在被操作，请耐心等待...");
//		        }

		        if (loanDO.getLoanMaxInvest() != null && pojo.getInvestMoney().compareTo(loanDO.getLoanMaxInvest()) > 0) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能大于当前标设置的最大可投限制");
		        }

		        if (pojo.getInvestMoney().compareTo(loanDO.getLoanMinInvest()) < 0
		                && loanDO.getLoanMinInvest().compareTo(loanDO.getLoanMoney().subtract(loanDO.getLoanCurrentInvest())) <= 0) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能小于当前标设置的最小可投限制");
		        }

//		        BigDecimal newCurrentInvest = loanDO.getLoanCurrentInvest().add(pojo.getInvestMoney());
		        BigDecimal newCurrentValidInvest = loanDO.getLoanCurrentValidInvest().add(pojo.getInvestMoney());
		        if (newCurrentValidInvest.compareTo(loanDO.getLoanMoney()) > 0) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能超过当前标的实际可投金额");
		        }

		        PlainResult<Boolean> orderResult = queryOrderExistence(pojo.getBidId(), pojo.getBidType(), pojo.getUserId());
		        if (!orderResult.isSuccess()) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "查询订单表判断是否投资多次失败");
		        }
				return result;
			 
		 }else{
			 
			// 1. 执行数据库进行投资前置条件判断: 是否已经满标，是否满足项目标的投标条件
		        // 1.1 基本前置条件判断
		        final TransferLoanDO transferLoanDO = transferLoanDao.findByTransferLoanIdWithLock(pojo.getBidId());

		        if (pojo.getUserId().equals(transferLoanDO.getTlUserId())) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和借款人不能为同一个人");
		        }

		        // 1.2 投资转让标前置条件判断
		        Date nowDate = new Date();
		        if (transferLoanDO.getTlInvestEndtime() != null && transferLoanDO.getTlInvestEndtime().before(nowDate)) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资结束时间已到，不可投资");
		        } else if (transferLoanDO.getTlInvestStarttime() != null
		                && transferLoanDO.getTlInvestStarttime().after(nowDate)) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资投资开始时间未到，不可投资");
		        }
		        
		      //判断当前标有没有在第三方停留    若标的LOANSTATE为INVEST_ING（99）则说明在用户在第三方停留
		        if(transferLoanDO.getTlState() == 99){
		        	return result.setError(CommonResultCode.BIZ_ERROR, "此标正在被操作，请耐心等待...");
		        }

		        // 检查转让标的投资人是不是原始借款人
		        LoanDO loanDO = loanDao.findById(transferLoanDO.getTlOriginId());
		        if (loanDO == null) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标不存在");
		        } else if (loanDO.getLoanUserId().equals(pojo.getUserId())) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "原始的普通标借款人不能投资本转让标");
		        }

		        if (pojo.getUserId().equals(transferLoanDO.getTlUserId())) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资人和转让人不能为同一个人");
		        } else if (TransferLoanState.TRANSFERING.getState() != transferLoanDO.getTlState()) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "当前的转让标不可投资");
		        }

//		        BigDecimal newCurrentInvest = transferLoanDO.getTlCurrentInvest().add(pojo.getInvestMoney());
		        BigDecimal newCurrentValidInvest = transferLoanDO.getTlCurrentValidInvest().add(pojo.getInvestMoney());
		        if (newCurrentValidInvest.compareTo(transferLoanDO.getTlTransferMoney()) > 0) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "投资金额不能超过当前转让标的实际可投金额");
		        }

		        PlainResult<Boolean> orderResult = queryOrderExistence(pojo.getBidId(), pojo.getBidType(), pojo.getUserId());
		        if (!orderResult.isSuccess()) {
		            return result.setError(CommonResultCode.BIZ_ERROR, "查询订单表判断是否投资多次失败");
		        }
		        
		        return result;
		 }
		 
	        
		
	}

	/**
     * 多次投资的校验 查询订单表是否存在对同一个类型的同一个标有未支付的或支付成功的订单
     */
    private PlainResult<Boolean> queryOrderExistence(int bidId, BidType bidType, int userId) {
        PlainResult<Boolean> orderResult = investOrderService.queryExistence(bidId, bidType, userId,
                Arrays.asList(OrderState.UNPAID, OrderState.PAID));
        return orderResult;
    }
	
}
